package com.example.lab8_q2;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

    private EditText productNameEditText, quantityEditText, priceEditText;
    private Button addButton, generateInvoiceButton, productInfoButton;
    private TextView invoiceTextView;
    private DBHelper dbHelper; // Use DBHelper
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productNameEditText = findViewById(R.id.productNameEditText);
        quantityEditText = findViewById(R.id.quantityEditText);
        priceEditText = findViewById(R.id.priceEditText);
        addButton = findViewById(R.id.addButton);
        generateInvoiceButton = findViewById(R.id.generateInvoiceButton);
        invoiceTextView = findViewById(R.id.invoiceTextView);
        productInfoButton = findViewById(R.id.productInfoButton);

        dbHelper = new DBHelper(this); // Use DBHelper
        db = dbHelper.getWritableDatabase();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        generateInvoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateInvoice();
            }
        });

        productInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProductInfo();
            }
        });
    }

    private void addItem() {
        String productName = productNameEditText.getText().toString();
        String quantityStr = quantityEditText.getText().toString();
        String priceStr = priceEditText.getText().toString();

        if (productName.isEmpty() || quantityStr.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity = Integer.parseInt(quantityStr);
        double price = Double.parseDouble(priceStr);

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_PRODUCT_NAME, productName); // Use DBHelper constants
        values.put(DBHelper.COLUMN_QUANTITY, quantity);     // Use DBHelper constants
        values.put(DBHelper.COLUMN_PRICE, price);        // Use DBHelper constants

        long newRowId = db.insert(DBHelper.TABLE_NAME, null, values); // Use DBHelper constants

        if (newRowId != -1) {
            Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();
            productNameEditText.getText().clear();
            quantityEditText.getText().clear();
            priceEditText.getText().clear();
        } else {
            Toast.makeText(this, "Error adding item", Toast.LENGTH_SHORT).show();
        }
    }

    private void generateInvoice() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        StringBuilder invoice = new StringBuilder("Invoice:\n\n");
        double totalAmount = 0;

        // Use rawQuery
        String query = "SELECT " +
                DBHelper.COLUMN_ID + " AS invoice_id, " +  // Removed table name qualification
                DBHelper.COLUMN_PRODUCT_NAME + ", " +      // Removed table name qualification
                DBHelper.COLUMN_QUANTITY + ", " +          // Removed table name qualification
                DBHelper.COLUMN_PRICE +
                " FROM " + DBHelper.TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_QUANTITY));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PRICE));
                String productName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PRODUCT_NAME));

                double itemTotal = quantity * price;
                invoice.append(productName).append(" x ").append(quantity).append(" = $").append(String.format("%.2f", itemTotal)).append("\n");
                totalAmount += itemTotal;
            } while (cursor.moveToNext());
        }
        cursor.close();

        invoice.append("\nTotal Amount: $").append(String.format("%.2f", totalAmount));
        invoiceTextView.setText(invoice.toString());
    }

    private void getProductInfo() {
        String sql = "SELECT COUNT(*) as total_products, " +
                "SUM(" + DBHelper.COLUMN_PRICE + " * " + DBHelper.COLUMN_QUANTITY + ") as total_price, " + // Use DBHelper constants
                "MAX(" + DBHelper.COLUMN_PRICE + ") as max_price, " +  // Use DBHelper constants
                "MIN(" + DBHelper.COLUMN_PRICE + ") as min_price, " +  // Use DBHelper constants
                "(SELECT " + DBHelper.COLUMN_PRODUCT_NAME + " FROM " + DBHelper.TABLE_NAME + " ORDER BY " + DBHelper.COLUMN_PRICE + " DESC LIMIT 1) as max_product_name, " + // Use DBHelper constants
                "(SELECT " + DBHelper.COLUMN_PRODUCT_NAME + " FROM " + DBHelper.TABLE_NAME + " ORDER BY " + DBHelper.COLUMN_PRICE + " ASC LIMIT 1) as min_product_name " + // Use DBHelper constants
                "FROM " + DBHelper.TABLE_NAME;  // Use DBHelper constants

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            int totalProducts = cursor.getInt(cursor.getColumnIndexOrThrow("total_products"));
            double totalPrice = cursor.getDouble(cursor.getColumnIndexOrThrow("total_price"));
            double maxPrice = cursor.getDouble(cursor.getColumnIndexOrThrow("max_price"));
            double minPrice = cursor.getDouble(cursor.getColumnIndexOrThrow("min_price"));
            String maxProductName = cursor.getString(cursor.getColumnIndexOrThrow("max_product_name"));
            String minProductName = cursor.getString(cursor.getColumnIndexOrThrow("min_product_name"));

            String info = "Total Products: " + totalProducts + "\n" +
                    "Total Price: $" + String.format("%.2f", totalPrice) + "\n" +
                    "Maximum Price: $" + String.format("%.2f", maxPrice) + " (" + maxProductName + ")\n" +
                    "Minimum Price: $" + String.format("%.2f", minPrice) + " (" + minProductName + ")";

            invoiceTextView.setText(info);
        } else {
            invoiceTextView.setText("No product information available.");
        }

        cursor.close();
    }
}
