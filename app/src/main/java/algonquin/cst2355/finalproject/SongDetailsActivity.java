package algonquin.cst2355.finalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SongDetailsActivity extends AppCompatActivity {

    private TextView textViewArtistName;
    private TextView textViewSongTitle;
    private TextView textViewDuration;
    private TextView textViewAlbumName;
    private Button buttonSave;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_details);

        // Initialize views
        textViewArtistName = findViewById(R.id.textViewArtistName);
        textViewSongTitle = findViewById(R.id.textViewSongTitle);
        textViewDuration = findViewById(R.id.textViewDuration);
        textViewAlbumName = findViewById(R.id.textViewAlbumName);
        buttonSave = findViewById(R.id.buttonSave);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Retrieve song details from intent
        Intent intent = getIntent();
        final String artistName = intent.getStringExtra("artistName");
        String tracklistUrl = intent.getStringExtra("tracklistUrl");

        // Set artist name to the TextView
        textViewArtistName.setText("Name: " + artistName);

        // Fetch song details from Deezer API
        fetchSongDetails(tracklistUrl);

        // Set onClickListener for the Save button
        buttonSave.setOnClickListener(view -> saveSongData(artistName, tracklistUrl));
    }

    private void fetchSongDetails(String tracklistUrl) {
        // Make a GET request to the tracklistUrl
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, tracklistUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse response to get song details
                            JSONArray tracks = response.getJSONArray("data");
                            if (tracks.length() > 0) {
                                JSONObject track = tracks.getJSONObject(0);
                                // Assuming the first track
                                String title = track.getString("title");
                                String duration = track.getString("duration");
                                String albumName = track.getJSONObject("album").getString("title");

                                // Update UI with song details
                                textViewSongTitle.setText("Song Title: " + title);
                                textViewDuration.setText("Duration: " + duration);
                                textViewAlbumName.setText("Album Name: " + albumName);
                            } else {
                                Toast.makeText(SongDetailsActivity.this, "No tracks found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Log.e("SongDetailsActivity", "Error parsing JSON response: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("SongDetailsActivity", "Error fetching song details: " + error.getMessage());
                        Toast.makeText(SongDetailsActivity.this, "Error fetching song details", Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void saveSongData(String artistName, String tracklistUrl) {
        // Check if the song is already saved
        if (isSongSaved(artistName)) {
            Toast.makeText(this, "Song already saved", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insert the song into the database using DatabaseHelper
        databaseHelper.addFavorite(artistName, tracklistUrl);
        Toast.makeText(this, "Song saved", Toast.LENGTH_SHORT).show();
    }

    private boolean isSongSaved(String artistName) {
        // Check if the song with the given artist name exists in the database
        Cursor cursor = databaseHelper.getAllFavorites();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String savedArtistName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ARTIST_NAME));
                if (artistName.equals(savedArtistName)) {
                    cursor.close();
                    return true;
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        return false;
    }
}
