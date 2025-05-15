package com.example.l2q2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editTextInput;
    private TextView textViewEncrypted;
    private static final int SHIFT_KEY = 3; // Shift value for the additive cipher

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();
    }

    private void setupUI() {
        editTextInput = findViewById(R.id.editTextInput);
        textViewEncrypted = findViewById(R.id.textViewEncrypted);
        Button buttonEncrypt = findViewById(R.id.buttonEncrypt);

        buttonEncrypt.setOnClickListener(v -> encryptText());
    }

    private void encryptText() {
        String inputText = editTextInput.getText().toString().replace(" ", ""); // Ignore spaces
        StringBuilder encryptedText = new StringBuilder();

        for (char ch : inputText.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                encryptedText.append((char) ((ch - base + SHIFT_KEY) % 26 + base));
            } else {
                encryptedText.append(ch); // Keep non-letter characters unchanged
            }
        }

        textViewEncrypted.setText(encryptedText.toString());
    }
}
