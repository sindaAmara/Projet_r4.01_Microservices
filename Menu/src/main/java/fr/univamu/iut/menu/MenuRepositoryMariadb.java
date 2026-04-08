package fr.univamu.iut.menu;

import fr.univamu.iut.menu.entities.Menu;
import fr.univamu.iut.menu.entities.MenuComplet;
import fr.univamu.iut.menu.entities.PlatSimple;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

public class MenuRepositoryMariadb implements MenuRepositoryInterface {
    private Connection connection;
    private final HttpClient httpClient;
    private final Jsonb jsonb;

    public MenuRepositoryMariadb(String url, String user, String pwd) throws SQLException, ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        connection = DriverManager.getConnection(url, user, pwd);
        this.httpClient = HttpClient.newHttpClient();
        this.jsonb = JsonbBuilder.create();
    }

    @Override
    public List<Menu> getAllMenus() {
        List<Menu> list = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM Menu");
            while (rs.next()) {
                int menuId = rs.getInt("id");
                List<Integer> platsIds = fetchPlatsIds(menuId);
                String dateMiseAJour = null;
                try { dateMiseAJour = rs.getString("dateMiseAJour"); } catch(Exception ignored) {}
                list.add(new Menu(menuId, rs.getString("nomMenu"),
                        rs.getInt("createurId"), rs.getString("dateCreation"), dateMiseAJour, platsIds));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public Menu getMenu(int id) {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Menu WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int menuId = rs.getInt("id");
                List<Integer> platsIds = fetchPlatsIds(menuId);
                String dateMiseAJour = null;
                try { dateMiseAJour = rs.getString("dateMiseAJour"); } catch(Exception ignored) {}
                return new Menu(menuId, rs.getString("nomMenu"),
                        rs.getInt("createurId"), rs.getString("dateCreation"), dateMiseAJour, platsIds);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    private List<Integer> fetchPlatsIds(int menuId) {
        List<Integer> platsIds = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT platId FROM Menu_Plat WHERE menuId = ?")) {
            stmt.setInt(1, menuId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                platsIds.add(rs.getInt("platId"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return platsIds;
    }

    @Override
    public int saveMenu(Menu menu) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO Menu (nomMenu, createurId, dateCreation, dateMiseAJour) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, menu.getNomMenu());
            stmt.setInt(2, menu.getCreateurId());
            stmt.setString(3, menu.getDateCreation());
            stmt.setString(4, menu.getDateMiseAJour());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int menuId = rs.getInt(1);
                if (menu.getPlatsIds() != null) {
                    try (PreparedStatement platStmt = connection.prepareStatement("INSERT INTO Menu_Plat (menuId, platId) VALUES (?, ?)")) {
                        for (Integer platId : menu.getPlatsIds()) {
                            platStmt.setInt(1, menuId);
                            platStmt.setInt(2, platId);
                            platStmt.addBatch();
                        }
                        platStmt.executeBatch();
                    }
                }
                return menuId;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    @Override
    public boolean updateMenu(Menu menu) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "UPDATE Menu SET nomMenu = ?, dateMiseAJour = ? WHERE id = ?")) {
            stmt.setString(1, menu.getNomMenu());
            stmt.setString(2, menu.getDateMiseAJour());
            stmt.setInt(3, menu.getId());
            int affected = stmt.executeUpdate();
            if (affected > 0) {
                // Delete old plats
                try(PreparedStatement delStmt = connection.prepareStatement("DELETE FROM Menu_Plat WHERE menuId = ?")) {
                    delStmt.setInt(1, menu.getId());
                    delStmt.executeUpdate();
                }
                // Insert new plats
                if (menu.getPlatsIds() != null && !menu.getPlatsIds().isEmpty()) {
                    try (PreparedStatement platStmt = connection.prepareStatement("INSERT INTO Menu_Plat (menuId, platId) VALUES (?, ?)")) {
                        for (Integer platId : menu.getPlatsIds()) {
                            platStmt.setInt(1, menu.getId());
                            platStmt.setInt(2, platId);
                            platStmt.addBatch();
                        }
                        platStmt.executeBatch();
                    }
                }
                return true;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean deleteMenu(int id) {
        try {
            // Delete plats first to respect potential constraints
            try(PreparedStatement delStmt = connection.prepareStatement("DELETE FROM Menu_Plat WHERE menuId = ?")) {
                delStmt.setInt(1, id);
                delStmt.executeUpdate();
            }
            try(PreparedStatement stmt = connection.prepareStatement("DELETE FROM Menu WHERE id = ?")) {
                stmt.setInt(1, id);
                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public void close() {
        try { if (connection != null) connection.close(); }
        catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public List<MenuComplet> getAllMenusComplets() {
        List<MenuComplet> list = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM Menu");
            while (rs.next()) {
                int menuId = rs.getInt("id");
                int createurId = rs.getInt("createurId");
                
                String createurNom = fetchUtilisateurNom(createurId);
                List<PlatSimple> plats = getPlatsByMenuId(menuId);
                double prixTotal = plats.stream().mapToDouble(PlatSimple::getPrix).sum();

                String dateMiseAJour = null;
                try { dateMiseAJour = rs.getString("dateMiseAJour"); } catch(Exception ignored) {}

                MenuComplet menu = new MenuComplet(
                    menuId,
                    rs.getString("nomMenu"),
                    createurId,
                    createurNom,
                    rs.getString("dateCreation"),
                    dateMiseAJour,
                    plats,
                    prixTotal
                );
                list.add(menu);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public MenuComplet getMenuComplet(int id) {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Menu WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int menuId = rs.getInt("id");
                int createurId = rs.getInt("createurId");
                
                String createurNom = fetchUtilisateurNom(createurId);
                List<PlatSimple> plats = getPlatsByMenuId(menuId);
                double prixTotal = plats.stream().mapToDouble(PlatSimple::getPrix).sum();

                String dateMiseAJour = null;
                try { dateMiseAJour = rs.getString("dateMiseAJour"); } catch(Exception ignored) {}

                return new MenuComplet(
                    menuId,
                    rs.getString("nomMenu"),
                    createurId,
                    createurNom,
                    rs.getString("dateCreation"),
                    dateMiseAJour,
                    plats,
                    prixTotal
                );
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    private List<PlatSimple> getPlatsByMenuId(int menuId) {
        List<PlatSimple> plats = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT platId FROM Menu_Plat WHERE menuId = ?")) {
            stmt.setInt(1, menuId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int platId = rs.getInt("platId");
                PlatSimple plat = fetchPlat(platId);
                if (plat != null) {
                    plats.add(plat);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return plats;
    }

    private String fetchUtilisateurNom(int utilisateurId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3003/utilisateurs/" + utilisateurId))
                .GET()
                .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return extractJsonField(response.body(), "nom");
            }
        } catch (Exception e) {
            System.err.println("Could not fetch User " + utilisateurId + ": " + e.getMessage());
        }
        return "Inconnu";
    }

    private PlatSimple fetchPlat(int platId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3003/plats/" + platId))
                .GET()
                .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                String nom = extractJsonField(response.body(), "nom");
                String prixStr = extractJsonField(response.body(), "prix");
                double prix = prixStr != null ? Double.parseDouble(prixStr) : 0.0;
                return new PlatSimple(platId, nom, prix);
            }
        } catch (Exception e) {
             System.err.println("Could not fetch Plat " + platId + ": " + e.getMessage());
        }
        return null;
    }

    private String extractJsonField(String json, String field) {
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("\"" + field + "\"\\s*:\\s*\"?([^,\"}]+)\"?");
        java.util.regex.Matcher m = p.matcher(json);
        if (m.find()) return m.group(1);
        return null;
    }
}