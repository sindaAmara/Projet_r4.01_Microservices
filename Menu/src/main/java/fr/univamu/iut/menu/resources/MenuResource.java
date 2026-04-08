package fr.univamu.iut.menu.resources;

import fr.univamu.iut.menu.MenuRepositoryInterface;
import fr.univamu.iut.menu.entities.Menu;
import fr.univamu.iut.menu.entities.MenuComplet;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/menus")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MenuResource {

    @Inject
    MenuRepositoryInterface menuRepo;

    @GET
    public List<MenuComplet> getAllMenus() {
        return menuRepo.getAllMenusComplets();
    }

    @GET
    @Path("/{id}")
    public Response getMenuById(@PathParam("id") int id) {
        MenuComplet m = menuRepo.getMenuComplet(id);
        if (m == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(m).build();
    }

    @POST
    public Response createMenu(Menu nouveauMenu) {
        int id = menuRepo.saveMenu(nouveauMenu);
        nouveauMenu.setId(id);
        return Response.status(Response.Status.CREATED).entity(nouveauMenu).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateMenu(@PathParam("id") int id, Menu menuExt) {
        menuExt.setId(id);
        boolean updated = menuRepo.updateMenu(menuExt);
        if(!updated) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(menuRepo.getMenuComplet(id)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteMenu(@PathParam("id") int id) {
        boolean deleted = menuRepo.deleteMenu(id);
        if(!deleted) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.noContent().build();
    }
}