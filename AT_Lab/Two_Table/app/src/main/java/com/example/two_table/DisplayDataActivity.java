package com.example.two_table;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayDataActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private TextView textViewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        textViewData = findViewById(R.id.textViewData);
        dbHelper = new DatabaseHelper(this);

        displayAllData();
    }

    private void displayAllData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        StringBuilder data = new StringBuilder();

        String query = "SELECT " +
                DatabaseHelper.TABLE_USERS + "." + DatabaseHelper.COLUMN_USER_ID + " AS user_id, " +
                DatabaseHelper.TABLE_USERS + "." + DatabaseHelper.COLUMN_USER_NAME + ", " +
                DatabaseHelper.TABLE_USERS + "." + DatabaseHelper.COLUMN_USER_EMAIL + ", " +
                DatabaseHelper.TABLE_ADDRESSES + "." + DatabaseHelper.COLUMN_STREET + ", " +
                DatabaseHelper.TABLE_ADDRESSES + "." + DatabaseHelper.COLUMN_CITY +
                " FROM " + DatabaseHelper.TABLE_USERS +
                " LEFT JOIN " + DatabaseHelper.TABLE_ADDRESSES +
                " ON " + DatabaseHelper.TABLE_USERS + "." + DatabaseHelper.COLUMN_USER_ID + " = " + DatabaseHelper.TABLE_ADDRESSES + "." + DatabaseHelper.COLUMN_USER_FOREIGN_KEY;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                long userId = cursor.getLong(cursor.getColumnIndexOrThrow("user_id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_NAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_EMAIL));
                String street = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_STREET));
                String city = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CITY));

                data.append("User ID: ").append(userId).append("\n");
                data.append("Name: ").append(name).append("\n");
                data.append("Email: ").append(email).append("\n");
                data.append("Street: ").append(street != null ? street : "N/A").append("\n");
                data.append("City: ").append(city != null ? city : "N/A").append("\n");
                data.append("------------------------\n");
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            data.append("No data found in the database.");
        }

        db.close();
        textViewData.setText(data.toString());
    }
}