package com.example.l1q1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Declare UI components
    private EditText editTextName, editTextJobTitle, editTextCompany, editTextEmail;
    private TextView textViewProfile;
    private Button buttonSubmit, buttonSelectImage;
    private ImageView imageViewProfile;
    private static final int PICK_IMAGE_REQUEST = 1;  // Constant to identify the image picker intent
    private Uri selectedImageUri;  // To store the URI of the selected image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        editTextName = findViewById(R.id.editTextName);
        editTextJobTitle = findViewById(R.id.editTextJobTitle);
        editTextCompany = findViewById(R.id.editTextCompany);
        editTextEmail = findViewById(R.id.editTextEmail);
        textViewProfile = findViewById(R.id.textViewProfile);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        imageViewProfile = findViewById(R.id.imageViewProfile);


        // Set up the submit button to display the profile
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user inputs
                String name = editTextName.getText().toString();
                String jobTitle = editTextJobTitle.getText().toString();
                String company = editTextCompany.getText().toString();
                String email = editTextEmail.getText().toString();

                // Check if any input is empty and show a toast message if so
                if (name.isEmpty() || jobTitle.isEmpty() || company.isEmpty() || email.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create the profile string to display
                String userProfile = "Name: " + name + "\n" +
                        "Job Title: " + jobTitle + "\n" +
                        "Company: " + company + "\n" +
                        "Email: " + email;

                // Display the profile on the screen
                textViewProfile.setText(userProfile);
                textViewProfile.setVisibility(View.VISIBLE); // Make the profile visible
            }
        });
    }
}
