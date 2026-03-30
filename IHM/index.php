<?php
require_once './config/config.php';
require_once './src/helpers/ApiClient.php';
require_once './src/controllers/homeController.php';
require_once './src/controllers/menusController.php';
require_once './src/controllers/platsController.php';
require_once './src/controllers/commandesController.php';

session_start();

// Page demandée via ?page=..., valeur par défaut 'home'
$page = isset($_GET['page']) ? $_GET['page'] : 'home';

// Whitelist des pages autorisées (évite les inclusions arbitraires)
$allowedPages = ['home', 'menus', 'plats', 'commandes'];
if (!in_array($page, $allowedPages, true)) {
    http_response_code(404);
    exit;
}

// Router : déléguer à la logique métier (contrôleur) et afficher la vue correspondante
switch ($page) {
    case 'menus':
        // Récupérer les données via le contrôleur (API call)
        $menus = menusController::getAllMenus();
        $menus = $menus ?? [];

        // Afficher la vue
        require './src/views/menus.php';
        break;

    case 'plats':
        // Récupérer les données via le contrôleur (API call)
        $plats = platsController::getAllPlats();
        $plats = $plats ?? [];

        // Afficher la vue
        require './src/views/plats.php';
        break;

    case 'commandes':
        // Récupérer les données via le contrôleur (API call)
        $commandes = commandesController::getAllCommandes();
        $commandes = $commandes ?? [];

        // Afficher la vue
        require './src/views/commandes.php';
        break;

    case 'home':
    default:
        // Afficher la vue d'accueil
        require './src/views/home.php';
        break;
}
