package fr.univamu.iut.commandes.resources;

import fr.univamu.iut.commandes.dto.CommandeInput;
import fr.univamu.iut.commandes.dto.CommandeUpdateInput;
import fr.univamu.iut.commandes.entities.Commande;
import fr.univamu.iut.commandes.repository.CommandeRepositoryMariadb;
import fr.univamu.iut.commandes.service.CommandeService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Resource REST JAX-RS pour les opérations CRUD sur les commandes.
 * Endpoints exposés sous /commandes.
 */
@Path("/commandes")
public class CommandeResource {

    private CommandeService service;

    /**
     * Constructeur par défaut.
     * Initialise le service avec le repository MySQL.
     */
    public CommandeResource() {
        try {
            CommandeRepositoryMariadb commandeRepo = new CommandeRepositoryMariadb(
                    "jdbc:mariadb://mysql-mrcoton.alwaysdata.net:3306/mrcoton_menus",
                    "mrcoton",
                    "ouiouibaguette"
            );
            this.service = new CommandeService(commandeRepo);
        } catch (Exception e) {
            System.err.println("Erreur connexion BDD : " + e.getMessage());
        }
    }

    /**
     * GET /commandes
     * Lister toutes les commandes, avec filtre optionnel par abonneId.
     * @param abonneId filtre optionnel sur l'identifiant de l'abonné
     * @return 200 avec la liste des commandes en JSON
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCommandes(@QueryParam("abonneId") Integer abonneId) {
        String json;
        if (abonneId != null) {
            json = service.getCommandesByAbonneIdJSON(abonneId);
        } else {
            json = service.getAllCommandesJSON();
        }
        return Response.ok(json).build();
    }

    /**
     * GET /commandes/{id}
     * Obtenir une commande par son identifiant.
     * @param id identifiant de la commande
     * @return 200 avec la commande en JSON, ou 404 si introuvable
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommandeById(@PathParam("id") int id) {
        String json = service.getCommandeByIdJSON(id);
        if (json == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(json).build();
    }

    /**
     * POST /commandes
     * Créer une nouvelle commande.
     * @param input les données de la commande
     * @return 201 avec la commande créée et header Location, ou 400/404 en cas d'erreur
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCommande(CommandeInput input) {
        Commande commande = service.createCommande(input);
        if (commande == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED)
                .header("Location", "/commandes/" + commande.getId())
                .entity(commande)
                .build();
    }

    /**
     * PUT /commandes/{id}
     * Modifier une commande (adresse et date de livraison).
     * @param id identifiant de la commande
     * @param input les nouvelles données
     * @return 200 avec la commande mise à jour, ou 404 si introuvable
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCommande(@PathParam("id") int id, CommandeUpdateInput input) {
        Commande commande = service.updateCommande(id, input);
        if (commande == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(commande).build();
    }

    /**
     * DELETE /commandes/{id}
     * Annuler (supprimer) une commande.
     * @param id identifiant de la commande
     * @return 204 si supprimée, 404 si introuvable
     */
    @DELETE
    @Path("/{id}")
    public Response deleteCommande(@PathParam("id") int id) {
        boolean deleted = service.deleteCommande(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
