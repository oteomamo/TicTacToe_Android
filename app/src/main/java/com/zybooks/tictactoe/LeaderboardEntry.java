package com.zybooks.tictactoe;

/*Used in the LeaderboardRepository to get the data for the database */

public class LeaderboardEntry {
    private String playerName;
    private int score;

    public LeaderboardEntry(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }
}
