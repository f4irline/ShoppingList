package fi.projects.fairline.app;

import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

class AppMenu extends MenuBar {
    Menu menuFile;
    Menu menuAbout;

    public AppMenu () {
        menuFile = createFileMenu();
        menuAbout = createAboutMenu();
        getMenus().addAll(menuFile, menuAbout);
    }

    private Menu createFileMenu() {
        Menu menuFile = new Menu("File");

        MenuItem exit = new MenuItem("Exit");
        
        menuFile.getItems().addAll(exit);
        return menuFile;
    }

    private Menu createAboutMenu() {
        Menu menuAbout = new Menu("About");
        MenuItem about = new MenuItem("About Lotto App");
        menuAbout.getItems().addAll(about);
        return menuAbout;
    }

    public MenuItem getExitButton() {
        return menuFile.getItems().get(0);
    }
}