package com.github.f4irline.app;

import com.github.f4irline.app.components.*;
import com.github.f4irline.app.containers.*;
import com.github.f4irline.ezparser.EzParser;

import javafx.application.Platform;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map;
import java.util.ArrayList;

/**
 * Graphical user interface implementation of the shopping list application.
 * 
 * <p>
 * Graphical user interface implementation of the shopping list application using 
 * the JavaFX library for the UI components.
 * </p>
 * 
 * <p>
 * Uses a JSON parser "EzParser" to write data to and read data from a .json file, and
 * Hibernate to save the list items to a MySQL database.
 * </p>
 * 
 * <p>
 * The application first initializes the EzParser to be able to write and read the data from
 * the .json file. EzParser also reads the existing .json file right off the bat if it already 
 * does exist. After initializing the EzParser it initializes the Hibernate connector (DBConnector).
 * </p>
 * 
 * @author Tommi Lepola
 * @version 1.0
 * @since 2018.1106
 */
public class Gui extends Application {

    private EzParser ezParser;
    private BorderPane root;
    private Items items;
    private DBConnector dbConnector;

    /**
     * Initializes the application. Sets logging level to "off" to avoid tons of lines from
     * Hibernate.
     */
    @Override
    public void init() {
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        ezParser = new EzParser();
        dbConnector = new DBConnector();
    }

    /**
     * Starts the application.
     * 
     * <p>
     * Defines the stylesheet to be used and initializes the different container objects and 
     * adds them to the BorderPane.
     * </p>
     * 
     * @param stage the stage which is rendered.
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("Shopping List");

        root = new BorderPane();
        Scene shoppingList = new Scene(root, 432, 768);
        shoppingList.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        items = new Items(ezParser);

        Controls controls = new Controls(items);
        AppMenu appMenu = new AppMenu();

        VBox topGroup = createTopGroup(appMenu, controls);

        Content content = new Content(items);

        root.setTop(topGroup);
        root.setCenter(content);

        stage.setScene(shoppingList);
        stage.show();
        stage.setResizable(false);

        // Tells the application to close Hibernate connection when the application is closed.
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                dbConnector.closeFactory();
                Platform.exit();
            }
        });

        setMenuListeners(appMenu);
    }

    /**
     * Creates the listeners for the menu items of the application.
     * 
     * <p>
     * Creates the listener for save, load and exit buttons of the menu.
     * </p>
     * 
     * <p>
     * Save button tells DBConnector to save all the items to the MySQL
     * database.
     * </p>
     * 
     * <p>
     * Load button tells DBConnector to write all the content from the MySQL
     * database to the JSON using EzParser.
     * </p>
     * 
     * @param appMenu - The container which holds all the menu items.
     */
    private void setMenuListeners(AppMenu appMenu) {

        appMenu.getSaveButton().setOnAction((e) -> {
            ArrayList<Item> itemsList = new ArrayList<>();

            for (Map.Entry<Integer, Item> entry : items.getMap().entrySet()) {
                itemsList.add(entry.getValue());
            } 

            if(dbConnector.saveItems(itemsList)) {
                Utils.createNewAlert("Success!", "Saved succesfully.", "All your items were saved to the database.", AlertType.INFORMATION);
            };
        });

        appMenu.getLoadButton().setOnAction((e) -> {
            dbConnector.writeTableToJSON(ezParser);
            items.generateItemList();
        });

        appMenu.getExitButton().setOnAction((e) -> {
            dbConnector.closeFactory();
            Platform.exit();
        });
    }

    /**
     * Creates the top group which holds the menubar and the controls.
     * 
     * @param menuBar - the application menubar.
     * @param controls - controls for adding items.
     * @return
     */
    private VBox createTopGroup(MenuBar menuBar, HBox controls) {
        VBox topGroup = new VBox();

        topGroup.getChildren().addAll(menuBar, controls);

        return topGroup;
    }
}