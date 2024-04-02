package algonquin.cst2355.finalproject.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2355.finalproject.R;

public class RecipeDetailsActivity extends AppCompatActivity {
    private TextView textViewRecipeTitle, textViewSummary, textViewSourceUrl;
    private ImageView imageViewRecipe;

    private RecipeDAO rDAO;
    private ArrayList<Recipe> RecipeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_recipe);

        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() ->
        {
            Recipe_database db = Room.databaseBuilder(getApplicationContext(), Recipe_database.class, getString(R.string.databaseName)).build();
            rDAO = db.recipeDao();
            rDAO.getAllEntries();

        });

        textViewRecipeTitle = findViewById(R.id.recipe_title);
        textViewSummary = findViewById(R.id.recipe_summary);
        textViewSourceUrl = findViewById(R.id.recipe_source_url);
        imageViewRecipe = findViewById(R.id.recipe_image);

        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("title");
            String imageUrl = intent.getStringExtra("imageUrl");
            String summary = intent.getStringExtra("summary");
            String sourceUrl = intent.getStringExtra("sourceUrl");

            textViewRecipeTitle.setText(title);
            Glide.with(this).load(imageUrl).into(imageViewRecipe);
            textViewSummary.setText(summary);
            textViewSourceUrl.setText(sourceUrl);
        }

        int recipeId = getIntent().getIntExtra("recipeId", -1);
        if (recipeId != -1) {
            fetchRecipeDetails(recipeId);
        } else {
            Toast.makeText(this, "Invalid Recipe ID", Toast.LENGTH_LONG).show();
        }
    }





    public void fetchRecipeDetails(int recipeId) {
        String apiKey = "0cb563b8845f48559da7b3141426a7df";
        String detailUrl = "https://api.spoonacular.com/recipes/" + recipeId + "/information?apiKey=" + apiKey;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, detailUrl, null,
                response -> {
                    try {
                        int id = response.getInt("id");
                        String title = response.getString("title");
                        String imageUrl = response.getString("image");
                        String summary = response.getString("summary");
                        String sourceUrl = response.getString("sourceUrl");

                        // Remove HTML tags from the summary
                        summary = removeHtmlTags(summary);

                        Glide.with(RecipeDetailsActivity.this).load(imageUrl).into(imageViewRecipe);
                        textViewSummary.setText(summary);
                        textViewSourceUrl.setText(sourceUrl);

                        Recipe recipe = new Recipe(id, title, imageUrl, summary, sourceUrl);
                        saveRecipeToDatabase(recipe);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(RecipeDetailsActivity.this, "Error fetching recipe details", Toast.LENGTH_SHORT).show();
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

    }

    // Utility method to remove HTML tags from a string
    private String removeHtmlTags(String html) {
        return android.text.Html.fromHtml(html).toString();
    }
    private void saveRecipeToDatabase(Recipe recipe) {
        // Initialize Room database
        Recipe_database database = Room.databaseBuilder(getApplicationContext(),
                Recipe_database.class, "recipe-database").build();

        // Insert recipe into database using DAO
        new Thread(() -> {
            database.recipeDao().insert(recipe);
            runOnUiThread(() -> Toast.makeText(this, "Recipe saved to database", Toast.LENGTH_SHORT).show());
        }).start();
    }
}
