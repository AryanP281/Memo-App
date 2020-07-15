package com.example.memo;

import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager
{
    private DatabaseHelper dbHelper; //The database helper required for connecting to the database
    private SQLiteDatabase db; //The database containing the memos' list

    public DBManager(Context c)
    {
        /*Initializes the database manager*/

        //Opening a connection to the database
        dbHelper = new DatabaseHelper(c);
        db = dbHelper.getWritableDatabase(); //Getting the connection to the database
    }

    public void close()
    {
        /*Closes the database connection*/

        dbHelper.close();
    }

    public void insert(String title, String fileName)
    {
        /*Adds a new memo to the database*/

        String sqlQuery = "INSERT INTO " + dbHelper.TABLE_NAME + "(" + dbHelper.TITLE + ", " + dbHelper.FILE_NAME + ") VALUES ('" + title + "', '" +
            fileName + "');"; //The query for adding a new memo to the database
        db.execSQL(sqlQuery); //Executing the query
    }

    public void update(String fileName, String newTitle)
    {
        /*Changes the title field of the entry with the given file name*/

        String sqlQuery = "UPDATE " + dbHelper.TABLE_NAME + " SET " + dbHelper.TITLE + "='" + newTitle + "' WHERE " + dbHelper.FILE_NAME
                + "='" + fileName + "';"; //The sql query for updating the memo title
        db.execSQL(sqlQuery); //Executing the sql query
    }

    public Cursor getAllEntries()
    {
        /*Returns all the entries in the table*/

        String sqlQuery = "SELECT * FROM " + dbHelper.TABLE_NAME + ";"; //The query for getting all the table entries

        Cursor cursor = db.rawQuery(sqlQuery, null); //Getting the entries

        return cursor;
    }

    public String getTitle(String fileName)
    {
        /*Returns the title of the memo with the given file name*/

        String query = "SELECT * FROM " + dbHelper.TABLE_NAME + " WHERE " + dbHelper.FILE_NAME + "='" + fileName + "';"; //The query for getting the entry

        Cursor cursor = db.rawQuery(query, null); //Executing the query
        cursor.moveToFirst(); //Moving the cursor to the entry

        return cursor.getString(cursor.getColumnIndex(dbHelper.TITLE));
    }

    public void delete(String fileName)
    {
        /*Deletes the memo entry with the given file name*/

        String query = String.format("DELETE FROM %s WHERE %s='%s';", dbHelper.TABLE_NAME, dbHelper.FILE_NAME, fileName); //The query for deleteing the entry

        db.execSQL(query); //Deleting the entry
    }

}
