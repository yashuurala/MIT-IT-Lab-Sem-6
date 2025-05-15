package com.example.l4q1ex;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Objects;


public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String firstAns = getIntent().getStringExtra("1");
        String secondAns = getIntent().getStringExtra("2");
        String thirdAns = getIntent().getStringExtra("3");

        Log.d("MainActivity", firstAns + '\n' + secondAns + '\n' + thirdAns);

        int score = 0;
        if (firstAns != null && firstAns.equals("New Delhi")) score++;
        if (secondAns != null && secondAns.equals("NaCl")) score++;
        if (thirdAns != null && thirdAns.equals("19")) score++;

        // Displaying the score
        TextView scoreText = findViewById(R.id.textView3);
        scoreText.setText("Score: " + score + "/3!");

        // Button to go back to MainActivity
        Button submit = findViewById(R.id.button);
        submit.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(intent);
        });
    }
}