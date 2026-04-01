package fr.univamu.iut.commandes.entities;

/**
 * Entité représentant une ligne de commande (un menu commandé avec sa quantité).
 */
public class LigneCommande {

    private int menuId;
    private String menuNom;
    private int quantite;
    private double prixUnitaire;
    private double prixLigne;

    /**
     * Constructeur par défaut.
     */
    public LigneCommande() {
    }

    /**
     * Constructeur complet.
     */
    public LigneCommande(int menuId, String menuNom, int quantite,
                         double prixUnitaire, double prixLigne) {
        this.menuId = menuId;
        this.menuNom = menuNom;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.prixLigne = prixLigne;
    }

    public int getMenuId() { return menuId; }
    public void setMenuId(int menuId) { this.menuId = menuId; }

    public String getMenuNom() { return menuNom; }
    public void setMenuNom(String menuNom) { this.menuNom = menuNom; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    public double getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(double prixUnitaire) { this.prixUnitaire = prixUnitaire; }

    public double getPrixLigne() { return prixLigne; }
    public void setPrixLigne(double prixLigne) { this.prixLigne = prixLigne; }
}
