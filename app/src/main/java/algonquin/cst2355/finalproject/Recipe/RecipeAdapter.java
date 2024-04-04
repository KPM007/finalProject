package algonquin.cst2355.finalproject.Recipe;

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

import algonquin.cst2355.finalproject.R;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private ArrayList<Recipe> recipes;
    private Context context;
    private OnItemClickListener listener;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    public interface OnItemClickListener {
        void onItemClick(int recipeId);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
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
        Glide.with(holder.itemView.getContext()).load(recipe.getImageUrl()).into(holder.image);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(recipe.getId()));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvRecipeTitle);
            image = itemView.findViewById(R.id.ivRecipeImage);
        }
    }
}