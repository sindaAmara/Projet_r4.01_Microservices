package fr.univamu.iut.commandes.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité représentant une commande passée par un abonné.
 */
public class Commande {

    private int id;
    private int abonneId;
    private LocalDateTime dateCommande;
    private String adresseLivraison;
    private LocalDate dateLivraison;
    private List<LigneCommande> lignes;
    private double prixTotal;

    /**
     * Constructeur par défaut.
     */
    public Commande() {
        this.lignes = new ArrayList<>();
    }

    /**
     * Constructeur complet.
     */
    public Commande(int id, int abonneId, LocalDateTime dateCommande,
                    String adresseLivraison, LocalDate dateLivraison,
                    List<LigneCommande> lignes, double prixTotal) {
        this.id = id;
        this.abonneId = abonneId;
        this.dateCommande = dateCommande;
        this.adresseLivraison = adresseLivraison;
        this.dateLivraison = dateLivraison;
        this.lignes = lignes != null ? lignes : new ArrayList<>();
        this.prixTotal = prixTotal;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAbonneId() { return abonneId; }
    public void setAbonneId(int abonneId) { this.abonneId = abonneId; }

    public LocalDateTime getDateCommande() { return dateCommande; }
    public void setDateCommande(LocalDateTime dateCommande) { this.dateCommande = dateCommande; }

    public String getAdresseLivraison() { return adresseLivraison; }
    public void setAdresseLivraison(String adresseLivraison) { this.adresseLivraison = adresseLivraison; }

    public LocalDate getDateLivraison() { return dateLivraison; }
    public void setDateLivraison(LocalDate dateLivraison) { this.dateLivraison = dateLivraison; }

    public List<LigneCommande> getLignes() { return lignes; }
    public void setLignes(List<LigneCommande> lignes) { this.lignes = lignes; }

    public double getPrixTotal() { return prixTotal; }
    public void setPrixTotal(double prixTotal) { this.prixTotal = prixTotal; }
}
