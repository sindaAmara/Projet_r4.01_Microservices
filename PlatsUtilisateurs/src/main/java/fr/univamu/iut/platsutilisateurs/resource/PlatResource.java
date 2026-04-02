package fr.univamu.iut.platsutilisateurs.resource;

import fr.univamu.iut.platsutilisateurs.model.entite.Plat;
import fr.univamu.iut.platsutilisateurs.service.PlatService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/plats")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlatResource {

    @Inject
    private PlatService platService;

    @GET
    public List<Plat> getAll() {
        return platService.getPlats();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id) {
        Plat p = platService.getPlat(id);
        if (p == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(p).build();
    }

    @POST
    public Response create(Plat plat) {
        boolean ok = platService.createPlat(plat);
        if (!ok) return Response.status(Response.Status.BAD_REQUEST).build();
        return Response.status(Response.Status.CREATED).entity(plat).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Plat plat) {
        plat.setId(id);
        boolean ok = platService.updatePlat(plat);
        if (!ok) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(plat).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        boolean ok = platService.deletePlat(id);
        if (!ok) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.noContent().build();
    }
}