package com.mycompany.galerieartproject;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Crée la SessionFactory à partir du fichier de configuration hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Log l'exception pour pouvoir diagnostiquer l'erreur en cas de problème
            System.err.println("Échec de la création de la SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Ferme les caches et les pools de connexions
        getSessionFactory().close();
    }
}