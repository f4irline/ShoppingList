/**
 * Package of the shoppinglist application.
 */
package fi.projects.fairline.app;

import fi.projects.fairline.ezparser.EzParser;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.AnchorPane;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import javafx.geometry.Insets;

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
    VBox itemBox;

    @Override
    public void init() {
        ezParser = new EzParser();
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

        MenuBar menuBar = new MenuBar();

        VBox topGroup = new VBox();
        HBox controls = new HBox();

        Label itemLabel = new Label("Item: ");
        TextField item = new TextField();
        Label amountLabel = new Label("Amount: ");
        TextField amount = new TextField();
        Button add = new Button("+");
        Separator separator = new Separator();

        controls.getChildren().addAll(itemLabel, item, amountLabel, amount, add);
        HBox.setMargin(itemLabel, new Insets(10, 0, 10, 5));
        HBox.setMargin(item, new Insets(7, 5, 10, 5));
        HBox.setMargin(amountLabel, new Insets(10, 0, 10, 0));
        HBox.setMargin(amount, new Insets(7, 5, 10, 5));
        HBox.setMargin(add, new Insets(7, 0, 10, 0));

        VBox.setMargin(separator, new Insets(0, 0, 10, 0));

        topGroup.getChildren().addAll(menuBar, controls, separator);

        generateItemList();

        root.setTop(topGroup);
        root.setCenter(itemBox);

        add.setOnAction((e) -> {
            ezParser.write(item.getText(), amount.getText());
            item.setText("");
            amount.setText("");
            generateItemList();
        });

        stage.setScene(shoppingList);
        stage.show();
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
            Label amount = new Label(itemAmount);
            itemWrapper.getChildren().addAll(item, amount, removeButton);
            itemBox.getChildren().addAll(itemWrapper, new Separator());

            AnchorPane.setLeftAnchor(item, 5.0);
            AnchorPane.setLeftAnchor(amount, 216.0);
            AnchorPane.setRightAnchor(removeButton, 5.0);

            removeButton.setOnAction((e) -> {
                ezParser.remove(key);
                generateItemList();
            });
        } 
    }
}