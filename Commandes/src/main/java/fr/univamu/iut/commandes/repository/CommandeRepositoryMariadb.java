package fr.univamu.iut.commandes.repository;

import fr.univamu.iut.commandes.entities.Commande;
import fr.univamu.iut.commandes.entities.LigneCommande;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation MariaDB du repository des commandes.
 * Gère la persistance des commandes et de leurs lignes dans la base de données.
 */
public class CommandeRepositoryMariadb implements CommandeRepositoryInterface {

    private Connection connection;

    /**
     * Constructeur : établit la connexion à la base de données.
     * @param url URL JDBC de la base de données (ex: jdbc:mysql://mysql-xxxxx.alwaysdata.net/xxxxx_commandes)
     * @param user nom d'utilisateur
     * @param password mot de passe
     * @throws SQLException si la connexion échoue
     * @throws ClassNotFoundException si le driver JDBC n'est pas trouvé
     */
    public CommandeRepositoryMariadb(String url, String user, String password)
            throws SQLException, ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        this.connection = DriverManager.getConnection(url, user, password);
    }

    @Override
    public List<Commande> getAllCommandes() {
        List<Commande> commandes = new ArrayList<>();
        String query = "SELECT * FROM commande";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Commande commande = new Commande();
                commande.setId(rs.getInt("id"));
                commande.setAbonneId(rs.getInt("abonne_id"));
                commande.setDateCommande(rs.getTimestamp("date_commande").toLocalDateTime());
                commande.setAdresseLivraison(rs.getString("adresse_livraison"));
                commande.setDateLivraison(rs.getDate("date_livraison").toLocalDate());
                commande.setPrixTotal(rs.getDouble("prix_total"));
                commande.setLignes(getLignesForCommande(commande.getId()));
                commandes.add(commande);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return commandes;
    }

    @Override
    public List<Commande> getCommandesByAbonneId(int abonneId) {
        List<Commande> commandes = new ArrayList<>();
        String query = "SELECT * FROM commande WHERE abonne_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, abonneId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Commande commande = new Commande();
                commande.setId(rs.getInt("id"));
                commande.setAbonneId(rs.getInt("abonne_id"));
                commande.setDateCommande(rs.getTimestamp("date_commande").toLocalDateTime());
                commande.setAdresseLivraison(rs.getString("adresse_livraison"));
                commande.setDateLivraison(rs.getDate("date_livraison").toLocalDate());
                commande.setPrixTotal(rs.getDouble("prix_total"));
                commande.setLignes(getLignesForCommande(commande.getId()));
                commandes.add(commande);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return commandes;
    }

    @Override
    public Commande getCommandeById(int id) {
        String query = "SELECT * FROM commande WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Commande commande = new Commande();
                commande.setId(rs.getInt("id"));
                commande.setAbonneId(rs.getInt("abonne_id"));
                commande.setDateCommande(rs.getTimestamp("date_commande").toLocalDateTime());
                commande.setAdresseLivraison(rs.getString("adresse_livraison"));
                commande.setDateLivraison(rs.getDate("date_livraison").toLocalDate());
                commande.setPrixTotal(rs.getDouble("prix_total"));
                commande.setLignes(getLignesForCommande(commande.getId()));
                return commande;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Commande createCommande(Commande commande) {
        String queryCommande = "INSERT INTO commande (abonne_id, date_commande, adresse_livraison, date_livraison, prix_total) VALUES (?, ?, ?, ?, ?)";
        String queryLigne = "INSERT INTO ligne_commande (commande_id, menu_id, menu_nom, quantite, prix_unitaire, prix_ligne) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(queryCommande, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, commande.getAbonneId());
            stmt.setTimestamp(2, Timestamp.valueOf(commande.getDateCommande()));
            stmt.setString(3, commande.getAdresseLivraison());
            stmt.setDate(4, Date.valueOf(commande.getDateLivraison()));
            stmt.setDouble(5, commande.getPrixTotal());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                commande.setId(generatedKeys.getInt(1));
            }

            for (LigneCommande ligne : commande.getLignes()) {
                try (PreparedStatement stmtLigne = connection.prepareStatement(queryLigne)) {
                    stmtLigne.setInt(1, commande.getId());
                    stmtLigne.setInt(2, ligne.getMenuId());
                    stmtLigne.setString(3, ligne.getMenuNom());
                    stmtLigne.setInt(4, ligne.getQuantite());
                    stmtLigne.setDouble(5, ligne.getPrixUnitaire());
                    stmtLigne.setDouble(6, ligne.getPrixLigne());
                    stmtLigne.executeUpdate();
                }
            }

            return commande;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Commande updateCommande(int id, String adresseLivraison, String dateLivraison) {
        String query = "UPDATE commande SET adresse_livraison = ?, date_livraison = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, adresseLivraison);
            stmt.setDate(2, Date.valueOf(dateLivraison));
            stmt.setInt(3, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                return getCommandeById(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean deleteCommande(int id) {
        String deleteLignes = "DELETE FROM ligne_commande WHERE commande_id = ?";
        String deleteCommande = "DELETE FROM commande WHERE id = ?";

        try {
            try (PreparedStatement stmt = connection.prepareStatement(deleteLignes)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = connection.prepareStatement(deleteCommande)) {
                stmt.setInt(1, id);
                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
        }
    }

    /**
     * Méthode utilitaire pour récupérer les lignes d'une commande.
     * @param commandeId identifiant de la commande
     * @return liste des lignes de commande
     */
    private List<LigneCommande> getLignesForCommande(int commandeId) {
        List<LigneCommande> lignes = new ArrayList<>();
        String query = "SELECT * FROM ligne_commande WHERE commande_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, commandeId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LigneCommande ligne = new LigneCommande();
                ligne.setMenuId(rs.getInt("menu_id"));
                ligne.setMenuNom(rs.getString("menu_nom"));
                ligne.setQuantite(rs.getInt("quantite"));
                ligne.setPrixUnitaire(rs.getDouble("prix_unitaire"));
                ligne.setPrixLigne(rs.getDouble("prix_ligne"));
                lignes.add(ligne);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lignes;
    }
}
