package com.example.l4_q1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    private TextView textViewResult;
    private Button buttonRestart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textViewResult = findViewById(R.id.textViewResult);
        buttonRestart = findViewById(R.id.buttonRestart);

        String userAnswer = getIntent().getStringExtra("answer");
        textViewResult.setText("Your Answer: " + userAnswer);

        buttonRestart.setOnClickListener(v -> restartQuiz());
    }

    private void restartQuiz() {
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
