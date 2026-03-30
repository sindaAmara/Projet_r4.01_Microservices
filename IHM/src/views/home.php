<?php
// src/views/home.php
// Vue pour la page d'accueil
?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil</title>
    <link rel="stylesheet" href="public/css/style.css">
</head>
<body>
    <h1>🏠 Bienvenue</h1>
    <p>Bienvenue sur notre application de gestion de menus et commandes.</p>
    <div class="nav">
        <a href="?page=menus">📋 Voir les Menus</a>
        <a href="?page=plats">🍽️ Voir les Plats</a>
        <a href="?page=commandes">📦 Voir les Commandes</a>
    </div>
</body>
</html>
