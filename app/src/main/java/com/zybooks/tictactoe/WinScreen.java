package com.zybooks.tictactoe;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

public class WinScreen extends AppCompatActivity{
    private TextView winnerText;
    private String playerOneName;
    private String playerTwoName;
    private String gameType;

    private LeaderboardRepository leaderboardRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_screen);

        // Update teh winner
        leaderboardRepository = LeaderboardRepository.getInstance(this);

        Intent intent = getIntent();
        playerOneName = intent.getStringExtra("playerOneName");
        playerTwoName = intent.getStringExtra("playerTwoName");
        String winner = intent.getStringExtra("winner");
        updateWinnerScore(winner);
        gameType = intent.getStringExtra("game_type");
        winnerText = findViewById(R.id.winner_text);

        winnerText.setText(winner + " Won!");
    }



    private void updateWinnerScore(String winnerName) {
        // Get the current score and update it
        int currentScore = leaderboardRepository.getScore(winnerName);
        leaderboardRepository.updateScore(winnerName, currentScore + 1);
    }



    public void viewRecords(View view) {
        Intent intent = new Intent(WinScreen.this, LeaderboardRepository.class);
        startActivity(intent);
    }

    public void mainMenu(View view) {
        //return the user to the main menu
        Intent intent = new Intent(WinScreen.this, MainActivityPage.class);
        startActivity(intent);
    }

    public void viewLeaderboard(View view) {
        Intent intent = new Intent(WinScreen.this, LeaderboardActivity.class);
        startActivity(intent);
    }



    public void playAgain(View view) {
        //returns the user to the game using the same players
        Intent intent;
        if (gameType.equals("Blitz")){
            intent = new Intent(WinScreen.this, MainActivityB.class);
        } else{
            intent = new Intent(WinScreen.this, MainActivity.class);
        }
        //gets the names of the players and sends them back to the tic tac toe board
        intent.putExtra("playerOneName", playerOneName);
        intent.putExtra("playerTwoName", playerTwoName);
        startActivity(intent);
    }


}
