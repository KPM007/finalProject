package algonquin.cst2355.finalproject;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder> {
    private List<Artist> artistList;
    private OnItemClickListener listener;

    // Constructor
    public ArtistsAdapter(List<Artist> artistList) {
        this.artistList = artistList;
    }

    // Interface to handle item clicks
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Method to set the item click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_item, parent, false);
        return new ArtistViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        Artist artist = artistList.get(position);
        holder.artistName.setText(artist.getName());

        // Load artist image using Glide
        Glide.with(holder.itemView.getContext())
                .load(artist.getPictureMedium())
                .into(holder.artistImage);

        // Set click listener for the item
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    public static class ArtistViewHolder extends RecyclerView.ViewHolder {
        public TextView artistName;
        public ImageView artistImage;

        public ArtistViewHolder(View view) {
            super(view);
            artistName = view.findViewById(R.id.artistName);
            artistImage = view.findViewById(R.id.artistImage);
        }
    }
}