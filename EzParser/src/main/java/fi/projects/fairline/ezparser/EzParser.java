package fi.projects.fairline.ezparser;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

/**
 * A simple parsing library for JSON files.
 * 
 * <p>
 * EzParser is a simple parsing library for JSON files. It provides
 * very basic functionality, such as writing info to a .json.
 * </p>
 * 
 * <p>
 * Currently EzParser only accepts string variables for it's write-method.
 * It adds the given string variable to a list in the .json file.
 * </p>
 * 
 * <p>
 * EzParser holds the whole data from .json file in an ArrayList. From the ArrayList
 * it generates a HashMap, which holds identifier (key) and value for every list item.
 * </p>
 * 
 * @author Tommi Lepola
 * @version 1.0
 * @since 2018.1106
 */

public class EzParser {

    private File jsonFile;
    private FileWriter jsonWriter;
    private List<String> lines;
    private HashMap<Integer, String> items = new HashMap<>();
    private int itemNumber = 100;
    private int indentAmount = 0;
    private StringBuilder jsonString;

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
     * file, initializes the writer for JSON and reads the whole file into an ArrayList.
     * </p>
     * 
     */
    private void createJSONFile() {
        if (new File("data.json").isFile() &&
            new File("data.json").length() != 0) {
            System.out.println("File already exists. Using the existing file.");
            initJSONWriter();
            readFileToArray();
        } else {
            try {
                jsonFile = new File("data.json");
                jsonFile.createNewFile();

                initJSONWriter();

                initJSON();

                readFileToArray();

                System.out.println("File initialized.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Initializes the FileWriter for the JSON file.
     */
    private void initJSONWriter() {
        try {
            jsonWriter = new FileWriter("data.json", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the JSON file if it is empty or if it does not exist.
     * 
     * <p>
     * initJSON writes a basic structure for the .json file. Currently
     * it only writes a structure so that there is a list which can hold different
     * items.
     * </p>
     */
    private void initJSON() {
        jsonString = new StringBuilder();
        jsonString.append("{");
        jsonString.append("\n");
        jsonString.append("    \"list\": [");
        jsonString.append("\n");
        jsonString.append("    ]");
        jsonString.append("\n");
        jsonString.append("}");
        
        try {
            jsonWriter.write(jsonString.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        flushWriter();
    }

    /**
     * Writes a string variable to the .json.
     * 
     * @param item the string variable which will be added to the .json.
     */
    public void write(String item) {
        itemNumber = checkNextValidID();
        items.put(itemNumber, item);
        addItemToJSON(item);
    }

    /**
     * Removes a string variable from the .json.
     * 
     * @param key the key of the string variable which exists in the .json.
     */
    public void remove(int key) {
        items.remove(key);
        removeItemFromJSON(key);
    }

    /**
     * Checks which identifier (key in the HashMap) is the next available.
     * 
     * <p>
     * Iterates through the HashMap and compares all the keys in it with the
     * index starting from 101. If the key and the index doesn't match, it 
     * defines the next available identifier to be the key and that will be used
     * for writing a new variable in the .json and the HashMap.
     * </p>
     */
    private int checkNextValidID() {
        int index = 101;
        Set set = items.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            if (index != (int) mentry.getKey()) {
                break;
            }
            index++;
        } 
        return index;
    }

    /**
     * Returns the items HashMap.
     * 
     * @return items the items HashMap which holds all the list items and their
     * identifiers.
     */
    public HashMap<Integer, String> getItems() {
        return items;
    }

    /**
     * Removes an item from the JSON.
     * 
     * <p>
     * First it changes the key to be a String and compares it in
     * whole ArrayList which holds the .json file data. If a match is found,
     * it defines the index of the item to be removed to be that one. Then 
     * it removes it using the index from the ArrayList.
     * </p>
     * 
     * <p>
     * After removing it from the list, it iterates through the ArrayList again.
     * It searches in which line the list starts and in which line it ends and 
     * saves it to startIndex and lastIndex variables.
     * </p>
     * 
     * <p>
     * After finding the start and end of the list in the ArrayList,
     * it calls the writeToFile method to write the ArrayList in the .json file itself.
     * </p>
     * 
     * @param key the key of the item which will be deleted from the JSON.
     */
    private void removeItemFromJSON(int key) {
        int removeIndex = 0;
        int index = 0;
        String keyString = Integer.toString(key);

        for (String line : lines) {
            index++;
            if (line.contains(keyString)) {
                removeIndex = index;
            }
        }

        lines.remove(removeIndex-1);

        int startIndex = 0;
        int lastIndex = 0;
        index = 0;

        for (String line : lines) {
            index++;
            if (line.contains("]")) {
                lastIndex = index;
                break;
            } else if (line.contains("[")) {
                startIndex = index;
            }
        }
        writeToFile(startIndex, lastIndex-1);
    }

    /**
     * Adds an item to the .json file.
     * 
     * <p>
     * First it formats the string parameter to look how it should look
     * in a .json file. Then it searches the starting line of the list in the
     * .json and the ending line of the list in the .json.
     * </p>
     * 
     * <p>
     * After that it adds the new item first to the ArrayList which holds all the
     * data from the .json and then it calls the writeToFile method to write the 
     * ArrayList into the .json.
     * </p>
     * 
     * @param item the string item which is added into the .json list.
     */
    private void addItemToJSON(String item) {
        item = "{ \""+itemNumber+"\" : \""+item+"\" }";
        int startIndex = 0;
        int addIndex = 0;
        int index = 0;
        for (String line : lines) {
            index++;
            if (line.contains("]")) {
                addIndex = index;
                break;
            } else if (line.contains("[")) {
                startIndex = index;
            }
        }
        lines.add(addIndex-1, item);
        writeToFile(startIndex, addIndex);
    }

    /**
     * Writes the content of the ArrayList to the .json file.
     * 
     * <p>
     * The writeToFile method first appends all the items from the
     * ArrayList to a StringBuilder while iterating through the ArrayList.
     * </p>
     * 
     * <p>
     * While appending it also checks how much every line should be indented
     * in the .json file.
     * </p>
     * 
     * @param startIndex the line where the list in the .json starts.
     * @param lastIndex the line where the list in the .json ends.
     */
    private void writeToFile(int startIndex, int lastIndex) {
        int index = 0;
        jsonString = new StringBuilder();
        for (String line : lines) {
            if (line.contains("}") || line.contains("]")) {
                if (!line.contains("{") && !line.contains("[")) {
                    indentAmount -= 4;
                }
            }
            indent();
            if (index > startIndex-1 && index < lastIndex-1) {
                jsonString.append(line+",\n");
            } else {
                jsonString.append(line+"\n");
            }
            if (line.contains("{") || line.contains("[")) {
                if (!line.contains("}") && !line.contains("]")) {
                    indentAmount += 4;
                }
            }
            index++;
        }
        try {
            jsonWriter = new FileWriter("data.json");
            jsonWriter.write(jsonString.toString());
            flushWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the whole .json file into an ArrayList.
     * 
     * <p>
     * The method readFileToArray reads the whole data.json
     * file into an ArrayList.
     * </p>
     * 
     * <p>
     * The idea behind it was that it could be easier to 
     * modify the .json file by having it in an ArrayList first,
     * which is then modified, and then the ArrayList is written
     * into the .json file.
     * </p>
     */
    private void readFileToArray() {
        lines = new ArrayList<String>();
        boolean nextIsArray = false;
        String indent = "    ";
        try (BufferedReader fileReader = new BufferedReader(new FileReader("data.json"))) {
            String line;

            while ((line = fileReader.readLine()) != null) {
                line = line.replaceAll(indent, "");
                line = line.replaceAll(",", "");
                lines.add(line);
                if (nextIsArray && !line.contains("]")) {
                    itemNumber++;
                    line = line.replaceAll("\"", "");
                    line = line.replaceAll(" ", "");
                    int key = Integer.parseInt(line.substring(1, 4));
                    String value = line.substring(5);
                    value = value.replaceAll("}", "");
                    items.put(key, value);
                } else if (line.contains("[")) {
                    nextIsArray = true;
                } else if (line.contains("]")) {
                    nextIsArray = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Appends indentation in the writeToFile method to the StringBuilder.
     */
    private void indent() {
        for (int i = 0; i < indentAmount; i++) {
            jsonString.append(" ");
        }
    }

    /**
     * Flushes the jsonWriter when called.
     */
    private void flushWriter() {
        try {
            jsonWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the jsonWriter when called.
     */
    private void closeWriter() {
        try {
            jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}