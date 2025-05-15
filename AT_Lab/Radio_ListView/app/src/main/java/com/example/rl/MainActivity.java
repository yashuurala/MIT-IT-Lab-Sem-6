package com.example.rl;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RadioGroup radioGroupFruits;
    private Button buttonAdd;
    private ListView listViewSelectedFruits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI Components
        radioGroupFruits = findViewById(R.id.radioGroupFruits);
        buttonAdd = findViewById(R.id.buttonAdd);
        listViewSelectedFruits = findViewById(R.id.listViewSelectedFruits);

        // Initialize List and Adapter
        ArrayList<String> selectedFruitsList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selectedFruitsList);
        listViewSelectedFruits.setAdapter(adapter);

        // Button Click Event
        buttonAdd.setOnClickListener(v -> {
            int selectedId = radioGroupFruits.getCheckedRadioButtonId(); // Get selected RadioButton
            if (selectedId != -1) { // Ensure something is selected
                RadioButton selectedRadioButton = findViewById(selectedId);
                String selectedFruit = selectedRadioButton.getText().toString();

                // Add fruit to list if not already added
                if (!selectedFruitsList.contains(selectedFruit)) {
                    selectedFruitsList.add(selectedFruit);
                    adapter.notifyDataSetChanged(); // Update ListView
                } else {
                    Toast.makeText(MainActivity.this, "Fruit already added!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Please select a fruit!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
