/**
 * Package for the UI implementation of the shoppinglist application.
 */
package fi.projects.fairline.app;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Gui extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Shopping List");
        BorderPane root = new BorderPane();
        Scene shoppingList = new Scene(root, 432, 768);
        stage.setScene(shoppingList);
        stage.show();
    }

}