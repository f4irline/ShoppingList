package com.github.f4irline.app;

import javafx.application.Application;

/**
 * A simple shopping list application made in java.
 * 
 * <p>
 * Shopping list application made in java with a graphical user interface.
 * </p>
 * 
 * <p>
 * The application uses a JSON parser "EzParser" to write data to and read data from a .json file.
 * It also uses Hibernate to save data into a MySQL database.
 * </p>
 * 
 * <p>
 * The application launches the GUI implementation of the application which controls the application.
 * </p>
 * 
 * @author Tommi Lepola
 * @version 2.0
 * @since 2018.1106
 */
public class App {

    /**
     * Acts as the main starting class for the application. Launches the GUI
     * of the application.
     * 
     * @param args command line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("Author: Tommi Lepola");
        
        Application.launch(Gui.class, args);
    }
}