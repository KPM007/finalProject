package algonquin.cst2355.finalproject.recipe;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;


@Dao
public interface RecipeDAO {
    @Insert
    void insert(Recipe recipe);

    @Query("SELECT * FROM recipe_table")
    List<Recipe> getAllEntries();
}
