package fr.univamu.iut.platsutilisateurs.repository;

import fr.univamu.iut.platsutilisateurs.model.entite.Plat;
import fr.univamu.iut.platsutilisateurs.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlatRepository {

    public List<Plat> getAllPlats() {
        List<Plat> plats = new ArrayList<>();
        String sql = "SELECT id, nom, description, prix FROM plat";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                plats.add(new Plat(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getDouble("prix")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plats;
    }

    public Plat getPlatById(int id) {
        String sql = "SELECT id, nom, description, prix FROM plat WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Plat(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("description"),
                            rs.getDouble("prix")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addPlat(Plat plat) {
        String sql = "INSERT INTO plat (nom, description, prix) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, plat.getNom());
            ps.setString(2, plat.getDescription());
            ps.setDouble(3, plat.getPrix());
            int rows = ps.executeUpdate();
            if (rows == 0) return false;
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) plat.setId(keys.getInt(1));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePlat(Plat plat) {
        String sql = "UPDATE plat SET nom=?, description=?, prix=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, plat.getNom());
            ps.setString(2, plat.getDescription());
            ps.setDouble(3, plat.getPrix());
            ps.setInt(4, plat.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePlat(int id) {
        String sql = "DELETE FROM plat WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}