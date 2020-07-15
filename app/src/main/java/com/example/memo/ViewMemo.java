package com.example.memo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ViewMemo extends AppCompatActivity {

    static final String SELECTED_MEMO_FILE = "FILE"; //The intent extra key for passing the file name of the selected memo
    static final String EDIT_MEMO = "EDIT"; //The intent extra key for specifying to the CreateNewMemo activity that the memo is to be edited

    static Memo memo = null; //The memo being viewed

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Setting the activity theme
        ThemeManager.setActivityTheme(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_memo);

    }

    @Override
    protected void onResume()
    {
        super.onResume();

        //Initializing the memo
        Intent intent = getIntent(); //The intent for starting the activity
        String memoFileName = intent.getStringExtra(SELECTED_MEMO_FILE); //The name of the file that contains the memo text
        memo = new Memo(MemosDisplayActivity.dbManager.getTitle(memoFileName), null, memoFileName);
        memo.readFromFile(this, true);

        //Updating the views
        ((TextView)findViewById(R.id.memo_display_title)).setText(memo.title); //Setting the memo title
        ((TextView)findViewById(R.id.memo_display_text)).setText(memo.text); //Setting the memo text
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_view_memo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_edit_memo : editMemo(); break;
            case R.id.action_delete_memo : deleteMemoData(); finish(); break;
            case android.R.id.home : finish(); break;
        }

        return true;
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        //Saving the theme settings
        ThemeManager.saveSettings(this, MemosDisplayActivity.INI_FILE_NAME);
    }

    private void editMemo()
    {
        /*Starts the create new memo activity for editing*/

        Intent intent = new Intent(this, CreateNewMemoActivity.class);
        intent.putExtra(EDIT_MEMO, true);
        startActivity(intent);
    }

    private void deleteMemoData()
    {
        /*Deletes the data for the current memo*/

        //Checking if file was successfully deleted
        if(!memo.deleteFile(this))
        {
            showToast("Unable to delete memo file");
            return;
        }

        //Deleting file from database
        MemosDisplayActivity.dbManager.delete(memo.filePath);
    }

    private void showToast(String msg)
    {
        /*Creates a toast to display the given message*/

        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

}