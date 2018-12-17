package com.github.f4irline.app.containers;

import com.github.f4irline.app.components.Utils;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * A container for the shopping list application's menu items.
 * 
 * @author Tommi Lepola
 * @version 1.0
 * @since 2018.1216
 */
public class AppMenu extends MenuBar {
    Menu menuFile;
    Menu menuAbout;

    /**
     * Initializes the menu bar by adding the file menu and about menu to it.
     */
    public AppMenu () {
        menuFile = createFileMenu();
        menuAbout = createAboutMenu();
        getMenus().addAll(menuFile, menuAbout);
    }

    /**
     * Creates the file menu.
     * 
     * @return - file menu.
     */
    private Menu createFileMenu() {
        Menu menuFile = new Menu("File");

        MenuItem save = new MenuItem("Save");
        MenuItem load = new MenuItem("Load");
        MenuItem exit = new MenuItem("Exit");
        
        menuFile.getItems().addAll(save, load, exit);
        return menuFile;
    }

    /**
     * Creates the about menu.
     * 
     * @return - about menu.
     */
    private Menu createAboutMenu() {
        Menu menuAbout = new Menu("About");
        MenuItem about = new MenuItem("About This App");
        about.setOnAction((e) -> {
            Utils.createNewAlert("Shopping List Application", "\u00A9 Tommi Lepola 2018", "A simple shopping list application.", AlertType.INFORMATION);
        });
        menuAbout.getItems().addAll(about);
        return menuAbout;
    }

    /**
     * Returns the save button from the file menu.
     * 
     * @return - the save button from the file menu.
     */
    public MenuItem getSaveButton() {
        return menuFile.getItems().get(0);
    }

    /**
     * Returns the load button from the file menu.
     * 
     * @return - the load button from the file menu.
     */
    public MenuItem getLoadButton() {
        return menuFile.getItems().get(1);
    }

    /**
     * Returns the exit button from the file menu.
     * 
     * @return - the exit button from the file menu.
     */
    public MenuItem getExitButton() {
        return menuFile.getItems().get(2);
    }
}