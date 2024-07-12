package com.example.quiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "QuizPrefs";
    private static final String KEY_PLAYER_NAME = "PlayerName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText nameEditText = findViewById(R.id.nameEditText);
        Button startQuizButton = findViewById(R.id.startQuizButton);
        Button changeNameButton = findViewById(R.id.changeNameButton);
        TextView welcomeTextView = findViewById(R.id.welcomeTextView);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String playerName = prefs.getString(KEY_PLAYER_NAME, "");
        if (!playerName.isEmpty()) {
            welcomeTextView.setText("Bienvenue " + playerName);
            nameEditText.setVisibility(View.GONE);
        } else {
            nameEditText.setVisibility(View.VISIBLE);
        }

        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameEditText.getVisibility() == View.VISIBLE) {
                    String playerName = nameEditText.getText().toString();
                    prefs.edit().putString(KEY_PLAYER_NAME, playerName).apply();
                    welcomeTextView.setText("Bienvenue " + playerName);
                    nameEditText.setVisibility(View.GONE);
                }
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        changeNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                startActivity(intent);
            }
        });
    }
}