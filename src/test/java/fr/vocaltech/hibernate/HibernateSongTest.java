package fr.vocaltech.hibernate;

import fr.vocaltech.hibernate.models.Song;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static fr.vocaltech.hibernate.utils.HibernateUtils.doInTransaction;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HibernateSongTest {
    @BeforeEach
    public void setup() {
        doInTransaction(session -> {
            clearPlaylistTable(session);
            updateAutoIncrement(session, 1);
        });
    }

    @Test
    void givenSong_whenSave_thenOk() {
        doInTransaction(session -> {
            // save a new song
            Song song = new Song();
            song.setSongName("songName1_doInTx");
            song.setArtist("artist1_doInTx");
            session.persist(song);

            // search song
            Song foundSong = session.find(Song.class, song.getId());
            session.refresh(foundSong);

            assertEquals(1, foundSong.getId());
            assertEquals("artist1_doInTx", foundSong.getArtist());
        });
    }

    @Test
    void givenSongs_whenList_thenOk() {
        doInTransaction(session -> {
            // save multiple songs
            Song song = new Song();
            song.setSongName("songName1");
            song.setArtist("artist1");
            session.persist(song);

            song = new Song();
            song.setSongName("songName2");
            song.setArtist("artist2");
            session.persist(song);

            // get the list of songs
            Query query = session.createQuery("from Song", Song.class);
            List<Song> resultList = query.getResultList();

            assertEquals(2, resultList.size());
        });
    }

    @Test
    void givenSongs_whenGet_thenOk() {
        doInTransaction(session -> {
            Song song = new Song();
            song.setSongName("songName1");
            song.setArtist("artist1");
            session.persist(song);

            song = new Song();
            song.setSongName("songName25");
            song.setArtist("artist25");
            session.persist(song);

            song = new Song();
            song.setSongName("songName3");
            song.setArtist("artist3");
            session.persist(song);

            // get song2%
            StringBuilder sql = new StringBuilder();
            sql.append("from Song where songName like :title and artist like :artist");

            Query query = session.createQuery(sql.toString(), Song.class)
                    .setParameter("title", "songName2%")
                    .setParameter("artist", "artist2%");

            List<Song> resultList = query.getResultList();

            if (resultList.size() == 1) {
                Song foundSong = (Song) query.getResultList().get(0);
                System.out.println(foundSong);

            } else {
                System.out.println("Not found !");
            }
        });
    }

    private void clearPlaylistTable(Session session) {
        //session.createQuery("delete Song").executeUpdate(); --> DEPRECATED
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaDelete<Song> cd = cb.createCriteriaDelete(Song.class);
        session.createMutationQuery(cd).executeUpdate();
    }

    private void updateAutoIncrement(Session session, int startValue) {
        //session.createNativeQuery("ALTER TABLE playlist AUTO_INCREMENT=1").executeUpdate(); --> DEPRECATED
        session
                .createNativeMutationQuery("ALTER TABLE playlist AUTO_INCREMENT=" + startValue)
                .executeUpdate();
    }
}