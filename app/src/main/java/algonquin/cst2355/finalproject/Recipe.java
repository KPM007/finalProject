package algonquin.cst2355.finalproject;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe_table")
public class Recipe {

    private String title;
    private String imageUrl;
    private String summary;
    private String sourceUrl;
    @PrimaryKey(autoGenerate = true)
    private int id;

    public Recipe( int id,String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public String getSummary()
    { return summary; }
    public String getSourceUrl()
    { return sourceUrl; }
    public int getId()
    { return id; }
    public void setSummary(String summary)
    { this.summary = summary; }
    public void setSourceUrl
            (String sourceUrl) { this.sourceUrl = sourceUrl; }
}
