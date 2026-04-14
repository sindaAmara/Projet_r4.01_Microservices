package fr.univamu.iut.platsutilisateurs.service;

import fr.univamu.iut.platsutilisateurs.model.entite.Utilisateur;
import fr.univamu.iut.platsutilisateurs.repository.UtilisateurRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class UtilisateurService {

    private UtilisateurRepository repository = new UtilisateurRepository();

    public List<Utilisateur> getAllUtilisateurs() {
        return repository.getAllUtilisateurs();
    }

    public Utilisateur getUtilisateur(int id) {
        return repository.getUtilisateurById(id);
    }

    public boolean createUtilisateur(Utilisateur utilisateur) {
        return repository.addUtilisateur(utilisateur);
    }

    public boolean updateUtilisateur(Utilisateur utilisateur) {
        return repository.updateUtilisateur(utilisateur);
    }

    public boolean deleteUtilisateur(int id) {
        return repository.deleteUtilisateur(id);
    }
}