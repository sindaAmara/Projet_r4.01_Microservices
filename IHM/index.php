<?php // ihm/index.php

/**
 * Point d'entrée unique de l'application IHM.
 *
 * Initialise la session, charge la configuration et les dépendances,
 * puis route la requête vers le contrôleur approprié selon le paramètre `page`.
 */

require_once __DIR__ . '/config/config.php';
require_once __DIR__ . '/src/ApiClient.php';

session_start();

// Déclarer currentPage comme variable globale pour qu'elle soit accessible dans les contrôleurs et vues
$GLOBALS['currentPage'] = isset($_GET['page']) ? $_GET['page'] : 'plats';
$currentPage = $GLOBALS['currentPage'];

switch ($currentPage) {
    case 'menus':
        require_once __DIR__ . '/src/controllers/MenusController.php';
        $controller = new MenusController();
        $controller->index();
        break;

    case 'composer':
        require_once __DIR__ . '/src/controllers/MenusController.php';
        $controller = new MenusController();
        $controller->create();
        break;

    case 'commandes':
        require_once __DIR__ . '/src/controllers/CommandesController.php';
        $controller = new CommandesController();
        $controller->index();
        break;

    case 'plats':
    default:
        require_once __DIR__ . '/src/controllers/PlatsController.php';
        $controller = new PlatsController();
        $controller->index();
        break;
}
