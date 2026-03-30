package fr.univamu.iut.menu;

import fr.univamu.iut.menu.entities.Menu;

import java.util.List;

public interface MenuRepositoryInterface {
    public List<Menu> getAllMenus();
    public Menu getMenu(int id);
    public int saveMenu(Menu menu);
    public void close();
}