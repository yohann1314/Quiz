package com.example.quiz;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Quiz2 extends AppCompatActivity {

    private ImageView logoImageView;
    private int score = 0;
    private Button[] answerButtons = new Button[2];
    private TextView scoreTextView;
    private TextView progressTextView;
    private TextView questionTextView;
    private int currentQuestionIndex = 0;
    private int totalQuestions = 7;

    private int[] logoImages = {
            R.drawable.dominos,
            R.drawable.ikea,
            R.drawable.lg,
            R.drawable.master,
            R.drawable.nutella,
            R.drawable.paypal,
            R.drawable.pespi
    };

    private Question[] questions = {
            new Question("Question 1", new String[]{"A", "B"}, 0),
            new Question("Question 2", new String[]{"A", "B"}, 1),
            new Question("Question 3", new String[]{"A", "B"}, 1),
            new Question("Question 4", new String[]{"A", "B"}, 0),
            new Question("Question 5", new String[]{"A", "B"}, 1),
            new Question("Question 6", new String[]{"A", "B"}, 0),
            new Question("Question 7", new String[]{"A", "B"}, 1)
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);

        logoImageView = findViewById(R.id.logoImageView);
        questionTextView = findViewById(R.id.questionTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        answerButtons[0] = findViewById(R.id.answerButton1);
        answerButtons[1] = findViewById(R.id.answerButton2);
        progressTextView = findViewById(R.id.progressTextView);

        for (Button answerButton : answerButtons) {
            answerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(((Button) v).getText().toString());
                    // Removed loadNextQuestion() from here
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
        if (currentQuestionIndex < totalQuestions) {
            logoImageView.setImageResource(logoImages[currentQuestionIndex]);
            Question question = questions[currentQuestionIndex];
            questionTextView.setText(question.getQuestion());
            String[] answers = question.getAnswers();
            for (int i = 0; i < answerButtons.length; i++) {
                answerButtons[i].setText(answers[i]);
                answerButtons[i].setTextColor(Color.BLACK); // Reset text color
                answerButtons[i].setBackgroundColor(Color.LTGRAY); // Reset button background color to initial color
            }

            progressTextView.setText("Question " + (currentQuestionIndex + 1) + "/" + totalQuestions);
            currentQuestionIndex++;
        } else {
            // End the quiz
        }
    }
}

class Question2 {
    private String question;
    private String[] answers;
    private int correctAnswerIndex;

    public Question2(String question, String[] answers, int correctAnswerIndex) {
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