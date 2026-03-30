package fr.univamu.iut.platsutilisateurs.model.entite;

public class Plat {
    private int id;
    private String nom;
    private String description;
    private double prix;

    public Plat(int id, String nom, String description, double prix) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
    }

    // getters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getDescription() { return description; }
    public double getPrix() { return prix; }
}