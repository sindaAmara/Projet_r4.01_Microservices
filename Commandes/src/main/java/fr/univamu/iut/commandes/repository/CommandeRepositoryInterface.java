package fr.univamu.iut.commandes.repository;

import fr.univamu.iut.commandes.entities.Commande;

import java.util.List;

/**
 * Interface définissant les opérations d'accès aux données pour les commandes.
 */
public interface CommandeRepositoryInterface {

    /**
     * Récupère toutes les commandes.
     * @return liste de toutes les commandes
     */
    List<Commande> getAllCommandes();

    /**
     * Récupère les commandes d'un abonné spécifique.
     * @param abonneId identifiant de l'abonné
     * @return liste des commandes de l'abonné
     */
    List<Commande> getCommandesByAbonneId(int abonneId);

    /**
     * Récupère une commande par son identifiant.
     * @param id identifiant de la commande
     * @return la commande ou null si introuvable
     */
    Commande getCommandeById(int id);

    /**
     * Crée une nouvelle commande en base de données.
     * @param commande la commande à créer
     * @return la commande créée avec son id généré
     */
    Commande createCommande(Commande commande);

    /**
     * Met à jour l'adresse et la date de livraison d'une commande.
     * @param id identifiant de la commande
     * @param adresseLivraison nouvelle adresse
     * @param dateLivraison nouvelle date de livraison
     * @return la commande mise à jour ou null si introuvable
     */
    Commande updateCommande(int id, String adresseLivraison, String dateLivraison);

    /**
     * Supprime une commande.
     * @param id identifiant de la commande
     * @return true si supprimée, false si introuvable
     */
    boolean deleteCommande(int id);

    /**
     * Ferme la connexion à la base de données.
     */
    void close();
}
