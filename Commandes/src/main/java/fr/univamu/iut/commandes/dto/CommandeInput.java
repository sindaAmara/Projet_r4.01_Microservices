package fr.univamu.iut.commandes.dto;

import java.util.List;

/**
 * DTO pour la création d'une commande.
 * Contient les données envoyées par le client lors du POST.
 */
public class CommandeInput {

    private int abonneId;
    private String adresseLivraison;
    private String dateLivraison;
    private List<LigneCommandeInput> lignes;

    public CommandeInput() {
    }

    public CommandeInput(int abonneId, String adresseLivraison,
                         String dateLivraison, List<LigneCommandeInput> lignes) {
        this.abonneId = abonneId;
        this.adresseLivraison = adresseLivraison;
        this.dateLivraison = dateLivraison;
        this.lignes = lignes;
    }

    public int getAbonneId() { return abonneId; }
    public void setAbonneId(int abonneId) { this.abonneId = abonneId; }

    public String getAdresseLivraison() { return adresseLivraison; }
    public void setAdresseLivraison(String adresseLivraison) { this.adresseLivraison = adresseLivraison; }

    public String getDateLivraison() { return dateLivraison; }
    public void setDateLivraison(String dateLivraison) { this.dateLivraison = dateLivraison; }

    public List<LigneCommandeInput> getLignes() { return lignes; }
    public void setLignes(List<LigneCommandeInput> lignes) { this.lignes = lignes; }
}
