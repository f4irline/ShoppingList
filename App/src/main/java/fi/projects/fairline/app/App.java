/**
 * Package for the UI implementation of the shoppinglist application.
 */
package fi.projects.fairline.app;

import javafx.application.Application;

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
public class App {

    /**
     * Acts as the main starting class for the application. Has basic CLI functionality for
     * adding, removing and checking list items.
     * 
     * @param args command line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("Author: Tommi Lepola");

        Application.launch(Gui.class, args);

    }
}