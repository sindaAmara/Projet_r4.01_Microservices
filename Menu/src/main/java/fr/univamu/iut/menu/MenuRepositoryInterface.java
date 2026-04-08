package fr.univamu.iut.menu;

import fr.univamu.iut.menu.entities.Menu;
import fr.univamu.iut.menu.entities.MenuComplet;

import java.util.List;

public interface MenuRepositoryInterface {
    public List<Menu> getAllMenus();
    public Menu getMenu(int id);
    public int saveMenu(Menu menu);
    public boolean updateMenu(Menu menu);
    public boolean deleteMenu(int id);
    public void close();
    public List<MenuComplet> getAllMenusComplets();
    public MenuComplet getMenuComplet(int id);
}