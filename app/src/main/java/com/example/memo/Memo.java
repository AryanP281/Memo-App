package com.example.memo;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.Date;

class Memo
{
    String title; //The title of the memo
    String text; //The text in the memo
    String filePath = null; //The path to file in which the memo is saved

    Memo(String title, String text)
    {
        this.title = title;
        this.text = text;
    }

    Memo(String title, String text, String path)
    {
        this.title = title;
        this.text = text;
        this.filePath = path;
    }

    String writeToFile(Context context)
    {
        /*Writes the memo to text file.
        Returns the name of the file*/

        //Checking if a new memo is to be created
        if(filePath == null)
            this.filePath = String.valueOf((new Date()).getTime()) + ".txt"; //The file name
        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(context.getFilesDir(), this.filePath)); //Opening an output stream to the file

            byte data[] = this.text.getBytes(); //The byte data
            fileOutputStream.write(data); //Writing the data to the file

            fileOutputStream.close(); //Closing the output stream after use
        }
        catch(Exception e)
        {
            return null; //Writing to file failed
        }

        return this.filePath;
    }

    boolean readFromFile(Context context, boolean overwrite)
    {
        /*Reads the memo data from its file
        overwrite = if true, then data is read from file only if this.text == null
           Returns false if reading fails*/

        //Checking if file exists
        if(filePath == null) return false;

        //Not reading from file as overwriting is prohibited
        if(!overwrite && this.text != null) return true;

        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(new File(context.getFilesDir(), this.filePath)));

            //Reading the memo text
            String line = null;
            this.text = "";
            while((line = reader.readLine()) != null)
            {
                this.text += line + "\n";
            }

            //Closing the reader after use
            reader.close();
        }
        catch(Exception e)
        {
            return false;
        }

        return true;
    }

    boolean deleteFile(Context context)
    {
        /*Deletes the file in which the memo is saved*/

        try
        {
            File file = new File(context.getFilesDir(), this.filePath); //Opening the file

            return file.delete(); //Deleting the file
        }
        catch(NullPointerException e)
        {
            return false;
        }
        catch(SecurityException e)
        {
            return false;
        }
    }

}
