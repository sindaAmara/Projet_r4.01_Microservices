package fr.univamu.iut.menu.entities;

import java.util.List;

public class MenuComplet {
    private int id;
    private String nom;
    private int createurId;
    private String createurNom;
    private String dateCreation;
    private String dateMiseAJour;
    private List<PlatSimple> plats;
    private double prixTotal;

    public MenuComplet() {}

    public MenuComplet(int id, String nom, int createurId, String createurNom, 
                      String dateCreation, String dateMiseAJour, 
                      List<PlatSimple> plats, double prixTotal) {
        this.id = id;
        this.nom = nom;
        this.createurId = createurId;
        this.createurNom = createurNom;
        this.dateCreation = dateCreation;
        this.dateMiseAJour = dateMiseAJour;
        this.plats = plats;
        this.prixTotal = prixTotal;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public int getCreateurId() { return createurId; }
    public void setCreateurId(int createurId) { this.createurId = createurId; }
    
    public String getCreateurNom() { return createurNom; }
    public void setCreateurNom(String createurNom) { this.createurNom = createurNom; }
    
    public String getDateCreation() { return dateCreation; }
    public void setDateCreation(String dateCreation) { this.dateCreation = dateCreation; }
    
    public String getDateMiseAJour() { return dateMiseAJour; }
    public void setDateMiseAJour(String dateMiseAJour) { this.dateMiseAJour = dateMiseAJour; }
    
    public List<PlatSimple> getPlats() { return plats; }
    public void setPlats(List<PlatSimple> plats) { this.plats = plats; }
    
    public double getPrixTotal() { return prixTotal; }
    public void setPrixTotal(double prixTotal) { this.prixTotal = prixTotal; }
}

