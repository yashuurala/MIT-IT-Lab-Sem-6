package com.example.l3q1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

        setClickListener(R.id.imageView, R.drawable.a);
        setClickListener(R.id.imageView2, R.drawable.b);
        setClickListener(R.id.imageView3, R.drawable.c);
        setClickListener(R.id.imageView4, R.drawable.d);
        setClickListener(R.id.imageView5, R.drawable.e);
        setClickListener(R.id.imageView6, R.drawable.f);
    }

    private void setClickListener(int imageViewId, final int drawableId) {
        ImageView imageView = findViewById(imageViewId);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("image_id", drawableId); // Send the drawable ID directly
                startActivity(intent);
            }
        });
    }
}
