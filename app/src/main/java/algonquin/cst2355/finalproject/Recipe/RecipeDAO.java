package algonquin.cst2355.finalproject.Recipe;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

import algonquin.cst2355.finalproject.Recipe.Recipe;

@Dao
public interface RecipeDAO {
    @Insert
    void insert(Recipe recipe);

    @Delete
    void delete(Recipe recipe);

    @Query("SELECT * FROM recipe_table")
    List<Recipe> getAllEntries();
    @Query("DELETE  FROM recipe_table")
    void delete_All();
}
