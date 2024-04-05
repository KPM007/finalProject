package algonquin.cst2355.finalproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe_table")
public class Recipe {
@ColumnInfo(name = "title")
    private String title;
    private String imageUrl;
    @ColumnInfo(name = "summary")
    private String summary;
    @ColumnInfo(name="sourceUrl")
    private String sourceUrl;
    @PrimaryKey(autoGenerate = true)
    private int id;
@Ignore
    public Recipe( int id,String title, String imageUrl , String summary ,String sourceUrl) {
        this.id=id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.summary=summary;
        this.sourceUrl=sourceUrl;
    }
    public Recipe(int id,String title, String imageUrl)
    {
        this.id=id;
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

