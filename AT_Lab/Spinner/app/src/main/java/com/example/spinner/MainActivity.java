package com.example.spinner;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.AdapterView;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Spinner spinnerFruits;
    private Button buttonShowSelected;
    private String selectedFruit; // Store selected item

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
        // Initialize Spinner and Button
        spinnerFruits = findViewById(R.id.spinnerFruits);
        buttonShowSelected = findViewById(R.id.buttonShowSelected);

        // Fruit options for Spinner
        String[] fruits = {"Apple", "Banana", "Mango", "Orange", "Grapes", "Pineapple", "Watermelon"};

        // Create an ArrayAdapter to link data with UI
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, fruits);
        spinnerFruits.setAdapter(adapter);

        // Handle item selection in Spinner
        spinnerFruits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedFruit = fruits[position]; // Get selected fruit
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedFruit = fruits[0]; // Default selection if nothing is selected
            }
        });

        // Button Click to Show Selected Fruit
        buttonShowSelected.setOnClickListener(v ->
                Toast.makeText(MainActivity.this, "You selected: " + selectedFruit, Toast.LENGTH_SHORT).show()
        );
    }
}

