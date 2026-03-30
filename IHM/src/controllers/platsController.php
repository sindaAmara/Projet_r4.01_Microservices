<?php

class platsController
{
    /**
     * Récupère tous les plats utilisateurs depuis l'API
     * @return array|false Les plats ou false en cas d'erreur
     */
    public static function getAllPlats()
    {
        return ApiClient::get(API_PLATS_UTILISATEURS . '/plats');
    }

    /**
     * Récupère un plat spécifique par son ID
     * @param int $id L'ID du plat
     * @return array|false Le plat ou false en cas d'erreur
     */
    public static function getPlatById($id)
    {
        return ApiClient::get(API_PLATS_UTILISATEURS . '/plats/' . intval($id));
    }
}
