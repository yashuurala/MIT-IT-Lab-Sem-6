// MainActivity.java
package com.example.l2q1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private CheckBox checkBoxApple, checkBoxOrange, checkBoxBanana, checkBoxMango;
    private TextView textViewSelectedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        checkBoxApple = findViewById(R.id.checkBoxApple);
        checkBoxOrange = findViewById(R.id.checkBoxOrange);
        checkBoxBanana = findViewById(R.id.checkBoxBanana);
        checkBoxMango = findViewById(R.id.checkBoxMango);
        textViewSelectedItems = findViewById(R.id.textViewSelectedItems);
        Button buttonAddItem = findViewById(R.id.buttonAddItem);

        buttonAddItem.setOnClickListener(v -> displaySelectedItems());
    }

    private void displaySelectedItems() {
        StringBuilder selectedItems = new StringBuilder("Selected Items:\n");

        if (checkBoxApple.isChecked()) {
            selectedItems.append("- Apple\n");
        }
        if (checkBoxOrange.isChecked()) {
            selectedItems.append("- Orange\n");
        }
        if (checkBoxBanana.isChecked()) {
            selectedItems.append("- Banana\n");
        }
        if (checkBoxMango.isChecked()) {
            selectedItems.append("- Mango\n");
        }
        textViewSelectedItems.setText(selectedItems.toString());
    }
}
