/**
 * Package of the shoppinglist application.
 */
package fi.projects.fairline.app;

import fi.projects.fairline.ezparser.EzParser;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.AnchorPane;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import javafx.event.EventHandler;
import javafx.application.Platform;

/**
 * Graphical user interface implementation of the shopping list application.
 * 
 * <p>
 * Graphical user interface implementation of the shopping list application using 
 * the JavaFX library for the UI components.
 * </p>
 * 
 * <p>
 * Uses a JSON parser "EzParser" to write data to and read data from a .json file.
 * </p>
 * 
 * <p>
 * The application first initializes the EzParser to be able to write and read the data from
 * the .json file. EzParser also reads the existing .json file right off the bat if it already 
 * does exist.
 * </p>
 * 
 * @author Tommi Lepola
 * @version 1.0
 * @since 2018.1106
 */
public class Gui extends Application {

    private EzParser ezParser;
    private BorderPane root;
    private LinkedHashMap<Integer, List<String>> items;
    private DBConnector dbConnector;
    private VBox itemBox;

    @Override
    public void init() {
        ezParser = new EzParser();
        dbConnector = new DBConnector();
    }

    /**
     * Starts the application.
     * 
     * <p>
     * The method creates the application by first placing all the components such as buttons
     * and labels in their places and then generates the item list if one already exists in the
     * .json file.
     * </p>
     * 
     * @param stage the stage which is rendered.
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("Shopping List");

        if (ezParser.getItems().size() == 0) {
            dbConnector.writeTableToJSON(ezParser);
        }

        root = new BorderPane();
        Scene shoppingList = new Scene(root, 432, 768);
        shoppingList.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        Controls controls = new Controls();
        AppMenu appMenu = new AppMenu();

        VBox topGroup = createTopGroup(appMenu, controls);

        generateItemList();

        root.setTop(topGroup);
        root.setCenter(itemBox);

        stage.setScene(shoppingList);
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                dbConnector.closeFactory();
                Platform.exit();
            }
        });

        setListeners(appMenu, controls);
    }

    private void setListeners(AppMenu appMenu, Controls controls) {
        controls.getAddButton().setOnAction((e) -> {
            if (validateAmountField(controls.getAmountField().getText()) && !controls.getAmountField().getText().equals("")) {
                addItem(controls.getItemField().getText(), controls.getAmountField().getText());
                controls.getItemField().setText("");
                controls.getAmountField().setText("");
            } else if (controls.getAmountField().getText().equals("")) {
                createNewAlert("Wrong input!", "No item!", "Please give an item to add to the list.");
            } else {
                createNewAlert("Wrong input!", "Invalid amount value!", "Please only give numeric values to amount, or leave it empty.");
            }
        });

        appMenu.getExitButton().setOnAction((e) -> {
            dbConnector.closeFactory();
            Platform.exit();
        });
    }

    private VBox createTopGroup(MenuBar menuBar, HBox controls) {
        VBox topGroup = new VBox();

        topGroup.getChildren().addAll(menuBar, controls);

        return topGroup;
    }

    private boolean validateAmountField(String amount) { 
        boolean validatedInput = false;
        if (amount.matches("\\d+(\\.\\d+)?") || amount.equals(" ")) {
            validatedInput = true;
        }
        return validatedInput;
    }

    private void createNewAlert(String title, String header, String content) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void addItem(String itemField, String amountField) {
        Item item;
        int id = ezParser.write(itemField, amountField);
        if (amountField.equals("")) {
            item = new Item(id, itemField);
        } else {
            item = new Item(id, itemField, Integer.parseInt(amountField));
        }
        createItemWrapper(item, id);
        dbConnector.saveItem(item);
    }

    /**
     * Generates the item list.
     * 
     * <p>
     * The method generates the item list. It's called when the application is launched
     * to add the items to the list in the application if a .json file already exists.
     * </p>
     */
    private void generateItemList() {
        itemBox = new VBox();
        itemBox.getChildren().add(new Separator());
        itemBox.getStyleClass().add("itemBox");
        itemBox.setSpacing(5);
        root.setCenter(itemBox);

        items = ezParser.getItems();

        if (items.size() == 0) {
            return;
        }

        for (Map.Entry<Integer, List<String>> entry : items.entrySet()) {
            Integer key = entry.getKey();
            String value = entry.getValue().get(0);
            String itemAmount = "";
            if (!entry.getValue().get(1).equals("null")) {
                itemAmount = entry.getValue().get(1);
            }

            Item item = new Item(key, value, Integer.parseInt(itemAmount));

            createItemWrapper(item, key);
        } 
    }

    private void createItemWrapper(Item item, Integer key) {
        Label itemLabel = new Label();
        if (item.getAmount() != null) {
            itemLabel.setText("("+item.getAmount()+")"+" "+item.getItem());
        } else {
            itemLabel.setText(item.getItem());
        }
        AnchorPane itemWrapper = new AnchorPane();
        Button removeButton = new Button();
        removeButton.getStyleClass().add("buttonRemove");
        itemLabel.getStyleClass().add("itemLabel");
        Separator separator = new Separator();
        separator.getStyleClass().add("itemSeparator");
        itemWrapper.getChildren().addAll(itemLabel, removeButton);
        itemBox.getChildren().addAll(itemWrapper, separator);

        AnchorPane.setTopAnchor(itemLabel, 7.0);
        AnchorPane.setLeftAnchor(itemLabel, 5.0);
        AnchorPane.setRightAnchor(removeButton, 7.0);

        removeButton.setOnAction((e) -> {
            ezParser.remove(key);
            dbConnector.removeItem(key);
            generateItemList();
        });
    }
}