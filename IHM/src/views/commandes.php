<?php
// src/views/commandes.php
// Vue pour afficher toutes les commandes
?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Commandes</title>
    <link rel="stylesheet" href="public/css/style.css">
</head>
<body>
    <h1>📦 Toutes les Commandes</h1>
    <?php if (empty($commandes)): ?>
        <p class="empty-message">Aucune commande disponible</p>
    <?php else: ?>
        <?php foreach ($commandes as $commande): ?>
            <div class="commande">
                <h2>Commande #<?php echo htmlspecialchars($commande['id']); ?></h2>
                <div class="commande-info">
                    <p>Client: <?php echo htmlspecialchars($commande['clientNom']); ?></p>
                    <p>Date: <?php echo $commande['dateCommande']; ?></p>
                    <p>Montant: <span class="montant"><?php echo $commande['montantTotal']; ?>€</span></p>
                </div>
            </div>
        <?php endforeach; ?>
    <?php endif; ?>
</body>
</html>
