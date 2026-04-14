package fr.univamu.iut.platsutilisateurs.service;

import fr.univamu.iut.platsutilisateurs.model.entite.Plat;
import fr.univamu.iut.platsutilisateurs.repository.PlatRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class PlatService {

    private PlatRepository repository = new PlatRepository();

    public List<Plat> getPlats() {
        return repository.getAllPlats();
    }

    public Plat getPlat(int id) {
        return repository.getPlatById(id);
    }

    public boolean createPlat(Plat plat) {
        return repository.addPlat(plat);
    }

    public boolean updatePlat(Plat plat) {
        return repository.updatePlat(plat);
    }

    public boolean deletePlat(int id) {
        return repository.deletePlat(id);
    }
}