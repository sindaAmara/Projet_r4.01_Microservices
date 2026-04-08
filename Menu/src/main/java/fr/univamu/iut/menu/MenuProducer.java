package fr.univamu.iut.menu;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class MenuProducer {

    @Produces
    @ApplicationScoped
    public MenuRepositoryInterface openDbConnection() {
        try {
            return new MenuRepositoryMariadb(
                    "jdbc:mariadb://mysql-mrcoton.alwaysdata.net/mrcoton_menus",
                    "mrcoton_annonces",
                    "ouiouibaguette"
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Impossible de se connecter à la DB AlwaysData : " + e.getMessage());
        }
    }

    public void closeDbConnection(@Disposes MenuRepositoryInterface repo) {
        if (repo != null) repo.close();
    }
}