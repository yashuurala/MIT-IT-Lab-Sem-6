package com.example.timepicker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private Button btnSelectTime;
    private TextView tvSelectedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        timePicker = findViewById(R.id.timePicker);
        btnSelectTime = findViewById(R.id.btnSelectTime);
        tvSelectedTime = findViewById(R.id.tvSelectedTime);


        // Set button click listener
        btnSelectTime.setOnClickListener(v -> {
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();

            String selectedTime = "Selected Time: " + String.format("%02d:%02d", hour, minute);
            tvSelectedTime.setText(selectedTime);
        });
    }
}
