# Hibernate workshop

Some tutorials to master Hibernate framework.
<br>
We are using MariaDb as target database.

### Model: Song

Below, we see the mappings between the `Song` class and the `playlist` table.
```
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
}
```

![song_table](assets/song_table.png)