package algonquin.cst2355.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavouritesDao extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FavoritesDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "favorites";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_LAT = "lat";
    private static final String COLUMN_LNG = "lng";
    private static final String COLUMN_SUNRISE = "sunrise";

    public FavouritesDao(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static void delete(String favorites, String s, String[] strings) {
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FAVORITES_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_LAT + " TEXT,"
                + COLUMN_LNG + " TEXT,"
                + COLUMN_SUNRISE + " TEXT" + ")";
        db.execSQL(CREATE_FAVORITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    public void addFavorite(String lat, String lng, String sunrise) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_LAT, lat);
        values.put(COLUMN_LNG, lng);
        values.put(COLUMN_SUNRISE, sunrise);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Cursor getAllFavorites() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public void deleteFavorite(String lat, String lng) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_LAT + "=? AND " + COLUMN_LNG + "=?", new String[]{lat, lng});
        db.close();
    }
}
