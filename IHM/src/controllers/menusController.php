<?php

class menusController
{
    /**
     * Récupère tous les menus depuis l'API
     * @return array|false Les menus ou false en cas d'erreur
     */
    public static function getAllMenus()
    {
        return ApiClient::get(API_MENUS . '/menus');
    }

    /**
     * Récupère un menu spécifique par son ID
     * @param int $id L'ID du menu
     * @return array|false Le menu ou false en cas d'erreur
     */
    public static function getMenuById($id)
    {
        return ApiClient::get(API_MENUS . '/menus/' . intval($id));
    }

    /**
     * Crée un nouveau menu
     * @param array $data Les données du menu
     * @return array|false Le menu créé ou false en cas d'erreur
     */
    public static function createMenu($data)
    {
        return ApiClient::post(API_MENUS . '/menus', $data);
    }

    /**
     * Met à jour un menu
     * @param int $id L'ID du menu
     * @param array $data Les données à mettre à jour
     * @return array|false Le menu mis à jour ou false en cas d'erreur
     */
    public static function updateMenu($id, $data)
    {
        return ApiClient::put(API_MENUS . '/menus/' . intval($id), $data);
    }

    /**
     * Supprime un menu
     * @param int $id L'ID du menu
     * @return bool True si succès, false sinon
     */
    public static function deleteMenu($id)
    {
        return ApiClient::delete(API_MENUS . '/menus/' . intval($id));
    }
}