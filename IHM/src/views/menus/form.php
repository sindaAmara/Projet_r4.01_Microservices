<?php // ihm/src/views/menus/form.php
/**
 * Vue du formulaire de création d'un nouveau menu.
 *
 * Affiche un formulaire avec champs nom, créateur, et cases à cocher pour chaque plat.
 * Le prix total est calculé dynamiquement en JavaScript.
 *
 * Variables attendues :
 *   - array $plats : Liste des plats disponibles
 */
?>
<?php require __DIR__ . '/../layout/header.php'; ?>

<h1>Composer un Menu</h1>

<?php if (isset($_SESSION['error'])): ?>
    <div class="alert alert-error">
        <?= htmlspecialchars($_SESSION['error']) ?>
    </div>
    <?php unset($_SESSION['error']); ?>
<?php endif; ?>

<form method="POST" action="index.php?page=composer" class="form-menu" id="menuForm">
    <div class="form-group">
        <label for="nom">Nom du menu</label>
        <input type="text" id="nom" name="nom" required>
    </div>

    <div class="form-group">
        <label for="createur_nom">Votre nom</label>
        <input type="text" id="createur_nom" name="createur_nom" required>
    </div>

    <div class="form-group">
        <label>Plats disponibles</label>
        <div class="plats-checkboxes">
            <?php foreach ($plats as $plat): ?>
                <label class="plat-checkbox">
                    <input type="checkbox" name="plats[]" value="<?= $plat['id'] ?>" data-prix="<?= $plat['prix'] ?>">
                    <span><?= htmlspecialchars($plat['nom']) ?></span>
                    <span class="plat-prix"><?= number_format($plat['prix'], 2, ',', ' ') ?> €</span>
                </label>
            <?php endforeach; ?>
        </div>
    </div>

    <div class="form-group">
        <p class="total-prix">Prix total : <span id="prixTotal">0,00</span> €</p>
    </div>

    <button type="submit" class="btn btn-primary">Sauvegarder</button>
</form>

<script>
    const checkboxes = document.querySelectorAll('input[name="plats[]"]');
    const prixTotalSpan = document.getElementById('prixTotal');
    const menuForm = document.getElementById('menuForm');
    let formSubmitted = false;

    function calculerPrixTotal() {
        let total = 0;
        checkboxes.forEach(cb => {
            if (cb.checked) {
                total += parseFloat(cb.dataset.prix);
            }
        });
        prixTotalSpan.textContent = total.toFixed(2).replace('.', ',');
    }

    // Prévenir les soumissions multiples du formulaire
    menuForm.addEventListener('submit', function(event) {
        if (formSubmitted) {
            event.preventDefault();
            return false;
        }
        formSubmitted = true;

        // Désactiver le bouton de soumission
        const submitBtn = menuForm.querySelector('button[type="submit"]');
        if (submitBtn) {
            submitBtn.disabled = true;
            submitBtn.textContent = 'Sauvegarde en cours...';
        }
    });

    checkboxes.forEach(cb => cb.addEventListener('change', calculerPrixTotal));
</script>

<?php require __DIR__ . '/../layout/footer.php'; ?>
