package algonquin.cst2335.recipesearchapi;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.VolleyError;
import algonquin.cst2355.finalproject.RecipeAdapter;
import algonquin.cst2355.finalproject.Recipe;

import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText etSearchQuery;

    private RecyclerView rvRecipes;
    private ArrayList<Recipe> recipes;
    private RecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);
        Button btnSearch = findViewById(R.id.btnSearch);

        etSearchQuery = findViewById(R.id.etSearchQuery);
        rvRecipes = findViewById(R.id.rvRecipes);
        recipes = new ArrayList<>();
        adapter = new RecipeAdapter(this, recipes);
        rvRecipes.setLayoutManager(new LinearLayoutManager(this));
        rvRecipes.setAdapter(adapter);
        fetchRecipes(); // Call this method when appropriate, e.g., after a search
        btnSearch.setOnClickListener(new View.OnClickListener() { // Set the OnClickListener
            @Override
            public void onClick(View v) {
                fetchRecipes(); // Call fetchRecipes when the button is clicked
            }
        });
    }
    // 700cf69419424c40a5412d2fe7d9fe73
    private void fetchRecipes() {
        String query = etSearchQuery.getText().toString().trim();
        String url = "https://api.spoonacular.com/recipes/complexSearch?query=" + query + "&apiKey=0cb563b8845f48559da7b3141426a7df"; // Replace YOUR_API_KEY with your actual API key

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray results = jsonObject.getJSONArray("results");
                        recipes.clear();
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject recipeJson = results.getJSONObject(i);
                            int  id     = recipeJson.getInt("id");
                            String title = recipeJson.getString("title");
                            String imageUrl = recipeJson.getString("image"); // Assuming 'image' is the key
                            recipes.add(new Recipe( title, imageUrl));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> error.printStackTrace());

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void fetchRecipeDetails(int recipeId) {
        String apiKey = "0cb563b8845f48559da7b3141426a7df"; // Replace this with your actual API key
        String detailUrl = "https://api.spoonacular.com/recipes/" + recipeId + "/information?apiKey=" + apiKey;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, detailUrl,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        // Assuming these details are available in the response
                        int  id     = jsonObject.getInt("id");
                        String imageUrl = jsonObject.getString("image");
                        String summary = jsonObject.getString("summary");
                        String sourceUrl = jsonObject.getString("spoonacularSourceUrl");

                        // Update UI with these details
                        // For simplicity, using Toast, but you should update your UI as needed
                        Toast.makeText(MainActivity.this, "Summary: " + summary, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(MainActivity.this, "Error fetching recipe details", Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}