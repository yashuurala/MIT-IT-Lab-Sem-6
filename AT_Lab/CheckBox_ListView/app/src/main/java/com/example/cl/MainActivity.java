package com.example.cl;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private CheckBox checkBoxApple, checkBoxBanana, checkBoxMango, checkBoxOrange, checkBoxGrapes;
    private Button buttonAdd;
    private ListView listViewSelectedFruits;
    private ArrayList<String> selectedFruitsList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI Components
        checkBoxApple = findViewById(R.id.checkBoxApple);
        checkBoxBanana = findViewById(R.id.checkBoxBanana);
        checkBoxMango = findViewById(R.id.checkBoxMango);
        checkBoxOrange = findViewById(R.id.checkBoxOrange);
        checkBoxGrapes = findViewById(R.id.checkBoxGrapes);
        buttonAdd = findViewById(R.id.buttonAdd);
        listViewSelectedFruits = findViewById(R.id.listViewSelectedFruits);

        // Initialize List and Adapter
        selectedFruitsList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selectedFruitsList);
        listViewSelectedFruits.setAdapter(adapter);

        // Button Click Event
        buttonAdd.setOnClickListener(v -> {
            addSelectedFruits();
        });
    }

    // Method to add selected fruits to the ListView
    private void addSelectedFruits() {
        String[] fruitNames = {"Apple", "Banana", "Mango", "Orange", "Grapes"};
        CheckBox[] checkBoxes = {checkBoxApple, checkBoxBanana, checkBoxMango, checkBoxOrange, checkBoxGrapes};

        boolean atLeastOneSelected = false;

        for (int i = 0; i < checkBoxes.length; i++) {
            if (checkBoxes[i].isChecked()) {
                String fruit = fruitNames[i];

                // Add fruit to list if not already added
                if (!selectedFruitsList.contains(fruit)) {
                    selectedFruitsList.add(fruit);
                    atLeastOneSelected = true;
                } else {
                    Toast.makeText(MainActivity.this, fruit + " is already added!", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (!atLeastOneSelected) {
            Toast.makeText(MainActivity.this, "Please select at least one fruit!", Toast.LENGTH_SHORT).show();
        }

        adapter.notifyDataSetChanged(); // Update ListView
    }
}
