package fr.univamu.iut.platsutilisateurs.controller;


import fr.univamu.iut.platsutilisateurs.model.entite.Utilisateur;
import fr.univamu.iut.platsutilisateurs.service.UtilisateurService;

public class UtilisateurController {

    public static void main(String[] args) {
        UtilisateurService service = new UtilisateurService();

        service.getAllUtilisateurs().forEach(u ->
                System.out.println(u.getNom() + " " + u.getPrenom() + " (" + u.getEmail() + ")"));

        Utilisateur newUser = new Utilisateur(0, "Test", "User", "test.user@email.fr", "123 rue Exemple");
        service.createUtilisateur(newUser);

        Utilisateur u = service.getUtilisateur(1);
        if (u != null) System.out.println("Utilisateur 1: " + u.getNom());
    }
}