package algonquin.cst2355.finalproject;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "songs")
public class Song {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "artistName")
    private String artistName;
    @ColumnInfo(name = "albumName")
    private String albumName;
    @ColumnInfo(name = "durationInSeconds")
    private int durationInSeconds;

    @ColumnInfo(name = "coverUrl")
    private String coverUrl;
    // Constructor
    public Song(int id, String title, String artistName, String albumName, int durationInSeconds, String coverUrl) {
        this.id = id;
        this.title = title;
        this.artistName = artistName;
        this.albumName = albumName;
        this.durationInSeconds = durationInSeconds;
        this.coverUrl = coverUrl;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setTitle(String string) {
    }

    public void setArtist(String string) {
    }

    public void setDuration(String string) {
    }

    public void setAlbum(String string) {

    }
}
