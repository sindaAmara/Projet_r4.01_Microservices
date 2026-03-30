package fr.univamu.iut.menu;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
public class RestApplication extends Application {
    // Vide, sert juste à activer JAX-RS sous le préfixe /api
}