package com.example.l4_q1;

//import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

//import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private RadioGroup radioGroupQuestion;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroupQuestion = findViewById(R.id.radioGroupQuestion);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(v -> showConfirmationDialog());
    }

    private void showConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Submit Quiz")
                .setMessage("Are you sure you want to submit the quiz?")
                .setPositiveButton("Yes", (dialog, which) -> submitQuiz())
                .setNegativeButton("No", null)
                .show();
    }

    private void submitQuiz() {
        int selectedId = radioGroupQuestion.getCheckedRadioButtonId();
        if (selectedId == -1) return;

        RadioButton selectedRadioButton = findViewById(selectedId);
        String selectedAnswer = selectedRadioButton.getText().toString();

        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        intent.putExtra("answer", selectedAnswer);
        startActivity(intent);
    }
}
