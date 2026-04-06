<?php // ihm/src/views/plats/list.php
/**
 * Vue d'affichage de la liste des plats.
 *
 * Présente les plats sous forme de cartes avec nom, description, prix et bouton d'ajout.
 *
 * Variables attendues :
 *   - array $plats : Liste des plats récupérés depuis l'API
 */
?>
<?php require __DIR__ . '/../layout/header.php'; ?>

<h1>Nos Plats</h1>

<div class="grid-plats">
    <?php foreach ($plats as $plat): ?>
        <div class="card">
            <h2><?= htmlspecialchars($plat['nom']) ?></h2>
            <p class="description"><?= htmlspecialchars($plat['description']) ?></p>
            <p class="prix"><?= number_format($plat['prix'], 2, ',', ' ') ?> €</p>
            <a href="index.php?page=composer&add_plat=<?= $plat['id'] ?>" class="btn btn-primary">Ajouter au menu</a>
        </div>
    <?php endforeach; ?>
</div>

<?php require __DIR__ . '/../layout/footer.php'; ?>
