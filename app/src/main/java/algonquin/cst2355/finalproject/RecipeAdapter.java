package algonquin.cst2355.finalproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

import algonquin.cst2335.recipesearchapi.R;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private ArrayList<Recipe> recipes;
    private Context context;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }



    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.title.setText(recipe.getTitle());
        Glide.with(context)
                .load(recipe.getImageUrl())
                .into(holder.image);
        holder.itemView.setOnClickListener(v -> {
            if(context instanceof MainActivity) {
                Intent detailIntent = new Intent(context, RecipeDetailsActivity.class);
                detailIntent.putExtra("title", recipe.getTitle());
                detailIntent.putExtra("imageUrl", recipe.getImageUrl());
                detailIntent.putExtra("summary", recipe.getSummary());
                detailIntent.putExtra("sourceUrl", recipe.getSourceUrl());
                context.startActivity(detailIntent);
            }
        });
        // Set an OnClickListener to handle clicks on the recipe item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Assuming MainActivity has a method fetchRecipeDetails(int recipeId) to fetch details
                if (context instanceof MainActivity) {
                    ((MainActivity) context).fetchRecipeDetails(recipe.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvRecipeTitle);
            image = itemView.findViewById(R.id.ivRecipeImage);
        }
    }
}
