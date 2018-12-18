package com.github.f4irline.app.containers;

import com.github.f4irline.ezparser.EzParser;
import com.github.f4irline.app.components.Item;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A container for the shopping list application's items.
 * 
 * <p>
 * Holds the shopping list application's shopping list items, which are
 * basically Item objects.
 * </p>
 * 
 * @author Tommi Lepola
 * @version 1.0
 * @since 2018.1216
 */
public class Items extends VBox {

    private LinkedHashMap<Integer, Item> items;
    private EzParser ezParser;

    /**
     * Initializes the container.
     * 
     * @param ezParser - The parser library for the JSON file.
     */
    public Items(EzParser ezParser) {
        items = new LinkedHashMap<>();
        this.ezParser = ezParser;
        setId("itemBox");
        generateItemList();
    }

    /**
     * Adds a new item to the LinkedHashMap which holds the items. 
     * Then tells EzParser to write it to JSON. Then it calls the
     * createItemWrapper method which creates the wrapper for the item.
     * 
     * @param key - the identifier for the item.
     * @param item - the item.
     */
    public void add (int key, Item item) {
        items.put(key, item);
        ezParser.write(item);
        createItemWrapper(item, key);
    }

    /**
     * Removes the item from the LinkedHashMap.
     * 
     * @param key - the identifier where to remove the item from.
     */
    public void remove (int key) {
        items.remove(key);
    }

    /**
     * Returns the LinkedHashMap which holds the items.
     * 
     * @return - the items LinkedHashMap.
     */
    public LinkedHashMap<Integer, Item> getMap() {
        return items;
    }

    /**
     * Generates the item list. Called when the application starts and when
     * items have been loaded from the database.
     * 
     * <p>
     * First it asks the items from the JSON using the EzParser, and clears the whole LinkedHashMap
     * and all children from it.
     * </p>
     * 
     * <p>
     * Then it iterates through the ArrayList that was returned from EzParser. The ArrayList holds several
     * LinkedHashMaps, which contain objects from the JSON file. So inside the iteration it also iterates
     * through every single LinkedHashMap.
     * </p>
     * 
     */
    public void generateItemList() {
        // Get the items ArrayList from EzParser.
        ArrayList<LinkedHashMap<Object, Object>> itemsList = ezParser.getItems();

        // Clear the items from the container and the LinkedHashMap which holded the items.
        items.clear();
        getChildren().clear();

        getChildren().add(new Separator());
        setSpacing(5);

        // Iterate through the ArrayList which holds LinkedHashMaps.
        for (LinkedHashMap<Object, Object> itemMap : itemsList) {
            Item item;
            String id = "";
            String itemString = "";
            String amount = "";
            String checkedString = "";
            Boolean checked = false;
            // Iterate through every LinkedHashMap in the ArrayList.
            // Add values to Strings if the keys match needed values.
            for (Map.Entry<Object, Object> entry : itemMap.entrySet()) {
                if (entry.getKey().equals("id")) {
                    id = (String) entry.getValue();
                } else if (entry.getKey().equals("item")) {
                    itemString = (String) entry.getValue();
                } else if (entry.getKey().equals("amount")) {
                    amount = (String) entry.getValue();
                } else if (entry.getKey().equals("checked")) {
                    checkedString = (String) entry.getValue();
                    if (checkedString.equals("true")) {
                        checked = true;
                    }
                }
            } 
            // After iterating through a LinkedHashMap (which is basically an object),
            // create a new list item with the values that were checked from the LinkedHashMap.
            if (amount.equals("") || amount.equals("null")) {
                item = new Item(Integer.parseInt(id), itemString, checked);
            } else {
                item = new Item(Integer.parseInt(id), itemString, Integer.parseInt(amount), checked);
            }
            // Put the item into the items LinkedHashMap.
            items.put(Integer.parseInt(id), item);
            // Create wrapper for the item, which is displayed in the application.
            createItemWrapper(item, Integer.parseInt(id));
        }
    }

    /**
     * Creates a wrapper for a item. The wrapper is displayed in the application.
     * 
     * <p>
     * It checks first if the item has an amount or not and creates the label according to that.
     * </p>
     * 
     * <p>
     * Then it creates the wrapper for the whole item and adds the label and remove button to it, 
     * positions them in the wrapper and adds the listener for the remove button.
     * </p>
     * 
     * @param item - the shopping list item.
     * @param key - the identifier of the shopping list item.
     */
    private void createItemWrapper(Item item, Integer key) {
        Label itemLabel = new Label();
        if (item.getAmount() != null) {
            itemLabel.setText("("+item.getAmount()+")"+" "+item.getItem());
        } else {
            itemLabel.setText(item.getItem());
        }
        AnchorPane itemWrapper = new AnchorPane();
        Button removeButton = new Button();
        Separator separator = new Separator();
        itemWrapper.getChildren().addAll(itemLabel, removeButton);
        getChildren().addAll(itemWrapper, separator);

        itemLabel.setId("itemLabel");
        removeButton.setId("buttonRemove");
        separator.setId("itemSeparator");

        AnchorPane.setTopAnchor(itemLabel, 7.0);
        AnchorPane.setLeftAnchor(itemLabel, 5.0);
        AnchorPane.setRightAnchor(removeButton, 7.0);

        itemLabel.setOnMouseClicked((e) -> {
            item.changeChecked();
            if (item.getChecked()) {
                ezParser.changeValue("checked", true, key);
                System.out.println("Is checked!");
            } else {
                ezParser.changeValue("checked", false, key);
                System.out.println("Is not checked!");
            }
        });

        removeButton.setOnAction((e) -> {
            ezParser.remove(key);
            items.remove(key);
            getChildren().removeAll(itemWrapper, separator);
        });
    }

    /**
     * Checks which identifier (key in the LinkedHashMap) is the next available.
     * 
     * <p>
     * Grows index every iteration in the while-loop. If the index is not found in the items
     * map amongst the keySet, breaks the loop and returns that index.
     * </p>
     * 
     * @return The index which is available.
     */
    public int checkNextValidID() {
        int index = 101;

        while (true) {
            if (!items.containsKey(index)) {
                break;
            }
            index++;
        }
        return index;
    }

}