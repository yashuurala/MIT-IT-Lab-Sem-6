package com.example.datepicker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private Button btnSelectDate;
    private TextView tvSelectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        datePicker = findViewById(R.id.datePicker);
        btnSelectDate = findViewById(R.id.btnSelectDate);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);

        // Set button click listener
        btnSelectDate.setOnClickListener(v -> {
            // Get selected date from DatePicker
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1;  // Month starts from 0
            int year = datePicker.getYear();

            // Display selected date in TextView
            String selectedDate = "Selected Date: " + day + "/" + month + "/" + year;
            tvSelectedDate.setText(selectedDate);
        });
    }
}
