package com.example.memo;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{

    static final String DB_NAME = "MEMOS.DB"; //The name of the database
    public static final String TABLE_NAME = "MEMOS"; //The name of the table in the database containing the data
    public static final String ID = "_id"; //The name of the column containing the id
    public static final String TITLE = "Title"; // The name of the column containing the memo title
    public static final String FILE_NAME = "File"; //The name of the column containing the memo file name

    public DatabaseHelper(Context context)
    {
        /*Initializes the database*/

        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        /*Creates the database if it doesn't exist*/

        String createTableQuery = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE + " TEXT NOT NULL, " +
                FILE_NAME + " TEXT NOT NULL);"; //The sql query for creating the table

        database.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
    {
        return;
    }
}
