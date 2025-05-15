package com.example.pb;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView tvStatus,textView2;
    private EditText Et;
    private Button button;
    private Handler h = new Handler();  // Correct handler initialization

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        tvStatus = findViewById(R.id.tv_status);
        button = findViewById(R.id.button);
        Et = findViewById(R.id.editTextText);
        textView2=findViewById(R.id.textView2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = Et.getText().toString().trim();

                if (!str.isEmpty()) {
                    progressBar.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.VISIBLE);
                    textView2.setText("Processing...");

                    // Correct way to use postDelayed with Runnable
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            textView2.setText("Success");
                        }
                    }, 3000);  // 3 seconds delay

                } else {
                    Toast.makeText(MainActivity.this, "Please enter text", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
