package fr.univamu.iut.platsutilisateurs.service;

import fr.univamu.iut.platsutilisateurs.model.entite.Plat;
import fr.univamu.iut.platsutilisateurs.repository.PlatRepository;
import java.util.List;

public class PlatService {

    private PlatRepository repository = new PlatRepository();

    public List<Plat> getPlats() {
        return repository.getAllPlats();
    }
}