package com.github.f4irline.app.containers;

import com.github.f4irline.app.components.Utils;
import com.github.f4irline.app.components.Item;

import javafx.geometry.Insets;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * A container for the shopping list application's controls.
 * 
 * <p>
 * Holds the shopping list application's controls, which are basically
 * the text fields for the item name and amount and the item add button.
 * </p>
 * 
 * @author Tommi Lepola
 * @version 1.0
 * @since 2018.1216
 */
public class Controls extends HBox {

    private Label itemLabel;
    private TextField itemField;
    private Label amountLabel;
    private TextField amountField;
    private Button add;
    private Items items;

    /**
     * Initializes the controls container. Creates the controls and then sets their margins to fit 
     * the application window properly.
     * 
     * @param items - Shopping list items.
     */
    public Controls (Items items) {
        this.items = items;
        createControls();
        setControlMargins();
        setId("controlBox");
    }

    /**
     * Creates the controls and adds the event listener for the add button.
     */
    private void createControls() {
        itemLabel = new Label("Item: ");
        itemField = new TextField();
        itemField.setPrefWidth(100);
        amountLabel = new Label("Amount: ");
        amountField = new TextField();
        amountField.setPrefWidth(60);
        add = new Button();

        itemLabel.getStyleClass().add("headerLabel");
        itemField.getStyleClass().add("controlField");
        amountLabel.getStyleClass().add("headerLabel");
        amountField.getStyleClass().add("controlField");
        add.setId("buttonAdd");

        add.setOnAction((e) -> {
            if (validateAmountField(amountField.getText()) && !itemField.getText().equals("")) {
                addItem(itemField.getText(), amountField.getText());
                itemField.setText("");
                amountField.setText("");
            } else if (itemField.getText().equals("")) {
                Utils.createNewAlert("Wrong input!", "No item!", "Please give an item to add to the list.", AlertType.WARNING);
            } else {
                Utils.createNewAlert("Wrong input!", "Invalid amount value!", "Please only give numeric values to amount, or leave it empty.", AlertType.WARNING);
            }
        });

        getChildren().addAll(itemLabel, itemField, amountLabel, amountField, add);
    }

    /**
     * Sets the margins for the controls.
     */
    private void setControlMargins() {
        HBox.setMargin(itemLabel, new Insets(10, 0, 10, 5));
        HBox.setMargin(itemField, new Insets(10, 5, 10, 5));
        HBox.setMargin(amountLabel, new Insets(10, 0, 10, 0));
        HBox.setMargin(amountField, new Insets(10, 5, 10, 5));
        HBox.setMargin(add, new Insets(7, 0, 10, 45));
    }

    /**
     * Validates the amount textfield. Uses regex to check whether
     * the amount field is a digit or empty.
     * 
     * @param amount - the string from the amount textfield.
     * @return - true if validated, false if invalid.
     */
    private boolean validateAmountField(String amount) { 
        boolean validatedInput = false;
        if (amount.matches("\\d+?") || amount.equals("")) {
            validatedInput = true;
        }
        return validatedInput;
    }

    /**
     * Adds a new item to the items container.
     * 
     * <p>
     * Checks first the next valid ID from the items container. Then
     * it creates a new Item object. If the amount field is empty, it leaves
     * the amount null on the object. Then it adds it to the items container.
     * </p>
     * 
     * @param itemField - String from the item textfield.
     * @param amountField - String from the amount textfield.
     */
    private void addItem(String itemField, String amountField) {
        Item item;
        int id = items.checkNextValidID();
        if (amountField.equals("")) {
            item = new Item(id, itemField);
        } else {
            item = new Item(id, itemField, Integer.parseInt(amountField));
        }
        items.add(id, item);
    }
}