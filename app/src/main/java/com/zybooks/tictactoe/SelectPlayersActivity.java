package com.zybooks.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class SelectPlayersActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PLAYER_ONE = 1;
    private static final int REQUEST_CODE_PLAYER_TWO = 2;

    private TextView playerOneName;
    private TextView playerTwoName;
    private Button selectPlayerOneButton;
    private Button selectPlayerTwoButton;
    private Button startGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_players);

        playerOneName = findViewById(R.id.player_one_name);
        playerTwoName = findViewById(R.id.player_two_name);
        selectPlayerOneButton = findViewById(R.id.select_player_one_button);
        selectPlayerTwoButton = findViewById(R.id.select_player_two_button);
        startGameButton = findViewById(R.id.start_game_button);

        selectPlayerOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectPlayersActivity.this, InputNameActivity.class);
                startActivityForResult(intent, REQUEST_CODE_PLAYER_ONE);
            }
        });

        selectPlayerTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectPlayersActivity.this, InputNameActivity.class);
                startActivityForResult(intent, REQUEST_CODE_PLAYER_TWO);
            }
        });


        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get player names
                String playerOne = playerOneName.getText().toString();
                String playerTwo = playerTwoName.getText().toString();

                if(!playerOne.isEmpty() && !playerTwo.isEmpty()) {
                    addNewPlayer(playerOne, SelectPlayersActivity.this);
                    addNewPlayer(playerTwo, SelectPlayersActivity.this);
                    Intent tempIntent = getIntent();
                    Intent intent;
                    String gameType = tempIntent.getStringExtra("game_type");
                    if(Objects.equals(gameType, "Blitz")){
                        intent = new Intent(SelectPlayersActivity.this, MainActivityB.class);
                    } else {
                        intent = new Intent(SelectPlayersActivity.this, MainActivity.class);
                    }
                    intent.putExtra("playerOneName", playerOne);
                    intent.putExtra("playerTwoName", playerTwo);

                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.startGameError, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void addNewPlayer(String playerName, Activity activity) {
        LeaderboardRepository leaderboardRepository = LeaderboardRepository.getInstance(activity);
        leaderboardRepository.addNewPlayer(playerName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            String name = data.getStringExtra(InputNameActivity.EXTRA_NAME);
            if (requestCode == REQUEST_CODE_PLAYER_ONE) {
                playerOneName.setText(name);
            } else if (requestCode == REQUEST_CODE_PLAYER_TWO) {
                playerTwoName.setText(name);
            }
        }
    }
}

