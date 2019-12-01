package com.example.quizletapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected Button nextBtn;
    protected EditText namePT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Quizlet Application - Start");
        // Initialize objects.
        Initialization();
        // Add action listener to buttons.
        SetUpActionListener();
    }

    private void Initialization() {
        nextBtn = findViewById(R.id.nextBtn);
        namePT = findViewById(R.id.namePT);
    }

    // Sets up action listener and assigns it to the next button.
    private void SetUpActionListener() {
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (namePT.getText() != null && !namePT.getText().toString().isEmpty()) { // If the name text view is not empty.
                    Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                    intent.putExtra("userName", namePT.getText().toString());
                    intent.putExtra("classID", "MainActivity");
                    startActivity(intent);
                    finish();
                }
                else { // Else it is empty.
                    CustomToastMessage("Please Enter a Name");
                }
            }
        });
    }

    // https://android--code.blogspot.com/2015/08/android-custom-toast-layoutstyleview.html
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
