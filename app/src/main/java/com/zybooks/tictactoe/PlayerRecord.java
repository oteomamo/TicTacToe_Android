package com.zybooks.tictactoe;

public class PlayerRecord {
    private String playerName;
    private int gamesWon;

    public PlayerRecord(String playerName, int gamesWon) {
        this.playerName = playerName;
        this.gamesWon = gamesWon;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void incrementGamesWon() {
        this.gamesWon++;
    }
}

