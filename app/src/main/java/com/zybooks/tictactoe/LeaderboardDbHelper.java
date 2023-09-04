package com.zybooks.tictactoe;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LeaderboardDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    /* ***********database file******************* */
    public static final String DATABASE_NAME = "leaderboard.db";

    /* *********SQL statement to create the "leaderboard" table*********** */
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + LeaderboardContents.LeaderboardEntry.TABLE_NAME + " (" +
                    LeaderboardContents.LeaderboardEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    LeaderboardContents.LeaderboardEntry.COLUMN_NAME_PLAYER_NAME + " TEXT," +
                    LeaderboardContents.LeaderboardEntry.COLUMN_NAME_GAMES_WON + " INTEGER)";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + LeaderboardContents.LeaderboardEntry.TABLE_NAME;


    public LeaderboardDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    /* *********database created for the first time********* */
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    /* *********database update from one version to another ********* */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}

