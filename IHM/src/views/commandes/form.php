<?php // ihm/src/views/commandes/form.php
/**
 * Vue du formulaire de création d'une nouvelle commande.
 *
 * Affiche un formulaire avec sélection du menu, quantité, date et adresse de livraison.
 * Le prix total est calculé dynamiquement en JavaScript.
 *
 * Variables attendues :
 *   - array $menus : Liste des menus disponibles pour le select
 *   - array|null $selectedMenu : Menu pré-sélectionné via GET ?menu_id=X
 *   - int|null $selectedMenuId : ID du menu pré-sélectionné
 */
?>
<div class="card form-commande">
    <h2>Nouvelle Commande</h2>
    <form method="POST" action="index.php?page=commandes" id="commandeForm">
        <div class="form-group">
            <label for="menu_id">Menu</label>
            <select id="menu_id" name="menu_id" required>
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
