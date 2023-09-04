package com.zybooks.tictactoe;

import android.provider.BaseColumns;


/* **************** define the schema of a database table ***************** */

public final class  LeaderboardContents {
    private LeaderboardContents() {}

    public static class LeaderboardEntry implements BaseColumns {
        public static final String TABLE_NAME = "leaderboard";
        public static final String COLUMN_NAME_PLAYER_NAME = "player_name";
        public static final String COLUMN_NAME_GAMES_WON = "games_won";
    }


}
