package com.example.two_table;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextStreet;
    private EditText editTextCity;
    private Button buttonSave,buttonViewData;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextStreet = findViewById(R.id.editTextStreet);
        editTextCity = findViewById(R.id.editTextCity);
        buttonSave = findViewById(R.id.buttonSave);
        buttonViewData = findViewById(R.id.buttonViewData);

        dbHelper = new DatabaseHelper(this);

        buttonViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayDataActivity.class);
                startActivity(intent);
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String street = editTextStreet.getText().toString().trim();
                String city = editTextCity.getText().toString().trim();

                if (!name.isEmpty() && !email.isEmpty()) {
                    long userId = dbHelper.insertUser(name, email);

                    if (userId != -1) {
                        if (!street.isEmpty() && !city.isEmpty()) {
                            long addressId = dbHelper.insertAddress(userId, street, city);

                            if (addressId != -1) {
                                Toast.makeText(MainActivity.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();
                                editTextName.getText().clear();
                                editTextEmail.getText().clear();
                                editTextStreet.getText().clear();
                                editTextCity.getText().clear();
                            } else {
                                Toast.makeText(MainActivity.this, "Failed to save address.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "User saved, please enter address details.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to save user", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please enter name and email.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}