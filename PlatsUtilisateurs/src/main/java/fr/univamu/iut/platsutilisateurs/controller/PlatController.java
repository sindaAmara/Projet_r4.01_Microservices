package fr.univamu.iut.platsutilisateurs.controller;

import fr.univamu.iut.platsutilisateurs.model.entite.Plat;
import fr.univamu.iut.platsutilisateurs.service.PlatService;
import java.util.List;

public class PlatController {
    public static void main(String[] args) {
        PlatService service = new PlatService();
        List<Plat> plats = service.getPlats();
        for(Plat p : plats) {
            System.out.println("Plat : " + p.getNom());
        }
    }
}