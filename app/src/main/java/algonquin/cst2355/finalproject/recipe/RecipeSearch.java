package algonquin.cst2355.finalproject.recipe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import algonquin.cst2355.finalproject.R;

public class RecipeSearch extends AppCompatActivity implements RecipeAdapter.OnItemClickListener {
    private EditText etSearchQuery;
    private RecyclerView rvRecipes;
    private ArrayList<Recipe> recipes;
    private RecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_search);

        Button btnSearch = findViewById(R.id.btnSearch);
        etSearchQuery = findViewById(R.id.etSearchQuery);
        rvRecipes = findViewById(R.id.rvRecipes);

        recipes = new ArrayList<>();
        adapter = new RecipeAdapter(this, recipes);
        rvRecipes.setLayoutManager(new LinearLayoutManager(this));
        rvRecipes.setAdapter(adapter);

        adapter.setOnItemClickListener(recipeId -> {
            Intent intent = new Intent(RecipeSearch.this, RecipeDetailsActivity.class);
            intent.putExtra("recipeId", recipeId);
            startActivity(intent);
        });

        btnSearch.setOnClickListener(v -> fetchRecipes());
    }

    public void fetchRecipes() {
        String query = etSearchQuery.getText().toString().trim();
        String url = "https://api.spoonacular.com/recipes/complexSearch?query=" + query + "&apiKey=0cb563b8845f48559da7b3141426a7df";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray results = response.getJSONArray("results");
                        recipes.clear();
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject recipeJson = results.getJSONObject(i);
                            int id = recipeJson.getInt("id");
                            String title = recipeJson.getString("title");
                            String imageUrl = recipeJson.getString("image");
                            recipes.add(new Recipe(id, title, imageUrl));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> error.printStackTrace());

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onItemClick(int recipeId) {
        // Pass selected recipe to RecipeDetailsActivity
        Intent intent = new Intent(RecipeSearch.this, RecipeDetailsActivity.class);
        intent.putExtra("recipeId", recipeId);
        startActivity(intent);

        // Show a Snackbar message or perform any other action if needed
        showSnackbarMessage("Recipe selected: " + recipeId);
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }
}