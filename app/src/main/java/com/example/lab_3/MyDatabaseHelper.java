package com.example.lab_3;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Date;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "DB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "my_odn";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_PIB = "Full_name";
    private static final String COLUMN_DATE = "Date";

    private static final String COLUMN_F = "Name";
    private static final String COLUMN_S = "SName";
    private static final String COLUMN_L = "LName";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_PIB + " TEXT, " +
                        COLUMN_DATE + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_S + " TEXT, " +
                COLUMN_F + " TEXT, " +
                COLUMN_L + " TEXT, " +
                COLUMN_DATE + " TEXT);";
        db.execSQL(query);
    }


    void add(String Full_name){
        Date date = new Date();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PIB, Full_name);
        cv.put(COLUMN_DATE, String.valueOf(date));

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();
        }
    }


    Cursor readAllData (){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }


    public void replaceLastRow(String Full_name) {
        String query = "SELECT * FROM " + TABLE_NAME;
        Date date = new Date();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String lastId;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToLast();
        lastId = cursor.getString(0);

        cv.put(COLUMN_PIB, Full_name);
        cv.put(COLUMN_DATE, String.valueOf(date));

        db.update(TABLE_NAME, cv, lastId,null);


    }






}
