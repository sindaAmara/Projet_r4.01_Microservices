package fr.univamu.iut.commandes;

import fr.univamu.iut.commandes.repository.CommandeRepositoryMariadb;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/hello-world")
public class HelloResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        try {
            CommandeRepositoryMariadb repo = new CommandeRepositoryMariadb(
                    "jdbc:mariadb://mysql-mrcoton.alwaysdata.net/mrcoton_menus",
                    "mrcoton_annonces",
                    "ouiouibaguette"
            );
            repo.close();
            return "Connexion BDD OK !";
        } catch (Exception e) {
            return "Erreur connexion BDD : " + e.getMessage();
        }
    }
}