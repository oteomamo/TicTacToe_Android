package com.zybooks.tictactoe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class LeaderboardRepository {
    private static LeaderboardRepository instance;

    private LeaderboardDbHelper dbHelper;

    /** Using an instance of LeaderboardDbHelper this class provides methods
    * for add, update, managing scores, retrieving the players from the database
    * and checking if they exist */

    public LeaderboardRepository(Context context) {
        dbHelper = new LeaderboardDbHelper(context);
    }

    public static LeaderboardRepository getInstance(Context context) {
        if (instance == null) {
            instance = new LeaderboardRepository(context);
        }
        return instance;
    }

    public void addNewPlayer(String playerName) {
        if (!playerExists(playerName)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Create new values to insert based on data given
            ContentValues values = new ContentValues();
            values.put(LeaderboardContents.LeaderboardEntry.COLUMN_NAME_PLAYER_NAME, playerName);
            values.put(LeaderboardContents.LeaderboardEntry.COLUMN_NAME_GAMES_WON, 0);

            // Insert the new player
            db.insertWithOnConflict(
                    LeaderboardContents.LeaderboardEntry.TABLE_NAME,
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_REPLACE
            );
        }
    }

    public void updateScore(String playerName, int newScore) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LeaderboardContents.LeaderboardEntry.COLUMN_NAME_GAMES_WON, newScore);

        String selection = LeaderboardContents.LeaderboardEntry.COLUMN_NAME_PLAYER_NAME + " = ?";
        String[] selectionArgs = {playerName};

        int count = db.update(
                LeaderboardContents.LeaderboardEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );

        Log.d("LeaderboardRepository", "Updated score for player: " + playerName + ", new score: " + newScore + ", rows affected: " + count);
    }

    public void deleteAllScores() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(LeaderboardContents.LeaderboardEntry.TABLE_NAME, null, null);
    }


    public Cursor getAllPlayers() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                LeaderboardContents.LeaderboardEntry._ID,
                LeaderboardContents.LeaderboardEntry.COLUMN_NAME_PLAYER_NAME,
                LeaderboardContents.LeaderboardEntry.COLUMN_NAME_GAMES_WON
        };

        String sortOrder = LeaderboardContents.LeaderboardEntry.COLUMN_NAME_GAMES_WON + " DESC";

        return db.query(
                LeaderboardContents.LeaderboardEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
    }


    


    public int getScore(String playerName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = { LeaderboardContents.LeaderboardEntry.COLUMN_NAME_GAMES_WON };

        String selection = LeaderboardContents.LeaderboardEntry.COLUMN_NAME_PLAYER_NAME + " = ?";
        String[] selectionArgs = { playerName };

        Cursor cursor = db.query(
                LeaderboardContents.LeaderboardEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            int scoreColumnIndex = cursor.getColumnIndex(LeaderboardContents.LeaderboardEntry.COLUMN_NAME_GAMES_WON);
            int score = cursor.getInt(scoreColumnIndex);
            cursor.close();
            return score;
        } else {
            cursor.close();
            addNewPlayer(playerName);
            return 0;
        }
    }

    public boolean playerExists(String playerName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = LeaderboardContents.LeaderboardEntry.COLUMN_NAME_PLAYER_NAME + " = ?";
        String[] selectionArgs = { playerName };

        Cursor cursor = db.query(
                LeaderboardContents.LeaderboardEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }
}
