package fr.vocaltech.hibernate;

import fr.vocaltech.hibernate.models.Song;
import fr.vocaltech.hibernate.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class HibernateApp {
    public static void main(String[] args) {
        // Open a SessionFactory
        SessionFactory sf = HibernateUtils.getSessionFactory();

        // Open a session
        Session session = sf.openSession();

        // Create a new object
        Song song1 = new Song();
        song1.setSongName("songName1");
        song1.setArtist("artist1");
        System.out.println(song1);

        // Save the created object in the db
        Transaction transaction = session.beginTransaction();
        session.persist(song1);
        transaction.commit();
    }
}
