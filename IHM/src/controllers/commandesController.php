<?php // ihm/src/controllers/CommandesController.php

/**
 * Contrôleur pour la gestion des commandes.
 *
 * Gère l'affichage, la création et l'annulation des commandes
 * via l'API Commandes distante.
 */
class CommandesController
{
    /**
     * Affiche la liste de toutes les commandes et le formulaire de nouvelle commande.
     *
     * Si un `menu_id` est passé en GET, le formulaire de commande est pré-rempli.
     * Gère la soumission du formulaire via POST et l'annulation via GET `cancel`.
     *
     * @return void
     */
    public function index()
    {
        $currentPage = 'commandes';
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            $menuId = (int) ($_POST['menu_id'] ?? 0);
            $quantite = (int) ($_POST['quantite'] ?? 1);
            $adresseLivraison = trim($_POST['adresse_livraison'] ?? '');
            $dateLivraison = trim($_POST['date_livraison'] ?? '');

            $menu = ApiClient::get(API_MENUS . '/menus/' . $menuId);
            $prixMenu = $menu !== null ? (float) $menu['prixTotal'] : 0;

            $data = [
                'menuId' => $menuId,
                'quantite' => $quantite,
                'dateCommande' => date('Y-m-d'),
                'dateLivraison' => $dateLivraison,
                'adresseLivraison' => $adresseLivraison,
                'prixTotal' => round($prixMenu * $quantite, 2),
                'statut' => 'confirmée'
            ];

            ApiClient::post(API_COMMANDES . '/commandes', $data);

            header('Location: index.php?page=commandes');
            exit;
        }

        if (isset($_GET['cancel'])) {
            $commandeId = $_GET['cancel'];
            ApiClient::delete(API_COMMANDES . '/commandes/' . urlencode($commandeId));
            header('Location: index.php?page=commandes');
            exit;
        }

        $commandes = ApiClient::get(API_COMMANDES . '/commandes');

        if ($commandes === null) {
            $commandes = [];
        } else if (is_object($commandes)) {
            // Convertir l'objet JSON en array de commandes
            $commandes = array_values((array)$commandes);
        }

        $menus = ApiClient::get(API_MENUS . '/menus');

        if ($menus === null) {
            $menus = [];
        }

        $selectedMenu = null;
        $selectedMenuId = isset($_GET['menu_id']) ? (int) $_GET['menu_id'] : null;

        if ($selectedMenuId !== null) {
            foreach ($menus as $menu) {
                if ($menu['id'] === $selectedMenuId) {
                    $selectedMenu = $menu;
                    break;
                }
            }
        }

        require __DIR__ . '/../views/commandes/list.php';
    }
}
