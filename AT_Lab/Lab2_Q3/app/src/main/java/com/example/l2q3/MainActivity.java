package com.example.l2q3;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private View mainLayout;
    private RadioGroup radioGroupColors;
    private Button buttonApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.mainLayout);
        radioGroupColors = findViewById(R.id.radioGroupColors);
        buttonApply = findViewById(R.id.buttonApply);

        buttonApply.setOnClickListener(v -> changeBackgroundColor());
    }

    private void changeBackgroundColor() {
        int selectedId = radioGroupColors.getCheckedRadioButtonId();
        if (selectedId == -1) return;

        RadioButton selectedRadioButton = findViewById(selectedId);
        String color = selectedRadioButton.getText().toString();

        switch (color) {
            case "Red":
                mainLayout.setBackgroundColor(Color.RED);
                break;
            case "Blue":
                mainLayout.setBackgroundColor(Color.BLUE);
                break;
            case "Green":
                mainLayout.setBackgroundColor(Color.GREEN);
                break;
            case "Yellow":
                mainLayout.setBackgroundColor(Color.YELLOW);
                break;
            default:
                mainLayout.setBackgroundColor(Color.WHITE);
                break;
        }
    }
}