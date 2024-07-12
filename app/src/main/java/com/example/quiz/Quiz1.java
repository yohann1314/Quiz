package com.example.quiz;

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

    private Button[] answerButtons = new Button[4];
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
            new Question("Question 2", new String[]{"Netflix", "Apple", "Adidas", "PornHub"}, 1),
            new Question("Question 3", new String[]{"Netflix", "Apple", "Adidas", "PornHub"}, 2),
            new Question("Question 4", new String[]{"Netflix", "Apple", "Adidas", "PornHub"}, 2),
            new Question("Question 5", new String[]{"Netflix", "Apple", "Adidas", "lacoste"}, 4)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz1);

        logoImageView = findViewById(R.id.logoImageView);
        questionTextView = findViewById(R.id.questionTextView);
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
                answerButton.setBackgroundColor(Color.GREEN); // Set correct answer button color to green
            } else if (answerButton.getText().toString().equals(selectedAnswer)) {
                answerButton.setBackgroundColor(Color.RED); // Set selected incorrect answer button color to red
            }
        }

        // Delay before loading next question
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadNextQuestion();
            }
        }, 2000); // 2000 milliseconds = 2 seconds delay
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