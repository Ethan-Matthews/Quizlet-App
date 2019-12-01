package com.example.quizletapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class ResultsActivity extends AppCompatActivity {

    protected ArrayList<String> scoreList;

    protected TextView usernameTV;
    protected TextView userScoreTV;
    protected TextView questionTV;
    protected TextView questionNumberTV;

    protected Button resultsRestart;
    protected Button resultsEnd;

    protected int userScore = 0;

    protected String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        // Initializes objects.
        Initialization();
        // Sets up action listener.
        SetUpActionListener();
    }

    // Initializes objects.
    private void Initialization() {
        setTitle("Quizlet Application - Results");

        usernameTV = findViewById(R.id.resultNameTV);
        userScoreTV = findViewById(R.id.resultScoreTV);
        questionTV = findViewById(R.id.resultQuestionTV);
        questionTV.setMovementMethod(new ScrollingMovementMethod());
        questionNumberTV = findViewById(R.id.resultQuestionNumberTV);

        resultsRestart = findViewById(R.id.resultRestartBtn);
        resultsEnd = findViewById(R.id.resultsEndBtn);

        scoreList = new ArrayList<>();

        Intent intent = getIntent();
        username = intent.getStringExtra("userName");
        usernameTV.setText(username);
        userScore = intent.getIntExtra("userScore", 0);
        userScoreTV.setText(String.format("Score: %s/10", userScore));
        scoreList = intent.getStringArrayListExtra("responseList");

        for (String score : Objects.requireNonNull(scoreList)) {
            questionTV.append(score + "\n");
        }
    }

    // Sets up action listener and assigns it to the quit and restart buttons.
    private void SetUpActionListener() {
        View.OnClickListener actionListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int buttonID = v.getId();
                switch (buttonID) {
                    case R.id.resultRestartBtn: // Passes a blank intent to the Main Activity.
                        Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.resultsEndBtn: // Calls home screen via intent flags.
                        Intent intent1 = new Intent(Intent.ACTION_MAIN);
                        intent1.addCategory(Intent.CATEGORY_HOME);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                        finish();
                        break;

                }
            }
        };
        resultsRestart.setOnClickListener(actionListener);
        resultsEnd.setOnClickListener(actionListener);
    }
}