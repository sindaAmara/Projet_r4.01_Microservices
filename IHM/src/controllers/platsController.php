<?php // ihm/src/controllers/PlatsController.php

/**
 * Contrôleur pour la gestion des plats.
 *
 * Récupère les données de l'API Plats et prépare les vues associées.
 */
class PlatsController
{
    /**
     * Affiche la liste de tous les plats.
     *
     * @return void
     */
    public function index()
    {
        $currentPage = 'plats';
        $plats = ApiClient::get(API_PLATS . '/plats');

        if ($plats === null) {
            $plats = [];
        }

        require __DIR__ . '/../views/plats/list.php';
    }
}
