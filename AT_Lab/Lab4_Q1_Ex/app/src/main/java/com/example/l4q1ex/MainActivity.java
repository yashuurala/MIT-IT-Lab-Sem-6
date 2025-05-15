package com.example.l4q1ex;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button submit = findViewById(R.id.button);
        submit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                        RadioGroup first = findViewById(R.id.radioGroup);
                        int selectedId1 = first.getCheckedRadioButtonId();
                        if (selectedId1 == -1) {
                            Toast.makeText(MainActivity.this, "Please select an answer for Question 1!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        RadioButton selectedButton1 = findViewById(selectedId1);
                        String firstAns = selectedButton1.getText().toString();
                        intent.putExtra("1", firstAns);

                        RadioGroup second = findViewById(R.id.radioGroup2);
                        int selectedId2 = second.getCheckedRadioButtonId();
                        if (selectedId2 == -1) {
                            Toast.makeText(MainActivity.this, "Please select an answer for Question 2!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        RadioButton selectedButton2 = findViewById(selectedId2);
                        String secondAns = selectedButton2.getText().toString();
                        intent.putExtra("2", secondAns);

                        RadioGroup third = findViewById(R.id.radioGroup3);
                        int selectedId3 = third.getCheckedRadioButtonId();
                        if (selectedId3 == -1) {
                            Toast.makeText(MainActivity.this, "Please select an answer for Question 3!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        RadioButton selectedButton3 = findViewById(selectedId3);
                        String thirdAns = selectedButton3.getText().toString();
                        intent.putExtra("3", thirdAns);

                        startActivity(intent);
                    }
                }
        );
    }
}