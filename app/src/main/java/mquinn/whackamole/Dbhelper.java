package mquinn.whackamole;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Matthew Quinn
 * 5/4/17
 *
 * This class handles the SQLite database we use to keep track of scores
 *
 */

public class Dbhelper extends SQLiteOpenHelper {

    // Some statics to use variables for database info, which makes it easier to upgrade
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "highscores.db";
    private static final String TABLE_NAME = "scoreboard";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SCORE = "score";

    // SQL statement to create our main table
    private static final String TABLE_CREATE = "CREATE TABLE " +
                                                TABLE_NAME + " (" +
                                                COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                                                COLUMN_NAME + " TEXT NOT NULL, "+
                                                COLUMN_SCORE + " TEXT NOT NULL);";

    SQLiteDatabase db;

    public Dbhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // onCreate bundle executes our query and makes the table
        db.execSQL(TABLE_CREATE);

    }

    // Method using our Player class to add a player upon submission
    public void addPlayer(Player newPlayer) {

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Using the DB cursor to insert our new player
        String query = "SELECT * FROM " +  TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(COLUMN_ID, count);
        values.put(COLUMN_NAME, newPlayer.getVarName());
        values.put(COLUMN_SCORE, newPlayer.getVarScore());

        // Insert the new player and close off the cursor
        db.insert(TABLE_NAME, null, values);
        db.close();

    }

    // Method to return our player list to populate the scoreboard
    public List<Player> getAll() {

        // Building the Arraylist of players
        List<Player> dataList = new ArrayList<>();
        db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
                do {
                    Player data = new Player();
                    data.setVarName(cursor.getString(1));
                    data.setVarScore(Integer.parseInt(cursor.getString(2)));
                    dataList.add(data);
                }
            while(cursor.moveToNext());
        }
        return dataList;
    }

    // Method to truncate the database
    public void deleteAll(){

        db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();

    }

    // Stub left in, in case of updating DB between versions
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String Upgrade = "DROP TABLE IF EXISTS" + TABLE_NAME;
        db.execSQL(Upgrade);
        this.onCreate(db);

    }
}