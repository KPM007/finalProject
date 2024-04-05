package algonquin.cst2355.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2355.finalproject.R;

public class SavedSearchesAdapter extends RecyclerView.Adapter<SavedSearchesAdapter.SearchEntryViewHolder> {
    private List<SearchEntry> searchEntries;
    private LayoutInflater mInflater;

    public SavedSearchesAdapter(Context context, List<SearchEntry> searchEntries) {
        this.mInflater = LayoutInflater.from(context);
        this.searchEntries = searchEntries;
    }

    @NonNull
    @Override
    public SearchEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.search_entry_item, parent, false);
        return new SearchEntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchEntryViewHolder holder, int position) {
        holder.searchTermTextView.setText(searchEntries.get(position).searchTerm);
        // You can also set onClickListener here if you want to add interaction
    }

    @Override
    public int getItemCount() {
        return searchEntries.size();
    }

    public static class SearchEntryViewHolder extends RecyclerView.ViewHolder {
        TextView searchTermTextView;

        public SearchEntryViewHolder(@NonNull View itemView) {
            super(itemView);
            searchTermTextView = itemView.findViewById(R.id.tvSearchTerm);
        }
    }
}
