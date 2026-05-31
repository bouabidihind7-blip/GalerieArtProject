package com.mycompany.galerieartproject;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class OeuvreService {

    // 1. Ajouter une oeuvre
    public void saveOeuvre(Oeuvre oeuvre) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(oeuvre);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // 2. Modifier une oeuvre
    public void updateOeuvre(Oeuvre oeuvre) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(oeuvre);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // 3. Supprimer une oeuvre par son ID
    public void deleteOeuvre(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Oeuvre oeuvre = session.get(Oeuvre.class, id);
            if (oeuvre != null) {
                session.delete(oeuvre);
                System.out.println("Oeuvre supprimée avec succès.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // 4. Récupérer toutes les oeuvres (pour charger le TableView)
    public List<Oeuvre> getAllOeuvres() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Oeuvre", Oeuvre.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}