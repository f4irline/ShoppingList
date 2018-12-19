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

    /**
     * Generates a random shade of blue. Used for item wrappers.
     * 
     * @return - a color style string.
     */
    public static int[] generateRandomBlue() {
        int R = 52;
        int minG = 60;
        int maxG = 85;
        int minB = 80;
        int maxB = 105;

        int G = (int) (Math.random() * ((maxG - minG) + 1) + minG);
        int B = (int) (Math.random() * ((maxB - minB) + 1) + minB);

        int[] rgb = {R, G, B};

        return rgb;
    }
}