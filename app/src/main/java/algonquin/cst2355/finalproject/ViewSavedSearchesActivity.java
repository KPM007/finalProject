package algonquin.cst2355.finalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewSavedSearchesActivity {

    package algonquin.cst2335.dictionary;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2355.dictionary.R;

    public class ViewSavedSearchesActivity extends AppCompatActivity {
        RecyclerView recyclerView;
        SavedSearchesAdapter adapter;
        List<SearchEntry> searchHistory = new ArrayList<>();

        Button deleteButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_view_saved_searches);

            recyclerView = findViewById(R.id.yourRecyclerViewId);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new SavedSearchesAdapter(this, searchHistory);
            recyclerView.setAdapter(adapter);
            deleteButton = findViewById(R.id.Delete_button);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Call a method to delete the search history
                    deleteSearchHistory();
                }
            });

            loadSearchHistory();
        }

        private void loadSearchHistory() {
            AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
            new Thread(() -> {
                List<SearchEntry> entries = db.searchEntryDao().getAllSync();
                runOnUiThread(() -> {
                    searchHistory.clear();
                    searchHistory.addAll(entries);
                    adapter.notifyDataSetChanged();
                });
            }).start();
        }

        private void deleteSearchHistory() {
            // Clear the search history list and notify the adapter
            searchHistory.clear();
            adapter.notifyDataSetChanged();

            // Delete search history from the database
            AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
            new Thread(() -> {
                db.searchEntryDao().deleteAll(); // Assuming you have a method to delete all entries in your DAO
            }).start();
        }
    }

}