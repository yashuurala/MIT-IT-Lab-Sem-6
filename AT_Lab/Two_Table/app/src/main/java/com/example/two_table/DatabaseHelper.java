package com.example.two_table;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyDatabase.db";
    private static final int DATABASE_VERSION = 1;

    // Table 1: Users
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "_id";
    public static final String COLUMN_USER_NAME = "name";
    public static final String COLUMN_USER_EMAIL = "email";

    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + "(" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_USER_NAME + " TEXT NOT NULL," +
                    COLUMN_USER_EMAIL + " TEXT NOT NULL UNIQUE);";

    // Table 2: Addresses
    public static final String TABLE_ADDRESSES = "addresses";
    public static final String COLUMN_ADDRESS_ID = "_id";
    public static final String COLUMN_USER_FOREIGN_KEY = "user_id"; // Foreign key linking to users table
    public static final String COLUMN_STREET = "street";
    public static final String COLUMN_CITY = "city";

    private static final String CREATE_TABLE_ADDRESSES =
            "CREATE TABLE " + TABLE_ADDRESSES + "(" +
                    COLUMN_ADDRESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_USER_FOREIGN_KEY + " INTEGER NOT NULL," +
                    COLUMN_STREET + " TEXT," +
                    COLUMN_CITY + " TEXT," +
                    "FOREIGN KEY(" + COLUMN_USER_FOREIGN_KEY + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_ADDRESSES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESSES);
        onCreate(db);
    }

    public long insertUser(String name, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, name);
        values.put(COLUMN_USER_EMAIL, email);
        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        return id;
    }

    public long insertAddress(long userId, String street, String city) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_FOREIGN_KEY, userId);
        values.put(COLUMN_STREET, street);
        values.put(COLUMN_CITY, city);
        long id = db.insert(TABLE_ADDRESSES, null, values);
        db.close();
        return id;
    }
}