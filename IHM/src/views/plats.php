<?php
// src/views/plats.php
// Vue pour afficher tous les plats utilisateurs
?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Plats</title>
    <link rel="stylesheet" href="public/css/style.css">
</head>
<body>
    <h1>🍽️ Tous les Plats</h1>
    <?php if (empty($plats)): ?>
        <p class="empty-message">Aucun plat disponible</p>
    <?php else: ?>
        <?php foreach ($plats as $plat): ?>
            <div class="plat">
                <h2><?php echo htmlspecialchars($plat['nom']); ?></h2>
                <div class="plat-info">
                    <p>Utilisateur: <?php echo htmlspecialchars($plat['utilisateurNom']); ?></p>
                    <p>Prix: <span class="prix"><?php echo $plat['prix']; ?>€</span></p>
                </div>
            </div>
        <?php endforeach; ?>
    <?php endif; ?>
</body>
</html>
