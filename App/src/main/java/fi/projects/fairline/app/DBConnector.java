package fi.projects.fairline.app;

import fi.projects.fairline.ezparser.EzParser;
import java.util.List;
import org.hibernate.*;
import org.hibernate.cfg.*;
import org.hibernate.query.Query;

// CREATE TABLE ITEMS (
//     id INT(11) NOT NULL PRIMARY KEY,
//     item VARCHAR(255),
//     amount INT(11)
// );

public class DBConnector {
    // Create configuration object
    Configuration cfg;

    // Create SessionFactory that can be used to open a session
    SessionFactory factory;

    public DBConnector () {
        cfg = new Configuration();

        // Populate the data of the default configuration file which name
        // is hibernate.cfg.xml
        cfg.configure();
        cfg.addAnnotatedClass(Item.class);

        factory = cfg.buildSessionFactory();
    }

    public void saveItem(Item item) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        session.persist(item);
        tx.commit();
        session.close();
    }

    @SuppressWarnings("rawtypes")
    public void removeItem(int key) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Query removeQuery = session.createQuery("DELETE FROM Item WHERE id = "+key);

        removeQuery.executeUpdate();

        tx.commit();
        session.close();
    }

    @SuppressWarnings("unchecked")
    public void writeTableToJSON(EzParser ezParser) {
        Session session = factory.openSession();

        List<Item> results = session.createQuery("FROM Item").list();

        for (Item item : results) {
            ezParser.write(item.getItem(), String.valueOf(item.getAmount()));
        }
    }

    public void closeFactory() {
        factory.close();
    }
}