<?php
// src/views/menus.php
// Vue pour afficher tous les menus
?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menus</title>
    <link rel="stylesheet" href="public/css/style.css">
</head>
<body>
    <h1>📋 Tous les Menus</h1>
    <?php if (empty($menus)): ?>
        <p class="empty-message">Aucun menu disponible</p>
    <?php else: ?>
        <?php foreach ($menus as $menu): ?>
            <div class="menu">
                <h2><?php echo htmlspecialchars($menu['nom']); ?></h2>
                <div class="menu-info">
                    <p>Créateur: <?php echo htmlspecialchars($menu['createurNom']); ?></p>
                    <p>Date création: <?php echo $menu['dateCreation']; ?></p>
                    <p>Prix total: <span class="prix"><?php echo $menu['prixTotal']; ?>€</span></p>
                </div>
                <div class="plats">
                    <h3>Plats:</h3>
                    <?php foreach ($menu['plats'] as $plat): ?>
                        <div class="plat">
                            • <?php echo htmlspecialchars($plat['nom']); ?> - <span class="prix"><?php echo $plat['prix']; ?>€</span>
                        </div>
                    <?php endforeach; ?>
                </div>
            </div>
        <?php endforeach; ?>
    <?php endif; ?>
</body>
</html>
