<?php // ihm/src/views/menus/list.php
/**
 * Vue d'affichage de la liste des menus.
 *
 * Présente les menus sous forme de cartes avec nom, créateur, date, liste des plats,
 * prix total et boutons d'action (commander, supprimer).
 *
 * Variables attendues :
 *   - array $menus : Liste des menus récupérés depuis l'API
 *   - array $platsMap : Map id => plat pour résoudre les noms des plats
 */
?>
<?php require __DIR__ . '/../layout/header.php'; ?>

<div class="page-header">
    <h1>Nos Menus</h1>
    <a href="index.php?page=composer" class="btn btn-primary">+ Composer un menu</a>
</div>

<div class="grid-menus">
    <?php foreach ($menus as $menu): ?>
        <div class="card">
            <h2><?= htmlspecialchars($menu['nom']) ?></h2>
            <p class="meta">Créé par <?= htmlspecialchars($menu['createurNom']) ?> le <?= htmlspecialchars($menu['dateCreation']) ?></p>
            <ul class="plats-list">
                <?php if (!empty($menu['plats'])): ?>
                    <?php foreach ($menu['plats'] as $plat): ?>
                        <li><?= htmlspecialchars($plat['nom']) ?></li>
                    <?php endforeach; ?>
                <?php else: ?>
                    <li>Aucun plat</li>
                <?php endif; ?>
            </ul>
            <p class="prix">Prix total : <?= number_format($menu['prixTotal'], 2, ',', ' ') ?> €</p>
            <div class="card-actions">
                <a href="index.php?page=commandes&menu_id=<?= $menu['id'] ?>" class="btn btn-primary">Commander ce menu</a>
                <a href="index.php?page=menus&delete=<?= $menu['id'] ?>" class="btn btn-danger" onclick="return confirm('Supprimer ce menu ?');">Supprimer</a>
            </div>
        </div>
    <?php endforeach; ?>
</div>

<?php require __DIR__ . '/../layout/footer.php'; ?>
