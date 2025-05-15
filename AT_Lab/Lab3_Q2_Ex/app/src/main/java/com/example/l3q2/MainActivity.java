package com.example.l3q2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private EditText feedbackEditText;
    private Button submitButton;
    private Button clearButton;
    private TextView feedbackTextView;
    private TextView charCountText;
    private RatingBar ratingBar;
    private ProgressBar progressBar;

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
        // Initialize the views
        feedbackEditText = findViewById(R.id.feedbackEditText);
        submitButton = findViewById(R.id.submitButton);
        clearButton = findViewById(R.id.clearButton);
        feedbackTextView = findViewById(R.id.feedbackTextView);
        charCountText = findViewById(R.id.charCountText);
        ratingBar = findViewById(R.id.ratingBar);
        progressBar = findViewById(R.id.progressBar);

        // Set up character count listener
        feedbackEditText.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Update character count
                int currentLength = charSequence.length();
                charCountText.setText(currentLength + "/500");
            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {
            }
        });

        // Submit button click listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the feedback entered by the user
                String feedback = feedbackEditText.getText().toString().trim();
                float rating = ratingBar.getRating();

                if (!feedback.isEmpty() && rating > 0) {
                    progressBar.setVisibility(View.VISIBLE); // Show the progress bar
                    feedbackTextView.setText("Submitting your feedback...");

                    // Simulate feedback submission process (could be an API call or saving data)
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE); // Hide progress bar after submission
                            feedbackTextView.setText("Thank you for your feedback!\nRating: " + rating + " stars\n" + "Feedback: " + feedback);
                        }
                    }, 2000); // Delay to simulate submission
                } else {
                    feedbackTextView.setText("Please provide feedback and a rating.");
                }
            }
        });

        // Clear button click listener
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedbackEditText.setText(""); // Clear feedback
                ratingBar.setRating(0); // Reset rating
                feedbackTextView.setText("Your feedback will be displayed here");
                charCountText.setText("0/500"); // Reset character count
            }
        });
    }
}
