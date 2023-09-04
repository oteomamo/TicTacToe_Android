package com.zybooks.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivityPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
    }

    // start the Tic Tac Toe game
    public void startGame(View view) {
        Intent intent = new Intent(this, SelectPlayersActivity.class);
        intent.putExtra("game_type", "Normal");
        startActivity(intent);
    }

    public void viewRecords(View view) {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
    }

    public void startBlitzGame(View view) {
        Intent intent = new Intent(this, SelectPlayersActivity.class);
        intent.putExtra("game_type", "Blitz");
        startActivity(intent);
    }

    public void deleteAllScores(View view) {
        LeaderboardRepository leaderboardRepository = LeaderboardRepository.getInstance(this);
        leaderboardRepository.deleteAllScores();
        Toast.makeText(this, "All scores have been deleted", Toast.LENGTH_SHORT).show();
    }
}

