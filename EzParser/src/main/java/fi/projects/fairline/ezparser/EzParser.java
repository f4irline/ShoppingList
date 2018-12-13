/**
 * Package of the json parser.
 */
package fi.projects.fairline.ezparser;

import fi.projects.fairline.ezparser.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.LinkedHashMap;

/**
 * A simple parsing library for JSON files.
 * 
 * <p>
 * EzParser is a simple parsing library for JSON files. It provides
 * very basic functionality, such as writing info to a .json.
 * </p>
 * 
 * <p>
 * Currently EzParser only accepts two string variables for it's write-method.
 * It adds the given string variables to a list in the .json file.
 * </p>
 * 
 * <p>
 * EzParser has the items from the .json file in an LinkedHashMap, which holds identifier
 * (key) and values for the item and the amount of the items as string variables.
 * </p>
 * 
 * @author Tommi Lepola
 * @version 2.0
 * @since 2018.1106
 */
public class EzParser {

    private File jsonFile;
    private JSONWriter jsonWriter;
    private LinkedHashMap<Integer, List<String>> items = new LinkedHashMap<>();
    private JSONStringifier jsonStringifier;

    /**
     * Constructor for EzParser. After creating the EzParser object,
     * it starts initializing the JSON file.
     */
    public EzParser () {
        createJSONFile();
    }

    /**
     * Initializes the JSON file to be used.
     * 
     * <p>
     * The method first checks if a file exists already and if the file is not empty. 
     * If neither of those conditions apply, it creates a new .json file in the root directory
     * of the application. If both of the conditions are true, it just uses the existing
     * file, initializes the writer and stringifier for JSON and gets the existing items 
     * from the JSONStringifier to a LinkedHashMap.
     * </p>
     * 
     */
    private void createJSONFile() {
        if (new File("data.json").isFile() &&
            new File("data.json").length() != 0) {
            System.out.println("File already exists. Using the existing file.");
            initJSONWriter();
            initJSONStringifier();
            readFileToItems();
        } else {
            try {
                jsonFile = new File("data.json");
                jsonFile.createNewFile();

                initJSONWriter();

                jsonWriter.initJSON();

                initJSONStringifier();

                readFileToItems();

                System.out.println("File initialized.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Initializes the JSONWriter which handles writing to the the JSON file.
     */
    private void initJSONWriter() {
        jsonWriter = new JSONWriter();
    }

    /**
     * Initializes the JSONStringifier which handles turning the JSON into an ArrayList which
     * the application can understand.
     */
    private void initJSONStringifier() {
        jsonStringifier = new JSONStringifier();
    }

    /**
     * Reads the content of the .json file into the LinkedHashMap.
     */
    private void readFileToItems() {
        items = jsonStringifier.getJSONList();
    }

    /**
     * Writes any kind of object to the .json.
     * 
     * @param obj the object that will be written.
     */
    public int write(Object obj) {
        int id = checkNextValidID();
        jsonStringifier.addObjectToJSON(obj, jsonWriter);
        return id;
    }

    /**
     * Writes an object with a key and two string variables, item's name and amount, to the .json.
     * 
     * @param item the item which will be added to the .json.
     * @param amount the amount of items which will be added to the .json. Can be empty (null).
     */
    public int write(String item, String amount) {
        if (amount.equals("")) {
            amount = null;
        }
        int id = checkNextValidID();
        JSONObject object = new JSONObject(id, item, amount);
        items.put(object.getKey(), object.getList());
        jsonStringifier.addItemToJSON(object, jsonWriter);
        return id;
    }

    /**
     * Removes an object from the .json.
     * 
     * @param key the key of the object which exists in the .json.
     */
    public void remove(int key) {
        items.remove(key);
        jsonStringifier.removeItemFromJSON(key, jsonWriter);
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

    /**
     * Returns the items LinkedHashMap.
     * 
     * @return items the items LinkedHashMap which holds all the list items and their
     * identifiers.
     */
    public LinkedHashMap<Integer, List<String>> getItems() {
        return items;
    }
}