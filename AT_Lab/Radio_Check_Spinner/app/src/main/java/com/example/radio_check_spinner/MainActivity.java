package com.example.radio_check_spinner;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton selectedRadioButton;
    CheckBox checkBox1, checkBox2, checkBox3;
    Spinner spinner;
    Button submitButton;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        // Get references to the UI elements
        radioGroup = findViewById(R.id.radioGroup);
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        spinner = findViewById(R.id.spinner);
        submitButton = findViewById(R.id.submitButton);

        List<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("Item A");
        spinnerItems.add("Item B");
        spinnerItems.add("Item C");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerItems);
        spinner.setAdapter(adapter);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedRadio = getSelectedRadioButtonText();
                List<String> selectedCheckboxes = getSelectedCheckboxTexts();
                String selectedSpinnerItem = spinner.getSelectedItem().toString();

                long rowId = dbHelper.insertData(selectedRadio, selectedCheckboxes, selectedSpinnerItem);

                if (rowId != -1) {
                    Toast.makeText(MainActivity.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to save data.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private String getSelectedRadioButtonText() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            selectedRadioButton = findViewById(selectedId);
            return selectedRadioButton.getText().toString();
        }
        return ""; // Or handle the case where no radio button is selected
    }


    private List<String> getSelectedCheckboxTexts() {
        List<String> selected = new ArrayList<>();
        if (checkBox1.isChecked()) {
            selected.add(checkBox1.getText().toString());
        }
        if (checkBox2.isChecked()) {
            selected.add(checkBox2.getText().toString());
        }
        if (checkBox3.isChecked()) {
            selected.add(checkBox3.getText().toString());
        }
        return selected;
    }

}