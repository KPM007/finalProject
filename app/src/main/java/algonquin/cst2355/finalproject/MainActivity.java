package algonquin.cst2355.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import algonquin.cst2355.finalproject.Recipe.RecipeSearch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_more_apps) {
            Intent intent = new Intent(this, Sunrise.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_more_apps2) {
            Intent intent = new Intent(this, DeezerSongSearch.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_more_apps3) {
            Intent intent = new Intent(this, Dictionary.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_more_apps4) {
            Intent intent = new Intent(this, RecipeSearch.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}