package com.example.lab8_q3;
// MainActivity.java
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
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText patientNameEditText, doctorNameEditText, appointmentDateEditText;
    private Button bookAppointmentButton;
    private TextView appointmentResultTextView;
    private DBHelper dbHelper; // Use DBHelper
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        patientNameEditText = findViewById(R.id.patientNameEditText);
        doctorNameEditText = findViewById(R.id.doctorNameEditText);
        appointmentDateEditText = findViewById(R.id.appointmentDateEditText);
        bookAppointmentButton = findViewById(R.id.bookAppointmentButton);
        appointmentResultTextView = findViewById(R.id.appointmentResultTextView);

        dbHelper = new DBHelper(this); // Use DBHelper
        db = dbHelper.getWritableDatabase();

        bookAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookAppointment();
            }
        });
    }

    private void bookAppointment() {
        String patientName = patientNameEditText.getText().toString();
        String doctorName = doctorNameEditText.getText().toString();
        String appointmentDate = appointmentDateEditText.getText().toString();

        if (patientName.isEmpty() || doctorName.isEmpty() || appointmentDate.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isDoctorAvailable(doctorName, appointmentDate)) {
            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_PATIENT_NAME, patientName); // Use DBHelper constants
            values.put(DBHelper.COLUMN_DOCTOR_NAME, doctorName);   // Use DBHelper constants
            values.put(DBHelper.COLUMN_APPOINTMENT_DATE, appointmentDate);  // Use DBHelper constants

            long newRowId = db.insert(DBHelper.TABLE_NAME, null, values); // Use DBHelper constants

            if (newRowId != -1) {
                appointmentResultTextView.setText("Appointment booked successfully.");
                clearFields();
            } else {
                appointmentResultTextView.setText("Error booking appointment.");
            }
        } else {
            appointmentResultTextView.setText("Doctor not available on that date.");
        }
    }

    private boolean isDoctorAvailable(String doctorName, String appointmentDate) {
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Get readable database

        String query = "SELECT " + DBHelper.COLUMN_ID +  // Use DBHelper constants
                " FROM " + DBHelper.TABLE_NAME +       // Use DBHelper constants
                " WHERE " + DBHelper.COLUMN_DOCTOR_NAME + " = ? AND " +  // Use DBHelper constants
                DBHelper.COLUMN_APPOINTMENT_DATE + " = ?";             // Use DBHelper constants
        String[] selectionArgs = {doctorName, appointmentDate};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        boolean available = cursor.getCount() == 0;
        cursor.close();
        return available;
    }

    private void clearFields() {
        patientNameEditText.getText().clear();
        doctorNameEditText.getText().clear();
        appointmentDateEditText.getText().clear();
    }
}
