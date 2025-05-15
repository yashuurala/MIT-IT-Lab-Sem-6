package com.example.l2q3ex;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;


public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Switch switchMode;
    private View parentLayout;

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
        textView = findViewById(R.id.textView);
        switchMode = findViewById(R.id.switch1);
        parentLayout = findViewById(R.id.main);

        setLightMode();

        switchMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) setDarkMode();
            else setLightMode();
        });
    }

    // Method to set Light Mode
    private void setLightMode() {
        parentLayout.setBackgroundColor(Color.WHITE);
        textView.setTextColor(Color.BLACK);
        textView.setText("Light Mode");
        switchMode.setTextColor(Color.BLACK);
        Toast.makeText(this, "Light Mode Activated", Toast.LENGTH_SHORT).show();
    }

    // Method to set Dark Mode
    private void setDarkMode() {
        parentLayout.setBackgroundColor(Color.BLACK);
        textView.setTextColor(Color.WHITE);
        textView.setText("Dark Mode");
        switchMode.setTextColor(Color.WHITE);
        Toast.makeText(this, "Dark Mode Activated", Toast.LENGTH_SHORT).show();
    }
}
