package algonquin.cst2355.finalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import org.json.JSONException;
import org.json.JSONObject;

import algonquin.cst2335.recipesearchapi.R;

public class RecipeDetailsActivity extends AppCompatActivity {

    private TextView textViewRecipeTitle, textViewSummary, textViewSourceUrl;
    private ImageView imageViewRecipe;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);

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
            textViewSummary.setText(android.text.Html.fromHtml(summary)); // Use Html.fromHtml if your summary contains HTML
            textViewSourceUrl.setText(sourceUrl);
        }
    }
}

