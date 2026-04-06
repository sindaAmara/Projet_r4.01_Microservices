<?php // ihm/src/controllers/MenusController.php

/**
 * Contrôleur pour la gestion des menus.
 *
 * Gère l'affichage, la création, la modification et la suppression des menus
 * via l'API Menus distante.
 */
class MenusController
{
    /**
     * Affiche la liste de tous les menus avec leurs détails.
     *
     * Gère également la suppression d'un menu si un paramètre GET `delete` est présent.
     *
     * @return void
     */
    public function index()
    {
        $currentPage = 'menus';
        if (isset($_GET['delete'])) {
            $menuId = $_GET['delete'];
            ApiClient::delete(API_MENUS . '/menus/' . $menuId);
            header('Location: index.php?page=menus');
            exit;
        }

        $menus = ApiClient::get(API_MENUS . '/menus');

        if ($menus === null) {
            $menus = [];
        }

        $plats = ApiClient::get(API_PLATS . '/plats');

        if ($plats === null) {
            $plats = [];
        }

        $platsMap = [];
        foreach ($plats as $plat) {
            $platsMap[$plat['id']] = $plat;
        }

        // Normaliser la structure des menus pour assurer la cohérence
        foreach ($menus as &$menu) {
            // Si le menu a platsIds au lieu de plats, reconstruire la liste des plats
            if (isset($menu['platsIds']) && !isset($menu['plats'])) {
                $menu['plats'] = [];
                foreach ($menu['platsIds'] as $platId) {
                    if (isset($platsMap[$platId])) {
                        $menu['plats'][] = $platsMap[$platId];
                    }
                }
            }
        }

        require __DIR__ . '/../views/menus/list.php';
    }

    /**
     * Affiche le formulaire de création d'un nouveau menu.
     *
     * Récupère la liste des plats disponibles pour les cases à cocher.
     * Gère la soumission du formulaire via POST.
     *
     * @return void
     */
    public function create()
    {
        $currentPage = 'composer';
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            $nom = trim($_POST['nom'] ?? '');
            $createurNom = trim($_POST['createur_nom'] ?? '');
            $platsIds = isset($_POST['plats']) ? array_map('intval', $_POST['plats']) : [];

            // Valider les champs requis
            if (empty($nom) || empty($createurNom)) {
                $_SESSION['error'] = 'Le nom du menu et le créateur sont obligatoires.';
                header('Location: index.php?page=composer');
                exit;
            }

            // Récupérer tous les menus existants
            $existingMenus = ApiClient::get(API_MENUS . '/menus');
            if ($existingMenus === null) {
                $existingMenus = [];
            }

            // Vérifier si un menu identique existe déjà (même nom, même créateur, même date)
            $todayDate = date('Y-m-d');
            $duplicateExists = false;

            foreach ($existingMenus as $menu) {
                if (
                    $menu['nom'] === $nom &&
                    $menu['createurNom'] === $createurNom &&
                    $menu['dateCreation'] === $todayDate &&
                    count($menu['plats']) === count($platsIds)
                ) {
                    // Vérifier que les plats sont identiques
                    $platsMatch = true;
                    foreach ($menu['plats'] as $plat) {
                        if (!in_array($plat['id'], $platsIds)) {
                            $platsMatch = false;
                            break;
                        }
                    }
                    if ($platsMatch) {
                        $duplicateExists = true;
                        break;
                    }
                }
            }

            if ($duplicateExists) {
                $_SESSION['error'] = 'Ce menu existe déjà dans le système.';
                header('Location: index.php?page=composer');
                exit;
            }

            $plats = ApiClient::get(API_PLATS . '/plats');
            $platsObjects = [];
            $prixTotal = 0;

            if ($plats !== null) {
                foreach ($plats as $plat) {
                    if (in_array($plat['id'], $platsIds)) {
                        $platsObjects[] = $plat;
                        $prixTotal += (float) $plat['prix'];
                    }
                }
            }

            $data = [
                'nom' => $nom,
                'createurNom' => $createurNom,
                'dateCreation' => $todayDate,
                'plats' => $platsObjects,
                'prixTotal' => round($prixTotal, 2)
            ];

            ApiClient::post(API_MENUS . '/menus', $data);


            header('Location: index.php?page=menus');
            exit;
        }

        $plats = ApiClient::get(API_PLATS . '/plats');

        if ($plats === null) {
            $plats = [];
        }

        require __DIR__ . '/../views/menus/form.php';
    }
}
