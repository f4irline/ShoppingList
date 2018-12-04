/**
 * Package of the json parser.
 */
package fi.projects.fairline.ezparser;

import java.util.List;
import java.util.ArrayList;

/**
 * Manages the format of every JSON object which is added to the list.
 * 
 * <p>
 * JSONObject manages the format of every JSON object so that the strings
 * that are added into the .json match the standards of .json files.
 * </p>
 * 
 * @author Tommi Lepola
 * @version 1.0
 * @since 2018.1106
 */
public class JSONObject {
    String item;
    String amount;
    int key;
    List<String> values;
    public JSONObject(int key, String ... itemValues) {
        values = new ArrayList<String>();
        setItem(itemValues[0]);
        setAmount(itemValues[1]);
        setKey(key);
    }

    /**
     * Sets the item which it is an object of.
     * 
     * @param item the item.
     */
    private void setItem(String item) {
        values.add(item);
        this.item = item;
    }

    /**
     * Sets the amount of the items.
     * 
     * @param amount the amount of the items.
     */
    private void setAmount(String amount) {
        values.add(amount);
        this.amount = amount;
    }

    /**
     * Sets the key which can be used to 
     * identify the object.
     * 
     * @param key the key identifier.
     */
    private void setKey(int key) {
        this.key = key;
    }

    /**
     * Returns the item of the object in a JSON format.
     * 
     * @return item in a JSON format.
     */
    public String getJsonItem() {
        String jsonString = "\"item\" : \""+item+"\",";
        return jsonString;
    }

    /**
     * Returns the amount of the object in a JSON format.
     * 
     * @return amount of the items in a JSON format.
     */
    public String getJsonAmount() {
        String jsonString = "\"amount\" : "+amount;
        return jsonString;
    }

    /**
     * Returns the key of the object in a JSON format.
     * 
     * @return the key of the object in a JSON format.
     */
    public String getJsonKey() {
        String jsonString = "\"id\" : "+key+",";
        return jsonString;
    }

    /**
     * Returns the item of the object.
     * 
     * @return item of the object.
     */
    public String getItem() {
        return item;
    }

    /**
     * Returns the amount of the object.
     * 
     * @return amount of the object.
     */
    public String getAmount() {
        return amount;
    }

    /**
     * Returns the key of the object.
     * 
     * @return key of the object.
     */
    public int getKey() {
        return key;
    }

    /**
     * Returns the list which holds the item and amount values.
     * 
     * @return ArrayList which holds the item and amount values.
     */
    public List<String> getList() {
        return values;
    }
}