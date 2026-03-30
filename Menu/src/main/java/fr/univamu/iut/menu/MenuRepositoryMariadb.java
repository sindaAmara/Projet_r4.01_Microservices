package fr.univamu.iut.menu;

import fr.univamu.iut.menu.entities.Menu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuRepositoryMariadb implements MenuRepositoryInterface {
    private Connection connection;

    public MenuRepositoryMariadb(String url, String user, String pwd) throws SQLException, ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        connection = DriverManager.getConnection(url, user, pwd);
    }

    @Override
    public List<Menu> getAllMenus() {
        List<Menu> list = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM Menu");
            while (rs.next()) {
                list.add(new Menu(rs.getInt("id"), rs.getString("nomMenu"),
                        rs.getInt("createurId"), rs.getString("dateCreation"), new ArrayList<>()));
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
                return new Menu(rs.getInt("id"), rs.getString("nomMenu"),
                        rs.getInt("createurId"), rs.getString("dateCreation"), new ArrayList<>());
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public int saveMenu(Menu menu) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO Menu (nomMenu, createurId, dateCreation) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, menu.getNomMenu());
            stmt.setInt(2, menu.getCreateurId());
            stmt.setString(3, menu.getDateCreation());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    @Override
    public void close() {
        try { if (connection != null) connection.close(); }
        catch (SQLException e) { e.printStackTrace(); }
    }
}