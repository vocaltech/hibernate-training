package fr.vocaltech.hibernate.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.function.Consumer;

/**
 * Helper class
 */
public class HibernateUtils {
    /**
     * Get a SessionFactory object
     * from hibernate.cfg.xml file configuration
     * @return SessionFactory
     */
    public static SessionFactory getSessionFactory() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();

        return new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }

    /**
     * Execute an action in transaction
     * @param action The action to execute
     */
    public static void doInTransaction(Consumer<Session> action) {
        Session session = getSessionFactory().openSession();

        Transaction txn = session.beginTransaction();
        action.accept(session);
        txn.commit();

        session.close();
    }
}
