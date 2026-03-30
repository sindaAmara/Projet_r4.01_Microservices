package fr.univamu.iut.menu.resources;

import fr.univamu.iut.menu.MenuRepositoryInterface;
import fr.univamu.iut.menu.entities.Menu;
import jakarta.enterprise.context.RequestScoped; // IMPORTANT
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/menus")
@RequestScoped // AJOUTE CECI
@Produces(MediaType.APPLICATION_JSON)
public class MenuResource {

    @Inject
    MenuRepositoryInterface menuRepo;

    @GET
    public List<Menu> getAllMenus() {
        // Si menuRepo est toujours null, on verra l'erreur ici
        return menuRepo.getAllMenus();
    }

    @GET
    @Path("/{id}")
    public Response getMenuById(@PathParam("id") int id) {
        Menu m = menuRepo.getMenu(id);
        if (m == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(m).build();
    }

    @POST
    public Response createMenu(Menu nouveauMenu) {
        int id = menuRepo.saveMenu(nouveauMenu);
        nouveauMenu.setId(id);
        return Response.status(Response.Status.CREATED).entity(nouveauMenu).build();
    }
}