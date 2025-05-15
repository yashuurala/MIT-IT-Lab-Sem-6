package com.example.radio_check_spinner;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FormData.db";
    private static final int DATABASE_VERSION = 1;

    // Table Name
    public static final String TABLE_NAME = "form_data";

    // Table Columns
    public static final String COLUMN_ID = "_id"; // Convention for primary key
    public static final String COLUMN_RADIO = "selected_radio";
    public static final String COLUMN_CHECKBOXES = "selected_checkboxes";
    public static final String COLUMN_SPINNER = "selected_spinner";

    // SQL statement to create the table
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_RADIO + " TEXT," +
                    COLUMN_CHECKBOXES + " TEXT," +
                    COLUMN_SPINNER + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public long insertData(String radioValue, List<String> checkboxValues, String spinnerValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RADIO, radioValue);
        values.put(COLUMN_CHECKBOXES, String.join(",", checkboxValues)); // Store checkboxes as comma-separated string
        values.put(COLUMN_SPINNER, spinnerValue);

        long newRowId = db.insert(TABLE_NAME, null, values);
        db.close();
        return newRowId;
    }
}