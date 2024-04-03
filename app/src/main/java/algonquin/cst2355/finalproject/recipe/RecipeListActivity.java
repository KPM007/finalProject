package algonquin.cst2355.finalproject.recipe;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import java.util.ArrayList;
import java.util.List;
import algonquin.cst2355.finalproject.R;

public class RecipeListActivity extends AppCompatActivity implements RecipeAdapter.OnItemClickListener {
    private RecyclerView rvSavedRecipes;
    private ArrayList<Recipe> savedRecipes;
    private RecipeAdapter adapter;
    private RecipeDAO recipeDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_recipe);

        rvSavedRecipes = findViewById(R.id.rvSavedRecipes);
        savedRecipes = new ArrayList<>();
        adapter = new RecipeAdapter(this, savedRecipes);
        adapter.setOnItemClickListener(this);
        rvSavedRecipes.setLayoutManager(new LinearLayoutManager(this));
        rvSavedRecipes.setAdapter(adapter);

        // Initialize Room database
        Recipe_database database = Room.databaseBuilder(getApplicationContext(),
                Recipe_database.class, "recipe-database").build();
        recipeDAO = database.recipeDao();

        // Load saved recipes from the database
        loadSavedRecipes();
    }

    private void loadSavedRecipes() {
        new Thread(() -> {
            List<Recipe> recipes = recipeDAO.getAllEntries();
            runOnUiThread(() -> {
                savedRecipes.clear();
                savedRecipes.addAll(recipes);
                adapter.notifyDataSetChanged();
            });
        }).start();
    }

    @Override
    public void onItemClick(int position) {
        // Handle recipe item click if needed
        // For example, you can show an AlertDialog to confirm deletion
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Recipe")
                .setMessage("Are you sure you want to delete this recipe?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    // Remove the recipe from the database and the list
                    Recipe recipeToDelete = savedRecipes.get(position);
                    new Thread(() -> {
                        recipeDAO.delete(recipeToDelete);
                        runOnUiThread(() -> {
                            savedRecipes.remove(position);
                            adapter.notifyItemRemoved(position);
                            Toast.makeText(this, "Recipe deleted", Toast.LENGTH_SHORT).show();
                        });
                    }).start();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }


    public void onItemDelete(int position) {
        // Remove the recipe from the database
        Recipe recipeToDelete = savedRecipes.get(position);
        new Thread(() -> {
            recipeDAO.delete(recipeToDelete);
            runOnUiThread(() -> {
                savedRecipes.remove(position);
                adapter.notifyItemRemoved(position);
                Toast.makeText(this, "Recipe deleted", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }

}
