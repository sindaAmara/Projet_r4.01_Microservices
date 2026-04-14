package fr.univamu.iut.platsutilisateurs.resource;

import fr.univamu.iut.platsutilisateurs.model.entite.Utilisateur;
import fr.univamu.iut.platsutilisateurs.service.UtilisateurService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/utilisateurs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UtilisateurResource {

    @Inject
    private UtilisateurService utilisateurService;

    @GET
    public List<Utilisateur> getAll() {
        return utilisateurService.getAllUtilisateurs();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id) {
        Utilisateur u = utilisateurService.getUtilisateur(id);
        if (u == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(u).build();
    }

    @POST
    public Response create(Utilisateur utilisateur) {
        boolean ok = utilisateurService.createUtilisateur(utilisateur);
        if (!ok) return Response.status(Response.Status.CONFLICT)
                .entity("{\"message\":\"Email déjà existant\"}")
                .build();
        return Response.status(Response.Status.CREATED).entity(utilisateur).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Utilisateur utilisateur) {
        utilisateur.setId(id);
        boolean ok = utilisateurService.updateUtilisateur(utilisateur);
        if (!ok) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(utilisateur).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        boolean ok = utilisateurService.deleteUtilisateur(id);
        if (!ok) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.noContent().build();
    }
}