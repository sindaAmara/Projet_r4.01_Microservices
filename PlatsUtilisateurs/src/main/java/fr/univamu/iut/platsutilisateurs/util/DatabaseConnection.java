package fr.univamu.iut.platsutilisateurs.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseConnection {

    private static Map<String, String> env = new HashMap<>();

    static {
        String envPath = "/amuhome/a24029395/microservices/Projet_r4.01_Microservices/PlatsUtilisateurs/.env";
        try (BufferedReader br = new BufferedReader(new FileReader(envPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.contains("=")) {
                    String[] parts = line.split("=", 2);
                    env.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Impossible de lire le fichier .env : " + envPath);
        }
    }

    public static Connection getConnection() {
        try {
            String host = env.get("DB_HOST");
            String dbName = env.get("DB_NAME");
            String user = env.get("DB_USER");
            String password = env.get("DB_PASSWORD");

            System.out.println("HOST=" + host + ", DB=" + dbName + ", USER=" + user);

            String url = "jdbc:mariadb://" + host + ":3306/" + dbName + "?useSSL=false&serverTimezone=UTC";
            Class.forName("org.mariadb.jdbc.Driver");

            return DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Erreur de connexion à la DB");
            return null;
        }
    }
}