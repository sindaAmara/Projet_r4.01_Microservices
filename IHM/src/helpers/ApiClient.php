<?php

class ApiClient
{
    /**
     * Effectue un appel HTTP générique à l'API
     * @param string $url L'URL complète de l'API
     * @param string $method La méthode HTTP (GET, POST, PUT, DELETE)
     * @param array|null $data Les données à envoyer (pour POST/PUT)
     * @return array|false Les données décodées ou false en cas d'erreur
     */
    public static function call($url, $method = 'GET', $data = null)
    {
        $ch = curl_init($url);

        // Configuration de base
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_CUSTOMREQUEST, $method);
        curl_setopt($ch, CURLOPT_TIMEOUT, 5);

        // Headers
        $headers = ['Content-Type: application/json'];
        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);

        // Données pour POST/PUT
        if ($data !== null && ($method === 'POST' || $method === 'PUT')) {
            curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($data));
        }

        // Exécuter la requête
        $response = curl_exec($ch);
        $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
        $curlError = curl_error($ch);

        // Vérifier les erreurs
        if ($curlError) {
            error_log("API Error: $curlError for URL: $url");
            return false;
        }

        // Vérifier le code HTTP
        if ($httpCode >= 200 && $httpCode < 300) {
            return json_decode($response, true);
        }

        error_log("API HTTP Error: $httpCode for URL: $url");
        return false;
    }

    /**
     * Effectue un appel GET
     * @param string $url L'URL de l'API
     * @return array|false Les données décodées ou false en cas d'erreur
     */
    public static function get($url)
    {
        return self::call($url, 'GET');
    }

    /**
     * Effectue un appel POST
     * @param string $url L'URL de l'API
     * @param array $data Les données à envoyer
     * @return array|false Les données créées ou false en cas d'erreur
     */
    public static function post($url, $data)
    {
        return self::call($url, 'POST', $data);
    }

    /**
     * Effectue un appel PUT
     * @param string $url L'URL de l'API
     * @param array $data Les données à mettre à jour
     * @return array|false Les données mises à jour ou false en cas d'erreur
     */
    public static function put($url, $data)
    {
        return self::call($url, 'PUT', $data);
    }

    /**
     * Effectue un appel DELETE
     * @param string $url L'URL de l'API
     * @return bool True si succès, false sinon
     */
    public static function delete($url)
    {
        $result = self::call($url, 'DELETE');
        return $result !== false;
    }
}
