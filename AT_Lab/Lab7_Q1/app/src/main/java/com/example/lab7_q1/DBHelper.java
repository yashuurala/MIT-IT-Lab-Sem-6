package com.example.lab7_q1;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Student.db";
    private static final int DATABASE_VERSION = 1;

    // Table: Students
    public static final String TABLE_STUDENTS = "student_table";
    public static final String COLUMN_ROLL_NUMBER = "roll_number";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_MARKS = "marks";

    private static final String CREATE_TABLE_STUDENTS =
            "CREATE TABLE " + TABLE_STUDENTS + "(" +
                    COLUMN_ROLL_NUMBER + " TEXT PRIMARY KEY, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_MARKS + " TEXT NOT NULL" +
                    ");";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STUDENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }
}
