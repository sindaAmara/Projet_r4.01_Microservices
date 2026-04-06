<?php // ihm/src/views/layout/header.php
/**
 * En-tête HTML commun à toutes les pages.
 *
 * Affiche la barre de navigation avec les liens vers les différentes sections.
 *
 * @param string $currentPage Page active pour le surlignage du lien
 */
?>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Livraison de Repas</title>
    <link rel="stylesheet" href="public/css/style.css">
</head>
<body>
    <nav class="navbar">
        <div class="container">
            <a href="index.php" class="logo">🍽️ LivraisonRepas</a>
            <ul class="nav-links">
                <li><a href="index.php?page=plats" class="<?= $currentPage === 'plats' ? 'active' : '' ?>">Plats</a></li>
                <li><a href="index.php?page=menus" class="<?= $currentPage === 'menus' ? 'active' : '' ?>">Menus</a></li>
                <li><a href="index.php?page=composer" class="<?= $currentPage === 'composer' ? 'active' : '' ?>">Composer</a></li>
                <li><a href="index.php?page=commandes" class="<?= $currentPage === 'commandes' ? 'active' : '' ?>">Commandes</a></li>
            </ul>
        </div>
    </nav>
    <main class="container">
