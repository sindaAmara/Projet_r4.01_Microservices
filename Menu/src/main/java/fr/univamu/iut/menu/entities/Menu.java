package fr.univamu.iut.menu.entities;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private int id;
    private String nomMenu;
    private int createurId;
    private String dateCreation; // On ajoute la date
    private List<Integer> platsIds;

    public Menu() {
        this.platsIds = new ArrayList<>();
    }

    public Menu(int id, String nomMenu, int createurId, String dateCreation, List<Integer> platsIds) {
        this.id = id;
        this.nomMenu = nomMenu;
        this.createurId = createurId;
        this.dateCreation = dateCreation;
        this.platsIds = platsIds;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNomMenu() { return nomMenu; }
    public void setNomMenu(String nomMenu) { this.nomMenu = nomMenu; }
    public int getCreateurId() { return createurId; }
    public void setCreateurId(int createurId) { this.createurId = createurId; }
    public String getDateCreation() { return dateCreation; }
    public void setDateCreation(String dateCreation) { this.dateCreation = dateCreation; }
    public List<Integer> getPlatsIds() { return platsIds; }
    public void setPlatsIds(List<Integer> platsIds) { this.platsIds = platsIds; }
}