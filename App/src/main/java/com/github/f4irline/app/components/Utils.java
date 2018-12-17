package com.github.f4irline.app.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Holds some general purpose methods for the shopping list item.
 * 
 * @author Tommi Lepola
 * @version 1.0
 * @since 2018.1216
 */
public class Utils {

    /**
     * Creates a new alert dialog.
     * 
     * @param title - title of the dialog.
     * @param header - header of the dialog.
     * @param content - content of the dialog.
     * @param type - type of the dialog (WARNING, ERROR, INFORMATION, CONFIRMATION or NONE)
     */
    public static void createNewAlert(String title, String header, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}