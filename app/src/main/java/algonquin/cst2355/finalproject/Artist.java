package algonquin.cst2355.finalproject;



import java.io.Serializable;

public class Artist implements Serializable {
    public int id;
    public String name;
    public String tracklist; // URL to their songs

    public String pictureMedium;

    // Constructor
    public Artist(int id, String name, String tracklist, String pictureMedium) {
        this.id = id;
        this.name = name;
        this.tracklist = tracklist;
        this.pictureMedium= pictureMedium;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTracklist() {
        return tracklist;
    }

    public String getPictureMedium() {return pictureMedium;}


}
