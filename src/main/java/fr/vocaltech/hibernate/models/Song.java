package fr.vocaltech.hibernate.models;


import jakarta.persistence.*;

@Entity
@Table(name = "playlist")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "songId")
    private int id;

    @Column(name = "songName")
    private String songName;

    @Column(name = "singer")
    private String artist;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", songName='" + songName + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }
}
