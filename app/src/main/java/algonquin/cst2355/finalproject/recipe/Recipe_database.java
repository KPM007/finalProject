package algonquin.cst2355.finalproject.Recipe;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import algonquin.cst2355.finalproject.Recipe.Recipe;
import algonquin.cst2355.finalproject.Recipe.RecipeDAO;

@Database(entities = {Recipe.class}, version = 1)
public abstract class
Recipe_database extends RoomDatabase {
    private static volatile Recipe_database instance;

    public abstract RecipeDAO recipeDao();

    public static synchronized Recipe_database getInstance(Context context) {
        if (instance == null) {
            synchronized (Recipe_database.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    Recipe_database.class, "recipe_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
