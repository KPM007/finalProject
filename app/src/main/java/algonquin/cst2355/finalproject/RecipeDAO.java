package algonquin.cst2355.finalproject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface RecipeDAO {
    @Insert
    void insert(Recipe recipe);

    @Query("DELETE FROM recipe_table")
    void deleteAllRecipes();

    @Query("SELECT * FROM recipe_table ORDER BY title ASC")
    LiveData<List<Recipe>> getAllRecipes();
}
