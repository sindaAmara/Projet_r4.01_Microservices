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
        } else if (is_object($menus)) {
            $menus = array_values((array)$menus);
        }

        $plats = ApiClient::get(API_PLATS . '/plats');

        if ($plats === null) {
            $plats = [];
        }

        $platsMap = [];
        foreach ($plats as $plat) {
            $platsMap[(string)$plat['id']] = $plat;
        }

        // Normaliser la structure des menus : si platsIds existe sans plats, reconstruire
        $normalizedMenus = [];
        foreach ($menus as $menu) {
            if (isset($menu['platsIds']) && is_array($menu['platsIds']) && !isset($menu['plats'])) {
                $menu['plats'] = [];
                foreach ($menu['platsIds'] as $platId) {
                    $key = (string)$platId;
                    if (isset($platsMap[$key])) {
                        $menu['plats'][] = $platsMap[$key];
                    }
                }
            }
            $normalizedMenus[] = $menu;
        }
        $menus = $normalizedMenus;

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

            if (empty($nom) || empty($createurNom)) {
                $_SESSION['error'] = 'Le nom du menu et le créateur sont obligatoires.';
                header('Location: index.php?page=composer');
                exit;
            }

            // Récupérer les plats disponibles
            $allPlats = ApiClient::get(API_PLATS . '/plats');
            $platsMap = [];
            if ($allPlats !== null) {
                foreach ($allPlats as $p) {
                    $platsMap[(string)$p['id']] = $p;
                }
            }

            // Construire les objets plats pour le nouveau menu
            $platsObjects = [];
            $prixTotal = 0;

            foreach ($platsIds as $platId) {
                $key = (string)$platId;
                if (isset($platsMap[$key])) {
                    $platsObjects[] = $platsMap[$key];
                    $prixTotal += (float)$platsMap[$key]['prix'];
                }
            }

            $data = [
                'nom' => $nom,
                'createurNom' => $createurNom,
                'dateCreation' => date('Y-m-d'),
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
