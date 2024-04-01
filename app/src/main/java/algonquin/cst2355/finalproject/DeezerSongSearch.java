package algonquin.cst2355.finalproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DeezerSongSearch extends AppCompatActivity implements ArtistsAdapter.OnItemClickListener {
    private List<Artist> artistList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ArtistsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deezer_song_search);

        recyclerView = findViewById(R.id.artistsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ArtistsAdapter(artistList);
        adapter.setOnItemClickListener(this); // Set the item click listener
        recyclerView.setAdapter(adapter);

        EditText searchEditText = findViewById(R.id.searchEditText);
        Button searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(view -> searchArtists(searchEditText.getText().toString()));
    }

    @Override
    public void onItemClick(int position) {
        Artist selectedArtist = artistList.get(position);

        // Pass selected artist to SongDetailsActivity
        Intent intent = new Intent(DeezerSongSearch.this, SongDetailsActivity.class);
        intent.putExtra("artistName", selectedArtist.getName());
        intent.putExtra("tracklistUrl", selectedArtist.getTracklist());
        startActivity(intent);

        // Show a Snackbar message or perform any other action if needed
        showSnackbarMessage("Artist selected: " + selectedArtist.getName());
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }

    private void searchArtists(String query) {
        String url = "https://api.deezer.com/search/artist/?q=" + Uri.encode(query);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray artistsArray = response.getJSONArray("data");
                        artistList.clear();
                        for (int i = 0; i < artistsArray.length(); i++) {
                            JSONObject artistObj = artistsArray.getJSONObject(i);
                            Artist artist = new Artist(
                                    artistObj.getInt("id"),
                                    artistObj.getString("name"),
                                    artistObj.getString("tracklist"),
                                    artistObj.getString("picture_medium")
                            );
                            artistList.add(artist);
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("Artist Search", "Error: " + error.toString())
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }
}