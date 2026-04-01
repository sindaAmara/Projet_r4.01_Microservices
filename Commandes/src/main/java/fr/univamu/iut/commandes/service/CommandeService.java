package fr.univamu.iut.commandes.service;

import fr.univamu.iut.commandes.dto.CommandeInput;
import fr.univamu.iut.commandes.dto.CommandeUpdateInput;
import fr.univamu.iut.commandes.dto.LigneCommandeInput;
import fr.univamu.iut.commandes.entities.Commande;
import fr.univamu.iut.commandes.entities.LigneCommande;
import fr.univamu.iut.commandes.repository.CommandeRepositoryInterface;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service métier pour la gestion des commandes.
 * Contient la logique applicative : validation, calcul des prix,
 * et délégation au repository pour la persistance.
 */
public class CommandeService {

    private final CommandeRepositoryInterface commandeRepo;

    /**
     * Constructeur.
     * @param commandeRepo repository pour l'accès aux données
     */
    public CommandeService(CommandeRepositoryInterface commandeRepo) {
        this.commandeRepo = commandeRepo;
    }

    /**
     * Récupère toutes les commandes au format JSON.
     * @return JSON de la liste des commandes
     */
    public String getAllCommandesJSON() {
        List<Commande> commandes = commandeRepo.getAllCommandes();
        try (Jsonb jsonb = JsonbBuilder.create()) {
            return jsonb.toJson(commandes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Récupère les commandes d'un abonné au format JSON.
     * @param abonneId identifiant de l'abonné
     * @return JSON de la liste des commandes
     */
    public String getCommandesByAbonneIdJSON(int abonneId) {
        List<Commande> commandes = commandeRepo.getCommandesByAbonneId(abonneId);
        try (Jsonb jsonb = JsonbBuilder.create()) {
            return jsonb.toJson(commandes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Récupère une commande par son id au format JSON.
     * @param id identifiant de la commande
     * @return JSON de la commande ou null si introuvable
     */
    public String getCommandeByIdJSON(int id) {
        Commande commande = commandeRepo.getCommandeById(id);
        if (commande == null) return null;
        try (Jsonb jsonb = JsonbBuilder.create()) {
            return jsonb.toJson(commande);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Crée une nouvelle commande.
     * @param input les données de la commande
     * @return la commande créée ou null si erreur
     */
    public Commande createCommande(CommandeInput input) {
        if (input.getLignes() == null || input.getLignes().isEmpty()) return null;

        LocalDate dateLivraison = LocalDate.parse(input.getDateLivraison());
        if (!dateLivraison.isAfter(LocalDate.now())) return null;

        List<LigneCommande> lignes = new ArrayList<>();
        double prixTotal = 0;
        for (LigneCommandeInput ligneInput : input.getLignes()) {
            if (ligneInput.getQuantite() <= 0) return null;
            LigneCommande ligne = new LigneCommande();
            ligne.setMenuId(ligneInput.getMenuId());
            ligne.setMenuNom(ligneInput.getMenuNom());
            ligne.setQuantite(ligneInput.getQuantite());
            ligne.setPrixUnitaire(ligneInput.getPrixUnitaire());
            double prixLigne = ligneInput.getPrixUnitaire() * ligneInput.getQuantite();
            ligne.setPrixLigne(prixLigne);
            prixTotal += prixLigne;
            lignes.add(ligne);
        }

        Commande commande = new Commande();
        commande.setAbonneId(input.getAbonneId());
        commande.setDateCommande(LocalDateTime.now());
        commande.setAdresseLivraison(input.getAdresseLivraison());
        commande.setDateLivraison(dateLivraison);
        commande.setLignes(lignes);
        commande.setPrixTotal(prixTotal);

        return commandeRepo.createCommande(commande);
    }

    /**
     * Met à jour une commande (adresse et date de livraison uniquement).
     * @param id identifiant de la commande
     * @param input les nouvelles données
     * @return la commande mise à jour ou null si introuvable
     */
    public Commande updateCommande(int id, CommandeUpdateInput input) {
        LocalDate dateLivraison = LocalDate.parse(input.getDateLivraison());
        if (!dateLivraison.isAfter(LocalDate.now())) return null;

        return commandeRepo.updateCommande(id, input.getAdresseLivraison(), input.getDateLivraison());
    }

    /**
     * Supprime une commande.
     * @param id identifiant de la commande
     * @return true si supprimée, false si introuvable
     */
    public boolean deleteCommande(int id) {
        return commandeRepo.deleteCommande(id);
    }
}
