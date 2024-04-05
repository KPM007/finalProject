package algonquin.cst2355.finalproject;

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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2355.finalproject.R;

public class MainActivity extends AppCompatActivity {
    EditText etSearchTerm;

    Button btnSearch, btnViewSaved;
    RecyclerView rvDefinitions;
    DefinitionsAdapter adapter;
    List<Definition> definitionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSearchTerm = findViewById(R.id.etSearchTerm);
        btnSearch = findViewById(R.id.btnSearch);
        rvDefinitions = findViewById(R.id.rvDefinitions);
        btnViewSaved = findViewById(R.id.btnViewSaved);

        rvDefinitions.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DefinitionsAdapter(definitionList);
        rvDefinitions.setAdapter(adapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String term = etSearchTerm.getText().toString();
                fetchDefinitions(term);
                saveSearchTerm(term);

            }
        });

        btnViewSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewSavedSearchesActivity.class);
                startActivity(intent);
            }
        });

        btnViewSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewSavedSearchesActivity.class);
                startActivity(intent);
            }
        });

    }

    private void fetchDefinitions(String term) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.dictionaryapi.dev/api/v2/entries/en/" + term;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Clear the current definitions
                            definitionList.clear();

                            JSONArray entries = new JSONArray(response);
                            for (int i = 0; i < entries.length(); i++) {
                                JSONObject entry = entries.getJSONObject(i);
                                JSONArray meanings = entry.getJSONArray("meanings");
                                for (int j = 0; j < meanings.length(); j++) {
                                    JSONObject meaning = meanings.getJSONObject(j);
                                    JSONArray definitionsArray = meaning.getJSONArray("definitions");
                                    for (int k = 0; k < definitionsArray.length(); k++) {
                                        JSONObject definitionObject = definitionsArray.getJSONObject(k);
                                        String definitionText = definitionObject.getString("definition");
                                        definitionList.add(new Definition(definitionText));
                                    }
                                }
                            }

                            // Notify the adapter of the data change on the UI thread
                            runOnUiThread(() -> {
                                adapter.notifyDataSetChanged();
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error parsing the definitions", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error fetching the definitions", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);
    }
    private void saveSearchTerm(String searchTerm) {
        Gson gson = new Gson();
        String definitionsJson = gson.toJson(definitionList); // Convert list of definitions to JSON String

        SearchEntry searchEntry = new SearchEntry();
        searchEntry.searchTerm = searchTerm;
        searchEntry.definitions = definitionsJson;

        AppDatabase db = AppDatabase.getDatabase(this);
        new Thread(() -> db.searchEntryDao().insert(searchEntry)).start(); // Save to database in a background thread
    }


}
