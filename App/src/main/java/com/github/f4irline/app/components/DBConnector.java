package com.github.f4irline.app.components;

import com.github.f4irline.ezparser.EzParser;

import org.hibernate.*;
import org.hibernate.cfg.*;
import org.hibernate.query.Query;

import javafx.scene.control.Alert.AlertType;

import java.util.List;
import java.util.ArrayList;

/**
 * Connector object for Hibernate.
 * 
 * <p>
 * The object holds all the necessary functions to use Hibernate to 
 * write to and read data from the MySQL database.
 * </p>
 * 
 * <p>
 * At this time the application does not create the MySQL table, but the 
 * table is created inside MySQL beforehand. The MySQL database is created with 
 * the following query:
 * </p>
 * 
 * <p>
 * CREATE TABLE ITEMS (id INT(11) NOT NULL PRIMARY KEY, item VARCHAR(255), amount INT(11), checked BOOLEAN, red INT(3), green INT(3), blue INT(3));
 * </p>
 * 
 * @author Tommi Lepola
 * @version 1.0
 * @since 2018.1216
 */
public class DBConnector {
    // Create configuration object
    Configuration cfg;

    // Create SessionFactory that can be used to open a session
    SessionFactory factory;
    Session session;

    /**
     * Initializes the connector. Opens a session right away to avoid
     * some "lag" on the first connection.
     */
    public DBConnector () {
        cfg = new Configuration();

        // Populate the data of the default configuration file which name
        // is hibernate.cfg.xml
        cfg.configure();
        cfg.addAnnotatedClass(Item.class);

        factory = cfg.buildSessionFactory();
        session = factory.openSession();
    }

    /**
     * Saves the given items to the database.
     * 
     * @param items - ArrayList which holds the items.
     * @return - true if succesful, false if there was an exception.
     */
    @SuppressWarnings("rawtypes")
    public boolean saveItems(ArrayList<Item> items) {
        
        // If a session is not already open, opens a new session.
        if (!session.isOpen()) {
            session = factory.openSession();
        }

        try {
            Transaction tx = session.beginTransaction();

            // Deletes all items currently in the database.
            Query removeQuery = session.createQuery("DELETE FROM Item");
    
            removeQuery.executeUpdate();
            tx.commit();
    
            // Populates the database with new items.
            for (Item item : items) {
                tx = session.beginTransaction();
                session.persist(item);
                tx.commit();
            }
    
            session.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Saves a single item to the database (not currently used on the application).
     * 
     * @param item - shopping list item.
     */
    public void saveItem(Item item) {
        if (!session.isOpen()) {
            session = factory.openSession();
        }

        Transaction tx = session.beginTransaction();

        session.persist(item);
        tx.commit();

        session.close();
    }

    /**
     * Removes a single item to the database (not currently used on the application).
     * 
     * @param key - the key identifier where to remove the item from.
     */
    @SuppressWarnings("rawtypes")
    public void removeItem(int key) {
        if (!session.isOpen()) {
            session = factory.openSession();
        }
        Transaction tx = session.beginTransaction();
        Query removeQuery = session.createQuery("DELETE FROM Item WHERE id = "+key);

        removeQuery.executeUpdate();

        tx.commit();
        session.close();
    }

    /**
     * Writes the whole table to the JSON file using EzParser.
     * 
     * @param ezParser - JSON parser library.
     * @return - true if the operation was succesful, false if it was not succesful.
     */
    @SuppressWarnings("unchecked")
    public boolean writeTableToJSON(EzParser ezParser) {
        if (!session.isOpen()) {
            session = factory.openSession();
        }

        List<Item> results = session.createQuery("FROM Item").list();

        if (results.size() > 0) {
            ezParser.clearJSON();
            for (Item item : results) {
                ezParser.write(item);
            }
            ezParser.initList();
            session.close();
            return true;
        } else {
            Utils.createNewAlert("No items found!", "The items database is empty.", "There seems to be no items saved to the database yet. Why don't you create some and save them?", AlertType.WARNING);
            session.close();
            return false;
        }
    }

    /**
     * Closes the connection's factory.
     */
    public void closeFactory() {
        factory.close();
    }
}