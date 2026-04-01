package fr.univamu.iut.commandes.dto;

/**
 * DTO pour une ligne de commande en entrée.
 * Contient uniquement le menuId et la quantité.
 */
public class LigneCommandeInput {

    private int menuId;
    private String menuNom;
    private int quantite;
    private double prixUnitaire;

    public LigneCommandeInput() {
    }

    public LigneCommandeInput(int menuId, String menuNom, int quantite, double prixUnitaire) {
        this.menuId = menuId;
        this.menuNom = menuNom;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
    }

    public int getMenuId() { return menuId; }
    public void setMenuId(int menuId) { this.menuId = menuId; }

    public String getMenuNom() { return menuNom; }
    public void setMenuNom(String menuNom) { this.menuNom = menuNom; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    public double getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(double prixUnitaire) { this.prixUnitaire = prixUnitaire; }
}
