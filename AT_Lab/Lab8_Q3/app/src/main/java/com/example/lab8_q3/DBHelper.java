package com.example.lab8_q3;
// DBHelper.java
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Clinic.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Table: Appointments
    public static final String TABLE_NAME = "appointment_table";
    public static final String COLUMN_PATIENT_NAME = "patient_name";
    public static final String COLUMN_DOCTOR_NAME = "doctor_name";
    public static final String COLUMN_APPOINTMENT_DATE = "appointment_date";
    public static final String COLUMN_ID = "_id"; //  Changed from BaseColumns._ID


    private static final String SQL_CREATE_APPOINTMENT_TABLE = "CREATE TABLE " +
            TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +  // Changed from BaseColumns._ID
            COLUMN_PATIENT_NAME + " TEXT NOT NULL, " +
            COLUMN_DOCTOR_NAME + " TEXT NOT NULL, " +
            COLUMN_APPOINTMENT_DATE + " TEXT NOT NULL" +
            ");";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_APPOINTMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
