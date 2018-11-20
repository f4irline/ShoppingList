/**
 * Package for the UI implementation of the shoppinglist application.
 */
package fi.projects.fairline.app;

import fi.projects.fairline.ezparser.EzParser;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;

/**
 * A simple shoppinglist application made in java.
 * 
 * <p>
 * Shoppinglist application made in java. Currently works as a CLI application, GUI implementation
 * is in the making.
 * </p>
 * 
 * 
 * <p>
 * The application uses a JSON parser "EzParser" to write data and read data from a .json file.
 * </p>
 * 
 * <p>
 * The application first initializes the parser and then asks the user what he wants to do. 
 * Currently options are either "Add" a list item, "Remove" a list item and "Check" all  list items.
 * </p>
 * 
 * @author Tommi Lepola
 * @version 1.0
 * @since 2018.1106
 */
public class Gui {

    /**
     * Acts as the main starting class for the application. Has basic CLI functionality for
     * adding, removing and checking list items.
     * 
     * @param args command line arguments (not used).
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean running = true;
        System.out.println("Author: Tommi Lepola");
        EzParser ezParser = new EzParser();

        HashMap<Integer, String> items = ezParser.getItems();

        while (running) {
            System.out.println("What would you like to do? ('Add' item, 'Remove' item, 'Check' Items): ");
            String userInput = "";
            userInput = input.nextLine();
            if (userInput.equals("check")) {
                printItems(items);
            } else if (userInput.equals("add")) {
                System.out.println("What would you like to add?");
                String addInput = "";
                addInput = input.nextLine();
                ezParser.write(addInput);
                items = ezParser.getItems();
            } else if (userInput.equals("remove")) {
                int keyInput = 0;
                System.out.println("Enter key for removal");
                printItems(items);
                keyInput = input.nextInt();
                ezParser.remove(keyInput);
                items = ezParser.getItems();
            }
            items = ezParser.getItems();
        }
        input.close();
    }

    /**
     * Iterates through the items list and prints them to command line.
     * 
     * @param items A hashmap which contains all items in the list currently.
     */
    public static void printItems(HashMap<Integer, String> items) {
        Set set = items.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
            System.out.println(mentry.getValue());
        } 
    }
}