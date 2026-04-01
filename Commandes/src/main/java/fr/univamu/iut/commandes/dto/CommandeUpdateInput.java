package fr.univamu.iut.commandes.dto;

/**
 * DTO pour la mise à jour d'une commande.
 * Seuls l'adresse et la date de livraison peuvent être modifiées.
 */
public class CommandeUpdateInput {

    private String adresseLivraison;
    private String dateLivraison;

    public CommandeUpdateInput() {
    }

    public CommandeUpdateInput(String adresseLivraison, String dateLivraison) {
        this.adresseLivraison = adresseLivraison;
        this.dateLivraison = dateLivraison;
    }

    public String getAdresseLivraison() { return adresseLivraison; }
    public void setAdresseLivraison(String adresseLivraison) { this.adresseLivraison = adresseLivraison; }

    public String getDateLivraison() { return dateLivraison; }
    public void setDateLivraison(String dateLivraison) { this.dateLivraison = dateLivraison; }
}
