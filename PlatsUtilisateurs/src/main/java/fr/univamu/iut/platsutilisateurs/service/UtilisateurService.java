package fr.univamu.iut.platsutilisateurs.service;


import fr.univamu.iut.platsutilisateurs.model.entite.Utilisateur;
import fr.univamu.iut.platsutilisateurs.repository.UtilisateurRepository;

import java.util.List;

public class UtilisateurService {

    private final UtilisateurRepository repository;

    public UtilisateurService() {
        this.repository = new UtilisateurRepository();
    }

    public List<Utilisateur> getAllUtilisateurs() {
        return repository.getAllUtilisateurs();
    }

    public Utilisateur getUtilisateur(int id) {
        return repository.getUtilisateurById(id);
    }

    public boolean createUtilisateur(Utilisateur utilisateur) {
        // Exemple de règle métier : nom/prénom non vide
        if (utilisateur.getNom().isEmpty() || utilisateur.getPrenom().isEmpty()) {
            System.err.println("Nom ou prénom invalide");
            return false;
        }

        return repository.addUtilisateur(utilisateur);
    }

    public boolean updateUtilisateur(Utilisateur utilisateur) {
        return repository.updateUtilisateur(utilisateur);
    }

    public boolean deleteUtilisateur(int id) {
        return repository.deleteUtilisateur(id);
    }
}