package com.example.sl;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Spinner spinnerFruits;
    private Button buttonAdd;
    private ListView listViewSelectedFruits;
    private String selectedFruit; // Store selected fruit

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

        // Initialize UI elements
        spinnerFruits = findViewById(R.id.spinnerFruits);
        buttonAdd = findViewById(R.id.buttonAdd);
        listViewSelectedFruits = findViewById(R.id.listViewSelectedFruits);

        // Fruits options for Spinner
        String[] fruits = {"Apple", "Banana", "Mango", "Orange", "Grapes", "Pineapple", "Watermelon"};

        // Spinner Adapter
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, fruits);
        spinnerFruits.setAdapter(spinnerAdapter);

        // Initialize ListView data
        ArrayList<String> selectedFruitsList= new ArrayList<>();
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selectedFruitsList);
        listViewSelectedFruits.setAdapter(listAdapter);

        // Handle Spinner Selection
        spinnerFruits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedFruit = fruits[position]; // Store selected fruit
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedFruit = fruits[0]; // Default selection
            }
        });

        // Handle Button Click (Add Selected Fruit to ListView)
        buttonAdd.setOnClickListener(v -> {
            if (!selectedFruitsList.contains(selectedFruit)) { // Prevent duplicate items
                selectedFruitsList.add(selectedFruit);
                listAdapter.notifyDataSetChanged(); // Update ListView
                Toast.makeText(MainActivity.this, selectedFruit + " added to list", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, selectedFruit + " is already in the list!", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle ListView Item Click (Optional: Remove item when clicked)
        listViewSelectedFruits.setOnItemClickListener((parent, view, position, id) -> {
            String removedFruit = selectedFruitsList.remove(position);
            listAdapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, removedFruit + " removed from list", Toast.LENGTH_SHORT).show();
        });
    }
}