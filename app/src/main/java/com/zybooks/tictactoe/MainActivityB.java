package com.zybooks.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//This is the Blitz version
public class MainActivityB extends AppCompatActivity implements View.OnClickListener{
    private Button[][] buttons = new Button[3][3];
    private boolean playerTurn = true;
    private int movesCount = 0;
    private char[][] board = new char[3][3];
    private TextView playerTurnText;
    private TextView timerText;
    private String playerOneName ;
    private String playerTwoName ;
    private static String winnerName;
    // 8 seconds for timer
    private static final long TIMER_TIME_MILLIS = 8000;
    private Handler timerHandler;
    private long millisRemaining;
    private boolean timesUp;
    private boolean gameWon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blitz_game);

        //gets the names of the players
        Intent intent = getIntent();
        playerOneName = intent.getStringExtra("playerOneName");
        playerTwoName = intent.getStringExtra("playerTwoName");

        playerTurnText = findViewById(R.id.player_turn_text);
        timerText = findViewById(R.id.timer_text);

        timerHandler = new Handler();
        millisRemaining = TIMER_TIME_MILLIS;

        updatePlayerTurnText();
        initializeBoard();
        initializeButtons();

        //starts the timer
        timerHandler.post(timerRunnable);
    }

    //runnable for the timer
    private final Runnable timerRunnable = new Runnable(){
        @Override
        public void run() {
            if (!timesUp){
                //the timer is not up
                //decreases the timer by 10 milliseconds
                millisRemaining -= 10;
                //changes the text on the UI
                updateTimerText();
                if (millisRemaining > 0){
                    //there is still time left
                    timerHandler.postDelayed(this, 10);
                } else {
                    //time ran out
                    timesUp = true;
                    //the times up screen is shown instead of the one that displays a winner
                    Intent intent = new Intent(MainActivityB.this, TimeUpScreen.class);
                    startActivity(intent);
                }
            }
        }
    };

    private void updateTimerText(){
        long seconds = millisRemaining / 1000;
        long milliseconds = millisRemaining % 1000;
        //gets the new time in the XX:XX format
        String timerString = String.format("%02d:%02d", seconds, milliseconds/10);
        //applies the new time to the TextView
        timerText.setText(timerString);
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
                gameWon = true;
                //stop timer
                timerHandler.removeCallbacks(timerRunnable);

                //determine winner
                String winner = playerTurn ? playerOneName : playerTwoName;

                //make a new intent to the win screen and save the winner, players, and game type
                Intent intent = new Intent(MainActivityB.this, WinScreen.class);
                intent.putExtra("winner", winner);
                intent.putExtra("game_type", "Blitz");
                intent.putExtra("playerOneName", playerOneName);
                intent.putExtra("playerTwoName", playerTwoName);

                //goes to the win screen
                startActivity(intent);

                //we can take this out if need be
                //the toast can be seen momentarily even after going to win screen
                displayResult(winner + " wins!");
                disableButtons();
            } else if (movesCount == 9) {
                //maximum moves reached
                displayResult("It's a draw!");
                //stops the clock
                timerHandler.removeCallbacks(timerRunnable);
            } else {
                //updates the turn
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

        //stops the previous timer and starts the new one
        timerHandler.removeCallbacks(timerRunnable);
        millisRemaining = TIMER_TIME_MILLIS;
        updateTimerText();
        timerHandler.post(timerRunnable);
    }


}
