package algonquin.cst2355.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "DeezerSongDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_FAVORITES = "favorites";
    private static final String TABLE_SONG_DETAILS = "songdetails";
    private static final String COLUMN_ID = "id";
    public static final String COLUMN_ARTIST_NAME = "artistName";
    private static final String COLUMN_TRACKLIST_URL = "tracklistUrl";
    private static final String COLUMN_SONG_TITLE = "songTitle";
    private static final String COLUMN_DURATION = "duration";
    // Add more columns for song details if needed

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table for favorites
        String CREATE_FAVORITES_TABLE = "CREATE TABLE " + TABLE_FAVORITES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ARTIST_NAME + " TEXT,"
                + COLUMN_TRACKLIST_URL + " TEXT" + ")";
        db.execSQL(CREATE_FAVORITES_TABLE);

        // Create table for song details
        String CREATE_SONG_DETAILS_TABLE = "CREATE TABLE " + TABLE_SONG_DETAILS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ARTIST_NAME + " TEXT,"
                + COLUMN_SONG_TITLE + " TEXT,"
                + COLUMN_DURATION + " INTEGER" + ")";
        db.execSQL(CREATE_SONG_DETAILS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG_DETAILS);
        // Create tables again
        onCreate(db);
    }

    // Methods for managing favorites
    public void addFavorite(String artistName, String tracklistUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ARTIST_NAME, artistName);
        values.put(COLUMN_TRACKLIST_URL, tracklistUrl);
        db.insert(TABLE_FAVORITES, null, values);
        db.close();
    }

    public Cursor getAllFavorites() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_FAVORITES, null);
    }

    public void deleteFavorite(String artistName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, COLUMN_ARTIST_NAME + "=?", new String[]{artistName});
        db.close();
    }
}
