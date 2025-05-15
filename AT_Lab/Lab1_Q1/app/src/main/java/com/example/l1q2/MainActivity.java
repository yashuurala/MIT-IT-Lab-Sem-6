package com.example.l1q2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.l1q1.R;

public class MainActivity extends AppCompatActivity {
    private EditText editTextName, editTextJobTitle, editTextCompany, editTextEmail;
    private TextView textViewProfile;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextJobTitle = findViewById(R.id.editTextJobTitle);
        editTextCompany = findViewById(R.id.editTextCompany);
        editTextEmail = findViewById(R.id.editTextEmail);
        textViewProfile = findViewById(R.id.textViewProfile);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String jobTitle = editTextJobTitle.getText().toString();
                String company = editTextCompany.getText().toString();
                String email = editTextEmail.getText().toString();

                if (name.isEmpty() || jobTitle.isEmpty() || company.isEmpty() || email.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                String userProfile = "Name: " + name + "\n" +
                        "Job Title: " + jobTitle + "\n" +
                        "Company: " + company + "\n" +
                        "Email: " + email;

                textViewProfile.setText(userProfile);
                textViewProfile.setVisibility(View.VISIBLE); // Make the profile visible
            }
        });
    }
}
