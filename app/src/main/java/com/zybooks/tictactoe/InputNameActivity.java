package com.zybooks.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

/* *********************** activity_input_name.xml **************************
=> get input for a player's name
*/
public class InputNameActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "com.zybooks.tictactoe.NAME";
    private EditText playerNameEditText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_name);

        //Get input from user and save it
        playerNameEditText = findViewById(R.id.name_input);
        submitButton = findViewById(R.id.submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = playerNameEditText.getText().toString();
                Intent resultIntent = new Intent();
                resultIntent.putExtra(EXTRA_NAME, name);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
