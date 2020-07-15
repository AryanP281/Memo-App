package com.example.memo;

import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MemosDisplayActivity extends AppCompatActivity {

    static final String INI_FILE_NAME = "Config.txt";

    static DBManager dbManager; //The database manager to be used for working with the memos list database

    private static boolean initialized = false; //Tells whether the activity has already been started once before

    private AdapterView.OnItemClickListener memoClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            TextView clickedView = (TextView)view; //The text view that was clicked

            //Switching the activity
            Intent intent = new Intent(MemosDisplayActivity.this, ViewMemo.class);
            intent.putExtra(ViewMemo.SELECTED_MEMO_FILE, (String)clickedView.getTag());
            startActivity(intent);

        }
    }; //The item click listener for the memo list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Restoring the saved settings
        if(!initialized) ThemeManager.loadTheme(this, INI_FILE_NAME);
        initialized = true;

        //Setting the activity theme
        ThemeManager.setActivityTheme(this);

        setContentView(R.layout.activity_memos_display);

        //Initializing the database manager
        dbManager = new DBManager(this);

        //Setting the button drawable shape background attrbutes
        setDrawableBackgroundAttrs();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        //Populating the list view with the memo list
        MemoCursorAdapter cursorAdapter = new MemoCursorAdapter(this, dbManager.getAllEntries());
        ListView listView = (ListView)findViewById(R.id.memos_list);
        listView.setAdapter(cursorAdapter);
        listView.setOnItemClickListener(this.memoClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Inflating the menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_create_new : createNewMemo(); break;
            case R.id.action_settings : goToSettings(); break;
        }

        return true;
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        //Saving the theme settings
        ThemeManager.saveSettings(this, INI_FILE_NAME);
    }

    private void createNewMemo()
    {
        /*Starts the activity used to create new memos*/

        //Creating an intent
        Intent intent = new Intent(this, CreateNewMemoActivity.class);
        startActivity(intent); //Starting the activity
    }

    private void goToSettings()
    {
        /*Switchs to the settings activity*/

        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        finish(); //Closing the current activity
    }

    private void setDrawableBackgroundAttrs()
    {
        /*Sets the attributes of the background drawable shape*/

        GradientDrawable shape = (GradientDrawable)getResources().getDrawable(R.drawable.memo_list_shape);
        shape.setShape(GradientDrawable.RECTANGLE);

        if(ThemeManager.theme == ThemeManager.THEMES.Dark)
        {
            shape.setColors(new int[]{getResources().getColor(R.color.DarkButtonGradientStart), getResources().getColor(R.color.DarkButtonGradientEnd)});
            shape.setStroke(5, getResources().getColor(R.color.DarkButtonStrokeColour));
            shape.setGradientType(GradientDrawable.SWEEP_GRADIENT);
        }
        else
        {
            shape.setColors(new int[]{getResources().getColor(R.color.LightButtonGradientStart), getResources().getColor(R.color.LightButtonGradientEnd)});
            shape.setGradientType(GradientDrawable.LINEAR_GRADIENT);
            shape.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
        }

    }

}

class MemoCursorAdapter extends CursorAdapter
{
    int count = 0;

    public MemoCursorAdapter(Context context, Cursor cursor)
    {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(R.layout.memo_selection, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        String title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TITLE)); //Getting the title of the memo
        TextView memoTextView = (TextView)view.findViewById(R.id.memo); //The text view used for displaying the memo title
        memoTextView.setText(title);  //Displaying the memo title
        memoTextView.setTag(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FILE_NAME))); //Saving the file name as tag in the text view
    }

}