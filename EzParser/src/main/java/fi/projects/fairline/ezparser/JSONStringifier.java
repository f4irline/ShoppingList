/**
 * Package of the json parser.
 */
package fi.projects.fairline.ezparser;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Handles and manages the ArrayList which is a representation of the
 * .json data file.
 * 
 * @author Tommi Lepola
 * @version 1.0
 * @since 2018.1106
 */
public class JSONStringifier {
    private LinkedHashMap<Integer, List<String>> items;
    private List<String> lines;
    private String indent = "    ";

    private int key = 0;
    private String value = "";
    private String amount = "";


    public JSONStringifier() {
        items = new LinkedHashMap<>();
        lines = new ArrayList<>();
        createJSONList();
    }

    /**
     * Reads the whole .json file into an ArrayList and puts all the
     * already existing items into a LinkedHashMap.
     * 
     * <p>
     * The method readFileToArray reads the whole data.json
     * file into an ArrayList and if items already exist in the .json
     * file, adds them into a LinkedHashMap.
     * </p>
     * 
     * <p>
     * The idea behind it was that it could be easier to 
     * read the .json file by having it in an ArrayList first and 
     * getting all the data from that ArrayList by iterating through
     * and checking where all the needed data is.
     * </p>
    */
    private void createJSONList() {
        try (BufferedReader fileReader = new BufferedReader(new FileReader("data.json"))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                line = line.replaceAll(indent, "");
                line = line.replaceAll("},", "}");
                lines.add(line);
                line = line.replaceAll(",", "");
                line = line.replaceAll(" ", "");
                if (line.contains("\"id\"")) {
                    line = line.replaceAll("\"", "");
                    key = Integer.parseInt(line.substring(3, 6));
                } else if (line.contains("\"item\"")) {
                    line = line.replaceAll("\"", "");
                    value = line.substring(5);
                } else if (line.contains("\"amount\"")) {
                    line = line.replaceAll("\"", "");
                    List<String> values = new ArrayList<>();
                    amount = line.substring(7);
                    values.add(value);
                    values.add(amount);
                    items.put(key, values);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
     * After removing it from the list, it asks for the boundaries of the whole
     * list in the .json for jsonWriter to be able to rewrite the list.
     * </p>
     * 
     * <p>
     * After finding the start and end of the list in the ArrayList,
     * it calls the writeToJSON method from the jsonWriter to write the 
     * ArrayList in to the .json file itself.
     * </p>
     * 
     * @param key the key of the item which will be deleted from the JSON.
     * @param jsonWriter the object which holds all the means to write to the .json file.
     */
    public void removeItemFromJSON(int key, JSONWriter jsonWriter) {
        int removeIndex = 0;
        int index = 0;
        String keyString = Integer.toString(key);

        for (String line : lines) {
            index++;
            if (line.contains(keyString)) {
                removeIndex = index;
            }
        }

        int objectStartIndex = removeIndex - 2;
        int idIndex = removeIndex - 1;
        int itemIndex = removeIndex;
        int amountIndex = removeIndex + 1;
        int objectEndIndex = removeIndex + 2;

        lines.remove(objectEndIndex);
        lines.remove(amountIndex);
        lines.remove(itemIndex);
        lines.remove(idIndex);
        lines.remove(objectStartIndex);

        int[] listBoundaries = checkListBoundaries();

        jsonWriter.writeToJSON(lines, listBoundaries[0], listBoundaries[1]-2);
    }

    /**
     * Adds an item to the .json file.
     * 
     * <p>
     * First it searches the starting line of the list in the .json and the ending 
     * line of the list in the .json.
     * </p>
     * 
     * <p>
     * After that it adds the new item first to the ArrayList which holds all the
     * data from the .json and then it calls the writeToFile method to write the 
     * ArrayList into the .json.
     * </p>
     * 
     * @param object the object which is added into the .json list.
     * @param jsonWriter the object which holds all the means to write to the .json file.
     */
    public void addItemToJSON(JSONObject object, JSONWriter jsonWriter) {
        int[] listBoundaries = checkListBoundaries();

        int objectStartIndex = listBoundaries[1] - 1;
        int idIndex = listBoundaries[1];
        int itemIndex = listBoundaries[1] + 1;
        int amountIndex = listBoundaries[1] + 2;
        int objectEndIndex = listBoundaries[1] + 3;

        lines.add(objectStartIndex, "{");
        lines.add(idIndex, object.getJsonKey());
        lines.add(itemIndex, object.getJsonItem());
        lines.add(amountIndex, object.getJsonAmount());
        lines.add(objectEndIndex, "}");

        jsonWriter.writeToJSON(lines, listBoundaries[0], listBoundaries[1]+3);
    }

    /**
     * Checks the boundaries of the list in the .json file.
     * 
     * <p>
     * Iterates through the lines which are a representation of the
     * .json file and finds the starting and ending of the list in the
     * file.
     * </p>
     * 
     * @return listBoundaries an Array which holds the ending and beginning
     * indexes of the .json file. The beginning is in the index[0] of the array
     * and ending is in the index[1] of the array.
     */
    private int[] checkListBoundaries() {
        int[] listBoundaries = new int[2];

        int index = 0;


        for (String line : lines) {
            index++;
            if (line.contains("]")) {
                listBoundaries[1] = index;
                break;
            } else if (line.contains("[")) {
                listBoundaries[0] = index;
            }
        }

        return listBoundaries;
    }

    /**
     * Returns the items LinkedHashMap.
     * 
     * @return items the items LinkedHashMap which holds all the list items, their amounts
     * and their identifiers.
     */
    public LinkedHashMap<Integer, List<String>> getJSONList() {
        return items;
    }
}