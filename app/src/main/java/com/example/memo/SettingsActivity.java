package com.example.memo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.os.Bundle;
import android.widget.RadioButton;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Setting the activity theme
        ThemeManager.setActivityTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Setting the default theme radio button
        if(ThemeManager.theme == ThemeManager.THEMES.Dark) ((RadioButton)findViewById(R.id.rb_theme_dark)).setChecked(true);
        else ((RadioButton)findViewById(R.id.rb_theme_light)).setChecked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Inflating the menu
        getMenuInflater().inflate(R.menu.menu_settings, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        saveSettings(); //Saving the setting changes made by the user

        return super.onOptionsItemSelected(item);
    }

    public void onThemeSelected(View v)
    {
        /*Gets the theme selected by the user*/

        switch(v.getId())
        {
            case R.id.rb_theme_dark : ThemeManager.theme = ThemeManager.THEMES.Dark; break; //The user selected dark theme
            case R.id.rb_theme_light : ThemeManager.theme= ThemeManager.THEMES.Light; break; //The user selected light theme
        }

    }

    private void saveSettings()
    {
       /*Saves the setting changes made by the user*/

        //Returning to the main menu
        Intent intent = new Intent(this, MemosDisplayActivity.class);
        startActivity(intent);
    }
}