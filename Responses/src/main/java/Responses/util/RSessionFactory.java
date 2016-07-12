package Responses.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class RSessionFactory {
    private static SessionFactory sessionFactory = buildSessionFactory();
    private static SessionFactory buildSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy( registry );
            throw new ExceptionInInitializerError("Initial SessionFactory failed" + e);
        }
        return sessionFactory;
    }
    private static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public static Session openSession() {
        return getSessionFactory().openSession();
    }
    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
}
