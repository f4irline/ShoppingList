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
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.AnchorPane;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
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

    EzParser ezParser;
    BorderPane root;
    LinkedHashMap<Integer, List<String>> items;
    DBConnector dbConnector;
    VBox itemBox;

    @Override
    public void init() {
        ezParser = new EzParser();
        dbConnector = new DBConnector();
        if (ezParser.getItems().size() == 0) {
            dbConnector.writeTableToJSON(ezParser);
        }
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

        root = new BorderPane();
        Scene shoppingList = new Scene(root, 432, 768);
        shoppingList.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        MenuBar menuBar = createMenuBar();

        HBox controls = createControls();

        VBox topGroup = createTopGroup(menuBar, controls);

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
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu menuFile = createFileMenu();

        Menu menuAbout = createAboutMenu();

        menuBar.getMenus().addAll(menuFile, menuAbout);
        return menuBar;
    }

    public Menu createFileMenu() {
        Menu menuFile = new Menu("File");

        MenuItem exit = new MenuItem("Exit");

        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                dbConnector.closeFactory();
                Platform.exit();
            }
        });
        
        menuFile.getItems().addAll(exit);
        return menuFile;
    }

    public Menu createAboutMenu() {
        Menu menuAbout = new Menu("About");
        MenuItem about = new MenuItem("About Lotto App");
        menuAbout.getItems().addAll(about);
        return menuAbout;
    }

    private VBox createTopGroup(MenuBar menuBar, HBox controls) {
        VBox topGroup = new VBox();

        Separator separator = new Separator();
        separator.getStyleClass().add("itemSeparator");

        VBox.setMargin(separator, new Insets(0, 0, 10, 0));

        topGroup.getChildren().addAll(menuBar, new Separator(), controls, separator);

        return topGroup;
    }

    private HBox createControls() {
        HBox controls = new HBox();

        Label itemLabel = new Label("Item: ");
        itemLabel.getStyleClass().add("headerLabel");
        TextField itemField = new TextField();
        itemField.setPrefWidth(120);
        Label amountLabel = new Label("Amount: ");
        amountLabel.getStyleClass().add("headerLabel");
        TextField amountField = new TextField();
        amountField.setPrefWidth(80);
        Button add = new Button("+");

        HBox.setMargin(itemLabel, new Insets(10, 0, 10, 5));
        HBox.setMargin(itemField, new Insets(7, 5, 10, 5));
        HBox.setMargin(amountLabel, new Insets(10, 0, 10, 0));
        HBox.setMargin(amountField, new Insets(7, 5, 10, 5));
        HBox.setMargin(add, new Insets(7, 0, 10, 0));

        controls.getChildren().addAll(itemLabel, itemField, amountLabel, amountField, add);

        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (validateAmountField(amountField.getText())) {
                    int id = ezParser.write(itemField.getText(), amountField.getText());
                    Item item = new Item(id, itemField.getText(), Integer.parseInt(amountField.getText()));
                    itemField.setText("");
                    amountField.setText("");
                    generateItemList();
                    dbConnector.saveItem(item);
                } else if (amountField.getText().equals("")) {
                    int id = ezParser.write(itemField.getText(), amountField.getText());
                    Item item = new Item(id, itemField.getText());
                    itemField.setText("");
                    amountField.setText("");
                    generateItemList();
                    dbConnector.saveItem(item);
                } else {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Wrong input!");
                    alert.setHeaderText("Invalid amount value!");
                    alert.setContentText("Please only give numeric values to amount, or leave it empty.");
                    alert.showAndWait();
                }
            }
        });
        return controls;
    }

    private boolean validateAmountField(String amount) { 
        return amount.matches("\\d+(\\.\\d+)?");
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
            try {
                if (!entry.getValue().get(1).equals("null")) {
                    itemAmount = entry.getValue().get(1)+"x";
                }
            } catch (NullPointerException e) {
                itemAmount = "";
            }

            AnchorPane itemWrapper = new AnchorPane();
            Button removeButton = new Button("X");
            Label item = new Label(value);
            item.getStyleClass().add("itemLabel");
            Label amount = new Label(itemAmount);
            amount.getStyleClass().add("itemLabel");
            Separator separator = new Separator();
            separator.getStyleClass().add("itemSeparator");
            itemWrapper.getChildren().addAll(item, amount, removeButton);
            itemBox.getChildren().addAll(itemWrapper, separator);

            AnchorPane.setLeftAnchor(item, 5.0);
            AnchorPane.setLeftAnchor(amount, 216.0);
            AnchorPane.setRightAnchor(removeButton, 5.0);

            removeButton.setOnAction((e) -> {
                ezParser.remove(key);
                dbConnector.removeItem(key);
                generateItemList();
            });
        } 
    }
}