package com.example.lab8_q2;
// DBHelper.java
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Invoice.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Table: Invoices
    public static final String TABLE_NAME = "invoice_table";
    public static final String COLUMN_PRODUCT_NAME = "product_name";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_ID = "_id"; // Added _ID column

    private static final String SQL_CREATE_INVOICE_TABLE = "CREATE TABLE " +
            TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + // Use COLUMN_ID
            COLUMN_PRODUCT_NAME + " TEXT NOT NULL, " +
            COLUMN_QUANTITY + " INTEGER NOT NULL, " +
            COLUMN_PRICE + " REAL NOT NULL" +
            ");";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_INVOICE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
