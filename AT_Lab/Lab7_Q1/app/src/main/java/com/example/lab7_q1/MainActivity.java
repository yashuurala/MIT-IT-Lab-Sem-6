package com.example.lab7_q1;

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

    private EditText rollNumberEditText, nameEditText, marksEditText;
    private Button addButton, updateButton, deleteButton, viewButton;
    private TextView resultTextView;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rollNumberEditText = findViewById(R.id.rollNumberEditText);
        nameEditText = findViewById(R.id.nameEditText);
        marksEditText = findViewById(R.id.marksEditText);
        addButton = findViewById(R.id.addButton);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        viewButton = findViewById(R.id.viewButton);
        resultTextView = findViewById(R.id.resultTextView);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStudent();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStudent();
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewStudents();
            }
        });
    }

    private void addStudent() {
        String rollNumber = rollNumberEditText.getText().toString();
        String name = nameEditText.getText().toString();
        String marks = marksEditText.getText().toString();

        if (rollNumber.isEmpty() || name.isEmpty() || marks.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_ROLL_NUMBER, rollNumber);
        values.put(DBHelper.COLUMN_NAME, name);
        values.put(DBHelper.COLUMN_MARKS, marks);

        long newRowId = db.insert(DBHelper.TABLE_STUDENTS, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Student added successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(this, "Error adding student", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateStudent() {
        String rollNumber = rollNumberEditText.getText().toString();
        String name = nameEditText.getText().toString();
        String marks = marksEditText.getText().toString();

        if (rollNumber.isEmpty() || name.isEmpty() || marks.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NAME, name);
        values.put(DBHelper.COLUMN_MARKS, marks);

        String selection = DBHelper.COLUMN_ROLL_NUMBER + " LIKE ?";
        String[] selectionArgs = {rollNumber};

        int count = db.update(DBHelper.TABLE_STUDENTS, values, selection, selectionArgs);

        if (count > 0) {
            Toast.makeText(this, "Student updated successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(this, "Error updating student", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteStudent() {
        String rollNumber = rollNumberEditText.getText().toString();

        if (rollNumber.isEmpty()) {
            Toast.makeText(this, "Please enter roll number", Toast.LENGTH_SHORT).show();
            return;
        }

        String selection = DBHelper.COLUMN_ROLL_NUMBER + " LIKE ?";
        String[] selectionArgs = {rollNumber};

        int deletedRows = db.delete(DBHelper.TABLE_STUDENTS, selection, selectionArgs);

        if (deletedRows > 0) {
            Toast.makeText(this, "Student deleted successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(this, "Error deleting student", Toast.LENGTH_SHORT).show();
        }
    }

    private void viewStudents() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        StringBuilder result = new StringBuilder();

        String query = "SELECT " +
                DBHelper.COLUMN_ROLL_NUMBER + ", " +
                DBHelper.COLUMN_NAME + ", " +
                DBHelper.COLUMN_MARKS +
                " FROM " + DBHelper.TABLE_STUDENTS;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String rollNumber = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ROLL_NUMBER));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NAME));
                String marks = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_MARKS));

                result.append("Roll Number: ").append(rollNumber).append("\n");
                result.append("Name: ").append(name).append("\n");
                result.append("Marks: ").append(marks).append("\n\n");
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            result.append("No students found in the database.");
        }

        resultTextView.setText(result.toString());
    }

    private void clearFields() {
        rollNumberEditText.getText().clear();
        nameEditText.getText().clear();
        marksEditText.getText().clear();
    }
}
