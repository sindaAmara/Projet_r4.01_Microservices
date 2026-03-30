<?php

class commandesController
{
    /**
     * Récupère toutes les commandes depuis l'API
     * @return array|false Les commandes ou false en cas d'erreur
     */
    public static function getAllCommandes()
    {
        return ApiClient::get(API_COMMANDES . '/commandes');
    }

    /**
     * Récupère une commande spécifique par son ID
     * @param int $id L'ID de la commande
     * @return array|false La commande ou false en cas d'erreur
     */
    public static function getCommandeById($id)
    {
        return ApiClient::get(API_COMMANDES . '/commandes/' . intval($id));
    }

    /**
     * Crée une nouvelle commande
     * @param array $data Les données de la commande
     * @return array|false La commande créée ou false en cas d'erreur
     */
    public static function createCommande($data)
    {
        return ApiClient::post(API_COMMANDES . '/commandes', $data);
    }
}
