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
import androidx.navigation.Navigation;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private boolean playerTurn = true;
    private int movesCount = 0;
    private char[][] board = new char[3][3];
    private TextView playerTurnText;

    private String playerOneName ;
    private String playerTwoName ;
    private static String winnerName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //gets the players names
        Intent intent = getIntent();
        playerOneName = intent.getStringExtra("playerOneName");
        playerTwoName = intent.getStringExtra("playerTwoName");

        playerTurnText = findViewById(R.id.player_turn_text);
        updatePlayerTurnText();
        initializeBoard();
        initializeButtons();


    }

    public void viewRecords(View view) {
        // Navigate to the leaderboard using the action defined in the navigation graph
        Navigation.findNavController(view).navigate(R.id.leaderboardFragment);
    }


    private void updatePlayerTurnText() {
        if (playerTurn) {
            playerTurnText.setText(playerOneName + "'s Turn");
        } else {
            playerTurnText.setText(playerTwoName + "'s Turn");
        }
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = 'E';
            }
        }
    }

    private void initializeButtons() {
        buttons[0][0] = findViewById(R.id.button_00);
        buttons[0][1] = findViewById(R.id.button_01);
        buttons[0][2] = findViewById(R.id.button_02);
        buttons[1][0] = findViewById(R.id.button_10);
        buttons[1][1] = findViewById(R.id.button_11);
        buttons[1][2] = findViewById(R.id.button_12);
        buttons[2][0] = findViewById(R.id.button_20);
        buttons[2][1] = findViewById(R.id.button_21);
        buttons[2][2] = findViewById(R.id.button_22);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setOnClickListener(this);
                buttons[i][j].setTag("" + i + j);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        int row = Integer.parseInt(button.getTag().toString().charAt(0) + "");
        int col = Integer.parseInt(button.getTag().toString().charAt(1) + "");

        if (board[row][col] == 'E') {
            if (playerTurn) {
                button.setText("X");
                board[row][col] = 'X';
            } else {
                button.setText("O");
                board[row][col] = 'O';
            }
            movesCount++;

            if (checkWin(row, col)) {
                //a player has won, go to win screen
                String winner = playerTurn ? playerOneName : playerTwoName;
                //makes a new intent for the win screen and saves winner, game type, and players
                Intent intent = new Intent(MainActivity.this, WinScreen.class);
                intent.putExtra("winner", winner);
                intent.putExtra("game_type", "regular");
                intent.putExtra("playerOneName", playerOneName);
                intent.putExtra("playerTwoName", playerTwoName);

                //goes to win screen
                startActivity(intent);

                //we can take this out if need be
                displayResult(winner + " wins!");
                disableButtons();
            } else if (movesCount == 9) {
                displayResult("It's a draw!");
            } else {
                playerTurn = !playerTurn;
                updatePlayerTurnText();
            }
        }
    }

    private boolean checkWin(int row, int col) {
        char currentPlayer = playerTurn ? 'X' : 'O';

        // Check row
        if (board[row][0] == currentPlayer && board[row][1] == currentPlayer && board[row][2] == currentPlayer) {
            return true;
        }

        // Check column
        if (board[0][col] == currentPlayer && board[1][col] == currentPlayer && board[2][col] == currentPlayer) {
            return true;
        }

        // Check diagonals
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) {
            return true;
        }
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) {
            return true;
        }

        return false;
    }

    private void displayResult(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void disableButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    public void resetGame(View view) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                board[i][j] = 'E';
                buttons[i][j].setEnabled(true);
            }
        }
        playerTurn = true;
        movesCount = 0;
        updatePlayerTurnText();
    }

}