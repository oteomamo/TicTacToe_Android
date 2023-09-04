package com.zybooks.tictactoe;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TimeUpScreen extends AppCompatActivity {
    private TextView winnerText;
    private String playerOneName;
    private String playerTwoName;
    private String gameType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_up);

        Intent intent = getIntent();
        playerOneName = intent.getStringExtra("playerOneName");
        playerTwoName = intent.getStringExtra("playerTwoName");
        gameType = intent.getStringExtra("game_type");
    }

    public void mainMenu(View view) {
        //return the user to the main menu
        Intent intent = new Intent(TimeUpScreen.this, MainActivityPage.class);
        startActivity(intent);
    }

    public void viewLeaderboard(View view) {
        Intent intent = new Intent(TimeUpScreen.this, LeaderboardActivity.class);
        startActivity(intent);
    }

    public void playAgain(View view) {
        //returns the user to the game using the same players
        Intent intent;
        if (gameType.equals("Blitz")){
            intent = new Intent(TimeUpScreen.this, MainActivityB.class);
        } else{
            intent = new Intent(TimeUpScreen.this, MainActivity.class);
        }
        //gets the names of the players and sends them back to the tic tac toe board
        intent.putExtra("playerOneName", playerOneName);
        intent.putExtra("playerTwoName", playerTwoName);
        startActivity(intent);
    }

}
