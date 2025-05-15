package com.example.cal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editTextDisplay;
    private double firstNumber = 0;
    private double secondNumber = 0;
    private String currentOperator = "";
    private boolean isNewOperation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextDisplay = findViewById(R.id.editTextDisplay);

        int[] numberButtons = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9};

        int[] operatorButtons = {R.id.buttonAdd, R.id.buttonSubtract, R.id.buttonMultiply, R.id.buttonDivide};

        // Number Buttons Click Listener
        for (int id : numberButtons) {
            findViewById(id).setOnClickListener(view -> {
                Button button = (Button) view;
                if (isNewOperation) {
                    editTextDisplay.setText(button.getText());
                    isNewOperation = false;
                } else {
                    editTextDisplay.append(button.getText());
                }
            });
        }

        // Operator Buttons Click Listener
        for (int id : operatorButtons) {
            findViewById(id).setOnClickListener(view -> {
                Button button = (Button) view;
                firstNumber = Double.parseDouble(editTextDisplay.getText().toString());
                currentOperator = button.getText().toString();
                isNewOperation = true;
            });
        }

        // Equal Button Click Listener
        findViewById(R.id.buttonEqual).setOnClickListener(view -> {
            secondNumber = Double.parseDouble(editTextDisplay.getText().toString());
            double result = calculate(firstNumber, secondNumber, currentOperator);
            editTextDisplay.setText(String.valueOf(result));
            isNewOperation = true;
        });

        // Clear Button Click Listener
        findViewById(R.id.buttonClear).setOnClickListener(view -> {
            editTextDisplay.setText("");
            firstNumber = 0;
            secondNumber = 0;
            currentOperator = "";
            isNewOperation = true;
        });
    }

    private double calculate(double num1, double num2, String operator) {
        switch (operator) {
            case "+": return num1 + num2;
            case "-": return num1 - num2;
            case "*": return num1 * num2;
            case "/": return num2 != 0 ? num1 / num2 : 0; // Prevent division by zero
            default: return num2;
        }
    }
}

