package fr.univamu.iut.platsutilisateurs.repository;

import fr.univamu.iut.platsutilisateurs.model.entite.Plat;
import fr.univamu.iut.platsutilisateurs.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PlatRepository {

    public List<Plat> getAllPlats() {
        List<Plat> plats = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT id, nom, description, prix FROM plat");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                plats.add(new Plat(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getDouble("prix")
                ));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return plats;
    }
}