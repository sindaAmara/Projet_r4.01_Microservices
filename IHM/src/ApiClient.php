<?php // ihm/src/ApiClient.php

/**
 * Client HTTP centralisé pour les appels cURL vers les APIs REST.
 *
 * Fournit des méthodes statiques pour effectuer des requêtes GET, POST, PUT et DELETE
 * vers les endpoints JSON configurés dans config.php.
 */
class ApiClient
{
    /**
     * Effectue une requête GET vers une API distante.
     *
     * @param string $url URL complète de l'endpoint
     * @return mixed|null Données JSON décodées ou null en cas d'erreur
     */
    public static function get($url)
    {
        $ch = curl_init($url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, ['Accept: application/json']);
        $response = curl_exec($ch);
        $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
        $curl = null;

        if ($response === false || $httpCode >= 400) {
            return null;
        }

        return json_decode($response, true);
    }

    /**
     * Effectue une requête POST vers une API distante.
     *
     * @param string $url URL complète de l'endpoint
     * @param array $data Données à envoyer en corps de requête
     * @return mixed|null Données JSON décodées ou null en cas d'erreur
     */
    public static function post($url, $data)
    {
        $payload = json_encode($data);
        $ch = curl_init($url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $payload);
        curl_setopt($ch, CURLOPT_HTTPHEADER, [
            'Content-Type: application/json',
            'Accept: application/json',
            'Content-Length: ' . strlen($payload)
        ]);
        $response = curl_exec($ch);
        $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
        curl_close($ch);

        if ($response === false || $httpCode >= 400) {
            return null;
        }

        return json_decode($response, true);
    }

    /**
     * Effectue une requête PUT vers une API distante.
     *
     * @param string $url URL complète de l'endpoint
     * @param array $data Données à envoyer en corps de requête
     * @return mixed|null Données JSON décodées ou null en cas d'erreur
     */
    public static function put($url, $data)
    {
        $payload = json_encode($data);
        $ch = curl_init($url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'PUT');
        curl_setopt($ch, CURLOPT_POSTFIELDS, $payload);
        curl_setopt($ch, CURLOPT_HTTPHEADER, [
            'Content-Type: application/json',
            'Accept: application/json',
            'Content-Length: ' . strlen($payload)
        ]);
        $response = curl_exec($ch);
        $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
        curl_close($ch);

        if ($response === false || $httpCode >= 400) {
            return null;
        }

        return json_decode($response, true);
    }

    /**
     * Effectue une requête DELETE vers une API distante.
     *
     * @param string $url URL complète de l'endpoint
     * @return bool true si la suppression a réussi, false sinon
     */
    public static function delete($url)
    {
        $ch = curl_init($url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'DELETE');
        curl_setopt($ch, CURLOPT_HTTPHEADER, ['Accept: application/json']);
        $response = curl_exec($ch);
        $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
        curl_close($ch);

        return $httpCode >= 200 && $httpCode < 300;
    }
}
