/**
 * Package for the UI implementation of the shoppinglist application.
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
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Gui extends Application {

    EzParser ezParser;
    BorderPane root;
    HashMap<Integer, String> items;
    VBox itemBox;

    @Override
    public void init() {
        ezParser = new EzParser();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Shopping List");
        root = new BorderPane();
        Scene shoppingList = new Scene(root, 432, 768);

        MenuBar menuBar = new MenuBar();

        VBox topGroup = new VBox();
        HBox controls = new HBox();

        Label itemLabel = new Label("Item: ");
        TextField item = new TextField();
        Label amountLabel = new Label("Amount: ");
        TextField amount = new TextField();
        Button add = new Button("+");

        controls.getChildren().addAll(itemLabel, item, amountLabel, amount, add);
        topGroup.getChildren().addAll(menuBar, controls);

        generateItemList();

        root.setTop(topGroup);
        root.setCenter(itemBox);

        add.setOnAction((e) -> {
            ezParser.write(item.getText());
            item.setText("");
            generateItemList();
        });

        stage.setScene(shoppingList);
        stage.show();
    }

    private void generateItemList() {
        itemBox = new VBox();
        root.setCenter(itemBox);

        items = ezParser.getItems();
        Set set = items.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();

            HBox itemWrapper = new HBox();
            Button removeButton = new Button("X");
            itemWrapper.getChildren().addAll(new Label(mentry.getValue().toString()), removeButton);

            itemBox.getChildren().add(itemWrapper);

            removeButton.setOnAction((e) -> {
                ezParser.remove(Integer.valueOf(mentry.getKey().toString()));
                generateItemList();
            });
        }
    }
}