package com.example.memo;

import android.content.Context;

import java.io.File;
import java.io.RandomAccessFile;

public class ThemeManager
{
    static enum THEMES {Dark, Light};
    static THEMES theme = THEMES.Dark; //Specifies what is the current theme of the app
    static void setActivityTheme(Context activity)
    {
        /*Sets the theme of the activity according to the current app theme*/

        switch(theme)
        {
            case Dark : activity.setTheme(R.style.AppThemeDark); break;
            case Light : activity.setTheme(R.style.AppThemeLight); break;
            default: activity.setTheme(R.style.AppThemeDark); break;
        }
    }
    static void saveSettings(Context activity, String fileName)
    {
        /*Saves the settings data to the file before app exits
         activity = the activity whose onStop was invoked
         fileName = the name of the file in which to save the settings*/

        try
        {
            RandomAccessFile file = new RandomAccessFile(new File(activity.getFilesDir(), fileName), "rw");

            //Positioning the pointer to the start of the file
            file.seek(0);
            file.writeBoolean(theme == THEMES.Dark); //Saving the theme

            //Closing the file after use
            file.close();
        }
        catch(Exception e)
        {
        }
    }

    static void loadTheme(Context activity, String fileName)
    {
        /*Reads the theme from the given file */

        try
        {
            RandomAccessFile file = new RandomAccessFile(new File(activity.getFilesDir(), fileName), "rw");

            //Reading the saved setting
            file.seek(0);
            theme = file.readBoolean() ? THEMES.Dark : THEMES.Light; //Setting the theme

            //Closing the file after use
            file.close();
        }
        catch(Exception e)
        {
            theme = THEMES.Dark; //The default theme
        }

    }
}
