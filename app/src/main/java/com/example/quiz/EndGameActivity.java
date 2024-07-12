package com.example.quiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class EndGameActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "QuizPrefs";
    private static final String KEY_PLAYER_NAME = "PlayerName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        TextView scoreTextView = findViewById(R.id.scoreTextView);
        TextView topScoresTextView = findViewById(R.id.topScoresTextView);
        Button restartButton = findViewById(R.id.restartButton);
        Button homeButton = findViewById(R.id.homeButton);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String playerName = prefs.getString(KEY_PLAYER_NAME, "Unknown Player");
        int finalScore = getIntent().getIntExtra("finalScore", 0);

        // Enregistrement du score actuel
        saveCurrentScore(playerName, finalScore);

        // Affichage du score final
        scoreTextView.setText("Final Score: " + finalScore);

        // Affichage des meilleurs scores
        displayTopScores(topScoresTextView);

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndGameActivity.this, Quiz1.class);
                startActivity(intent);
                finish();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndGameActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void saveCurrentScore(String playerName, int finalScore) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        int totalScores = prefs.getInt("totalScores", 0);

        editor.putString("player_" + totalScores, playerName);
        editor.putInt("score_" + totalScores, finalScore);
        editor.putInt("totalScores", totalScores + 1);
        editor.apply();
    }

    private void displayTopScores(TextView topScoresTextView) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int totalScores = prefs.getInt("totalScores", 0);
        ArrayList<PlayerScore> scoresList = new ArrayList<>();

        for (int i = 0; i < totalScores; i++) {
            String playerName = prefs.getString("player_" + i, null);
            int score = prefs.getInt("score_" + i, 0);
            if (playerName != null) {
                scoresList.add(new PlayerScore(playerName, score));
            }
        }

        Collections.sort(scoresList, new Comparator<PlayerScore>() {
            @Override
            public int compare(PlayerScore o1, PlayerScore o2) {
                return Integer.compare(o2.getScore(), o1.getScore());
            }
        });

        StringBuilder topScores = new StringBuilder();
        if (!scoresList.isEmpty()) {
            PlayerScore bestPlayerScore = scoresList.get(0);
            topScores.append("Meilleur Score: ").append(bestPlayerScore.getPlayerName()).append(": ").append(bestPlayerScore.getScore()).append("\n\n");
        }

        for (int i = 1; i < Math.min(10, scoresList.size()); i++) {
            PlayerScore playerScore = scoresList.get(i);
            topScores.append(playerScore.getPlayerName()).append(": ").append(playerScore.getScore()).append("\n");
        }

        topScoresTextView.setText(topScores.toString());
    }

    // Classe PlayerScore ajoutée en tant que classe interne
    private class PlayerScore {
        private String playerName;
        private int score;

        public PlayerScore(String playerName, int score) {
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
}
