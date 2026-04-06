<?php // ihm/src/views/commandes/list.php
/**
 * Vue d'affichage de la liste des commandes et du formulaire de nouvelle commande.
 *
 * Affiche les commandes existantes sous forme de cartes et un formulaire pour
 * créer une nouvelle commande. Le prix est calculé dynamiquement en JavaScript.
 *
 * Variables attendues :
 *   - array $commandes : Liste des commandes récupérées depuis l'API
 *   - array $menus : Liste des menus disponibles pour le select
 *   - array|null $selectedMenu : Menu pré-sélectionné via GET ?menu_id=X
 *   - int|null $selectedMenuId : ID du menu pré-sélectionné
 */
?>
<?php require __DIR__ . '/../layout/header.php'; ?>

<h1>Mes Commandes</h1>

<div class="grid-commandes">
    <?php foreach ($commandes as $commande): ?>
        <div class="card">
            <h2>Commande #<?= htmlspecialchars((string)($commande['id'] ?? '')) ?></h2>
            <p><strong>Menu :</strong> ID <?= htmlspecialchars((string)($commande['menuId'] ?? 'N/A')) ?></p>
            <p><strong>Quantité :</strong> <?= htmlspecialchars((string)($commande['quantite'] ?? '0')) ?></p>
            <p><strong>Date de commande :</strong> <?= htmlspecialchars((string)($commande['dateCommande'] ?? '')) ?></p>
            <p><strong>Date de livraison :</strong> <?= htmlspecialchars((string)($commande['dateLivraison'] ?? '')) ?></p>
            <p><strong>Adresse :</strong> <?= htmlspecialchars((string)($commande['adresseLivraison'] ?? '')) ?></p>
            <p><strong>Prix total :</strong> <?= number_format((float)($commande['prixTotal'] ?? 0), 2, ',', ' ') ?> €</p>
            <p><strong>Statut :</strong> <span class="badge badge-<?= strtolower(str_replace(' ', '-', (string)($commande['statut'] ?? 'inconnu'))) ?>"><?= htmlspecialchars((string)($commande['statut'] ?? 'Inconnu')) ?></span></p>
            <div class="card-actions">
                <a href="index.php?page=commandes&cancel=<?= htmlspecialchars((string)($commande['id'] ?? '')) ?>" class="btn btn-danger" onclick="return confirm('Annuler cette commande ?');">Annuler</a>
            </div>
        </div>
    <?php endforeach; ?>
</div>

<div class="card form-commande">
    <h2>Nouvelle Commande</h2>
    <form method="POST" action="index.php?page=commandes" id="commandeForm">
        <div class="form-group">
            <label for="menu_id">Menu</label>
            <select id="menu_id" name="menu_id" required data-prix="<?= $selectedMenu ? htmlspecialchars($selectedMenu['prixTotal']) : '' ?>">
                <option value="">-- Sélectionner un menu --</option>
                <?php foreach ($menus as $menu): ?>
                    <option value="<?= $menu['id'] ?>" data-prix="<?= $menu['prixTotal'] ?>" <?= $selectedMenuId === $menu['id'] ? 'selected' : '' ?>>
                        <?= htmlspecialchars($menu['nom']) ?> — <?= number_format($menu['prixTotal'], 2, ',', ' ') ?> €
                    </option>
                <?php endforeach; ?>
            </select>
        </div>

        <div class="form-group">
            <label for="quantite">Quantité</label>
            <input type="number" id="quantite" name="quantite" min="1" value="1" required>
        </div>

        <div class="form-group">
            <label for="date_livraison">Date de livraison</label>
            <input type="date" id="date_livraison" name="date_livraison" required>
        </div>

        <div class="form-group">
            <label for="adresse_livraison">Adresse de livraison</label>
            <input type="text" id="adresse_livraison" name="adresse_livraison" required>
        </div>

        <p class="total-prix">Prix total : <span id="prixTotal"><?= $selectedMenu ? number_format($selectedMenu['prixTotal'], 2, ',', ' ') : '0,00' ?></span> €</p>

        <button type="submit" class="btn btn-primary">Confirmer</button>
    </form>
</div>

<script>
    const menuSelect = document.getElementById('menu_id');
    const quantiteInput = document.getElementById('quantite');
    const prixTotalSpan = document.getElementById('prixTotal');

    function calculerPrix() {
        const selectedOption = menuSelect.options[menuSelect.selectedIndex];
        const prixMenu = parseFloat(selectedOption.dataset.prix) || 0;
        const quantite = parseInt(quantiteInput.value) || 0;
        const total = prixMenu * quantite;
        prixTotalSpan.textContent = total.toFixed(2).replace('.', ',');
    }

    menuSelect.addEventListener('change', calculerPrix);
    quantiteInput.addEventListener('input', calculerPrix);
</script>

<?php require __DIR__ . '/../layout/footer.php'; ?>
