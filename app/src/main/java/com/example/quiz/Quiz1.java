package com.example.quiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Quiz1 extends AppCompatActivity {

    private ImageView logoImageView;
    private int score = 0;
    private Button[] answerButtons = new Button[4];
    private TextView scoreTextView;
    private TextView progressTextView;
    private TextView questionTextView;
    private int currentQuestionIndex = 0;
    private int totalQuestions = 5;

    private int[] logoImages = {
            R.drawable.netflix,
            R.drawable.apple,
            R.drawable.adidas,
            R.drawable.youtube,
            R.drawable.lacoste
    };

    private Question[] questions = {
            new Question("Question 1", new String[]{"Netflix", "Apple", "Adidas", "Youtube"}, 0),
            new Question("Question 2", new String[]{"Netflix", "Apple", "Adidas", "Youtube"}, 1),
            new Question("Question 3", new String[]{"Netflix", "Apple", "Adidas", "Youtube"}, 2),
            new Question("Question 4", new String[]{"Netflix", "Apple", "Adidas", "Youtube"}, 3),
            new Question("Question 5", new String[]{"Netflix", "Apple", "Adidas", "Lacoste"}, 3)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz1);

        logoImageView = findViewById(R.id.logoImageView);
        questionTextView = findViewById(R.id.questionTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        answerButtons[0] = findViewById(R.id.answerButton1);
        answerButtons[1] = findViewById(R.id.answerButton2);
        answerButtons[2] = findViewById(R.id.answerButton3);
        answerButtons[3] = findViewById(R.id.answerButton4);
        progressTextView = findViewById(R.id.progressTextView);

        for (Button answerButton : answerButtons) {
            answerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(((Button) v).getText().toString());
                }
            });
        }

        loadNextQuestion();
    }

    private void checkAnswer(String selectedAnswer) {
        Question currentQuestion = questions[currentQuestionIndex - 1];
        String correctAnswer = currentQuestion.getAnswers()[currentQuestion.getCorrectAnswerIndex()];
        for (Button answerButton : answerButtons) {
            if (answerButton.getText().toString().equals(correctAnswer)) {
                answerButton.setBackgroundColor(Color.GREEN);
                if (selectedAnswer.equals(correctAnswer)) {
                    score++;
                    scoreTextView.setText("Score: " + score);
                }
            } else if (answerButton.getText().toString().equals(selectedAnswer)) {
                answerButton.setBackgroundColor(Color.RED);
            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadNextQuestion();
            }
        }, 1000);
    }

    private void loadNextQuestion() {
        if (currentQuestionIndex >= totalQuestions) {
            saveScoreAndEndGame();
        } else {
            logoImageView.setImageResource(logoImages[currentQuestionIndex]);
            Question question = questions[currentQuestionIndex];
            questionTextView.setText(question.getQuestion());
            String[] answers = question.getAnswers();
            for (int i = 0; i < answerButtons.length; i++) {
                answerButtons[i].setText(answers[i]);
                answerButtons[i].setTextColor(Color.BLACK);
                answerButtons[i].setBackgroundColor(Color.LTGRAY);
            }

            progressTextView.setText("Question " + (currentQuestionIndex + 1) + "/" + totalQuestions);
            currentQuestionIndex++;
        }
    }

    private void saveScoreAndEndGame() {
        SharedPreferences prefs = getSharedPreferences("QuizPrefs", Context.MODE_PRIVATE);
        String playerName = prefs.getString("PlayerName", "Unknown");

        SharedPreferences.Editor editor = prefs.edit();
        int totalScores = prefs.getInt("totalScores", 0);

        editor.putString("player_" + totalScores, playerName);
        editor.putInt("score_" + totalScores, score);
        editor.putInt("totalScores", totalScores + 1);
        editor.apply();

        Intent intent = new Intent(Quiz1.this, EndGameActivity.class);
        intent.putExtra("finalScore", score);
        startActivity(intent);
        finish();
    }
}



class Question {
    private String question;
    private String[] answers;
    private int correctAnswerIndex;

    public Question(String question, String[] answers, int correctAnswerIndex) {
        this.question = question;
        this.answers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}