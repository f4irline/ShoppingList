package fi.projects.fairline.app;

import org.hibernate.*;
import org.hibernate.cfg.*;
import org.hibernate.query.Query;

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

    public void closeFactory() {
        factory.close();
    }
}