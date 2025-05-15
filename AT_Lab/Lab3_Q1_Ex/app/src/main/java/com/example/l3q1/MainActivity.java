package com.example.l3q1;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

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
        View[] images = {findViewById(R.id.imageView), findViewById(R.id.imageView2), findViewById(R.id.imageView3), findViewById(R.id.imageView4), findViewById(R.id.imageView5), findViewById(R.id.imageView6)};
        for(int i=0; i<images.length; i++) {
            int finalI = i;
            images[i].setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                            intent.putExtra("id", images[finalI].getId());
                            startActivity(intent);
                        }
                    }
            );
        }
    }
}