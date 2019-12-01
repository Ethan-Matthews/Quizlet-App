package com.example.quizletapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";

    private static final int READ_BLOCK_SIZE = 2000;

    protected Map<String, String> map;

    protected ArrayList<String> staticList;
    protected ArrayList<String> shuffledList;
    protected ArrayList<String> responseList;

    protected TextView usernameTV;
    protected TextView userScoreTV;
    protected TextView questionTV;
    protected TextView quizQuestionNumberTV;

    protected Button aBtn;
    protected Button bBtn;
    protected Button cBtn;
    protected Button dBtn;
    protected Button quizNextBtn;

    protected Button[] buttonList;
    protected String[] questionsArray;

    protected int questionNumber = 1;
    protected int userScore = 0;

    protected String currentQuestionKey;
    protected String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        // Initialize objects.
        Initialization();
        // Get passed intent.
        GetIntent();
        // Create HashMap.
        PopulateHashMap(Objects.requireNonNull(questionsArray), map);
        // Populate lists on the first Quiz Activity.
        if (staticList.isEmpty()) {
            PopulateLists(staticList, shuffledList);
        }
        // Set up questions, return this quiz pages correct answer.
        currentQuestionKey = SetUpQuestion(buttonList);
        // Add action listener to buttons.
        SetActionListener();
    }

    // Creates action listener and assigns it to all buttons.
    private void SetActionListener() {
        View.OnClickListener actionListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int buttonID = v.getId();
                switch (buttonID) {
                    case R.id.quizABtn:
                        if (aBtn.getText().toString().equals(currentQuestionKey)) { // If the response is right.
                            userScore += 1; // Update score.
                            userScoreTV.setText(String.format("Score: %s/10", userScore)); // Sets new score to text.
                            responseList.add(String.format("Question %s: Correct\n", questionNumber)); // Adds the user response to the the response list.
                            CustomToastMessage("Correct!"); // Shows toast message to user.

                        } else { // Else the response is wrong.
                            responseList.add(String.format("Question %s: Incorrect", questionNumber)); // Adds the user response to the the response list.
                            responseList.add(String.format("      Answer: %s\n", currentQuestionKey)); // Adds correct answer to the the response list.
                            CustomToastMessage("Incorrect"); // Shows toast message to user.
                        }
                        disableButtons(); // Disable buttons.
                        break;
                    case R.id.quizBBtn:
                        if (bBtn.getText().toString().equals(currentQuestionKey)) {
                            userScore += 1;
                            userScoreTV.setText(String.format("Score: %s/10", userScore));
                            responseList.add(String.format("Question %s: Correct\n", questionNumber));
                            CustomToastMessage("Correct!");
                        } else {
                            responseList.add(String.format("Question %s: Incorrect", questionNumber));
                            responseList.add(String.format("      Answer: %s\n", currentQuestionKey));
                            CustomToastMessage("Incorrect");
                        }
                        disableButtons();
                        break;
                    case R.id.quizCBtn:
                        if (cBtn.getText().toString().equals(currentQuestionKey)) {
                            userScore += 1;
                            userScoreTV.setText(String.format("Score: %s/10", userScore));
                            responseList.add(String.format("Question %s: Correct\n", questionNumber));
                            CustomToastMessage("Correct!");

                        } else {
                            responseList.add(String.format("Question %s: Incorrect", questionNumber));
                            responseList.add(String.format("      Answer: %s\n", currentQuestionKey));
                            CustomToastMessage("Incorrect");
                        }
                        disableButtons();
                        break;
                    case R.id.quizDBtn:
                        if (dBtn.getText().toString().equals(currentQuestionKey)) {
                            userScore += 1;
                            userScoreTV.setText(String.format("Score: %s/10", userScore));
                            responseList.add(String.format("Question %s: Correct\n", questionNumber));
                            CustomToastMessage("Correct!");
                        } else {
                            responseList.add(String.format("Question %s: Incorrect", questionNumber));
                            responseList.add(String.format("      Answer: %s\n", currentQuestionKey));
                            CustomToastMessage("Incorrect");
                        }
                        disableButtons();
                        break;
                    case R.id.quizNextBtn:
                        questionNumber += 1;
                        if (questionNumber > 10) { // If the quiz is complete create and pass intent bundle to the Results activity.
                            Intent intent = new Intent(QuizActivity.this, ResultsActivity.class);
                            intent.putExtra("classID", "QuizActivity");
                            intent.putExtra("userName", usernameTV.getText().toString());
                            intent.putExtra("userScore", userScore);
                            intent.putStringArrayListExtra("responseList", responseList);
                            startActivity(intent);
                            finish();
                        } else { // Else the quiz is not complete, creates and passes intent bundle to the next instance of the Quiz Activity.
                            Intent intent = new Intent(QuizActivity.this, QuizActivity.class);
                            intent.putExtra("classID", "QuizActivity");
                            intent.putExtra("userName", usernameTV.getText().toString());
                            intent.putExtra("userScore", userScore);
                            intent.putStringArrayListExtra("staticList", staticList);
                            intent.putStringArrayListExtra("shuffledList", shuffledList);
                            intent.putStringArrayListExtra("responseList", responseList);
                            intent.putExtra("questionCount", questionNumber);
                            startActivity(intent);
                            finish();
                        }
                        break;
                }
            }
        };

        for (Button button : buttonList) {
            button.setOnClickListener(actionListener);
        }
        quizNextBtn.setOnClickListener(actionListener);
    }

    // Initializes objects and sets page title.
    private void Initialization() {
        setTitle("Quizlet Application - Quiz");
        map = new HashMap<>();

        staticList = new ArrayList<>();
        shuffledList = new ArrayList<>();
        responseList = new ArrayList<>();

        usernameTV = findViewById(R.id.quizNameTV);
        userScoreTV = findViewById(R.id.scoreTV);
        questionTV = findViewById(R.id.quizQuestionTV);
        quizQuestionNumberTV = findViewById(R.id.quizQuestionNumberTV);

        aBtn = findViewById(R.id.quizABtn);
        bBtn = findViewById(R.id.quizBBtn);
        cBtn = findViewById(R.id.quizCBtn);
        dBtn = findViewById(R.id.quizDBtn);

        quizNextBtn = findViewById(R.id.quizNextBtn);
        quizNextBtn.setEnabled(false);

        buttonList = new Button[] {aBtn, bBtn, cBtn, dBtn};
        questionsArray = ImportQuestions();

        currentQuestionKey = "";
    }

    // Gets Intent bundles from either the main activity or the last instance of the quiz
    // activity depending on the class ID in the bundle.
    private void GetIntent() {
        Intent intent = getIntent();
        if (Objects.equals(intent.getStringExtra("classID"), "MainActivity")) {
            username = intent.getStringExtra("userName");
            usernameTV.setText(username);
            userScoreTV.setText(String.format("Score: %s/10", userScore));
        }

        if (Objects.equals(intent.getStringExtra("classID"), "QuizActivity")) {
            username = intent.getStringExtra("userName");
            usernameTV.setText(username);
            userScore = intent.getIntExtra("userScore", 0);
            userScoreTV.setText(String.format("Score: %s/10", userScore));
            shuffledList = intent.getStringArrayListExtra("shuffledList");
            staticList = intent.getStringArrayListExtra("staticList");
            responseList = intent.getStringArrayListExtra("responseList");
            questionNumber = intent.getIntExtra("questionCount", 0);
        }
        quizQuestionNumberTV.setText(String.format("Question %s", questionNumber));
    }

    // Sets up questions and answers.
    private String SetUpQuestion(Button[] buttonList) {
        int questionIndex = new Random().nextInt(staticList.size()); // Random staticList Number
        int buttonIndex = new Random().nextInt(buttonList.length); // Random buttonList number

        questionTV.setText(staticList.get(questionIndex)); // Sets text to random question.
        staticList.remove(questionIndex); // Removes question from list

        String questionKey = "";
        for (Map.Entry<String, String> entry : map.entrySet()) { // For each entry
            if (entry.getValue().equals(questionTV.getText().toString())) { // If the entry value is equal to question text
                questionKey = entry.getKey(); // This is the question key.
                buttonList[buttonIndex].setText(questionKey);  // Assigns question key to random button.
            }
        }
        for (Button button : buttonList) { // For each button.
            while (button.getText().toString().isEmpty()) { // While the button is empty.
                String falseAnswer = shuffledList.get(new Random().nextInt(shuffledList.size())); // Gets random answer for the shuffled list.
                if (!button.getText().toString().contains(questionKey)) { // If the current button DOES NOT contain the answer to the current question.
                    boolean doesContain = false; // Initialize boolean.
                    for (Button button1 : buttonList) { // For each button again.
                        if (button1.getText().toString().contains(falseAnswer)) { // If the current nested button DOES contain the false answer.
                            doesContain = true;
                        }
                    }
                    if (!doesContain) { // If the current nested button DOES NOT contain the false answer
                        button.setText(falseAnswer); //
                    }
                }
            }
        }
        return questionKey;
    }

    // Disables answer buttons and enables next button.
    private void disableButtons() {
        aBtn.setEnabled(false);
        bBtn.setEnabled(false);
        cBtn.setEnabled(false);
        dBtn.setEnabled(false);
        quizNextBtn.setEnabled(true);
    }

    // Loops though the hash map and adds value/key to respective lists.
    private void PopulateLists(ArrayList<String> staticList, ArrayList<String> shuffledList) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            staticList.add(entry.getValue());
            shuffledList.add(entry.getKey());
        }
    }

    // Loops though the question array 2 at a time. Question is followed by answer every two index places.
    private void PopulateHashMap(String[] questionsArray, Map<String, String> map) {
        for (int i = 0; i < questionsArray.length; i += 2) {
            map.put(questionsArray[i + 1], questionsArray[i]);
        }
    }

    // Reads File from Assets folder. Catches and logs any errors reading the file. Returns a string array with parsed questions/answers or returns null.
    private String[] ImportQuestions() {
        try {
            InputStreamReader reader = new InputStreamReader(getAssets().open("QuizQuestions.txt"));

            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            int charRead;
            String readString = "";

            while ((charRead = reader.read(inputBuffer)) > 0) {
                readString = String.copyValueOf(inputBuffer, 0, charRead);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(readString);
                inputBuffer = new char[READ_BLOCK_SIZE];
            }
            return readString.split("\n");

        } catch(IOException ioe) {
            CustomToastMessage("Error Loading File.");
            Log.e(TAG, "Error Loading File.");
            ioe.printStackTrace();
            return null;
        }
    }

    public void CustomToastMessage(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.layout_toast_custom, (ViewGroup)findViewById(R.id.custom_toast_container));

        TextView text = layout.findViewById(R.id.text);
        text.setTextSize(16);
        text.setPadding(4, 4, 4, 4);
        text.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}