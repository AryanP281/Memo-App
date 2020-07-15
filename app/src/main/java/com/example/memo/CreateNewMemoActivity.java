package com.example.memo;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class CreateNewMemoActivity extends AppCompatActivity {

    boolean toEdit = false; //Tells whether a memo is to be edited or a new memo is to be created

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Setting the activity theme
        ThemeManager.setActivityTheme(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_memo);

        //Checking if memo is to be edited
        if(getIntent().hasExtra(ViewMemo.EDIT_MEMO)) //Checking if the id of the memo to be edited wa sent
        {
            //Updating the views
            ((EditText)findViewById(R.id.title)).setText(ViewMemo.memo.title);
            ((EditText)findViewById(R.id.text)).setText(ViewMemo.memo.text);

            toEdit = true; //Memo is to be edited
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_creat_new_memo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
           case R.id.action_save_memo : if(!saveMemo()) break; finish(); return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        //Saving the theme settings
        ThemeManager.saveSettings(this, MemosDisplayActivity.INI_FILE_NAME);

    }

    private boolean saveMemo()
    {
        /*Saves the memo created by the user
        returns false if the write operation fails*/

        //Checking if a new memo needs to be created
        Memo memo = toEdit ? ViewMemo.memo : null;
        if(memo == null) {
            memo = new Memo(((EditText) findViewById(R.id.title)).getText().toString(), ((EditText) findViewById(R.id.text)).getText().toString());
        }
        else
        {
            memo.title = ((EditText) findViewById(R.id.title)).getText().toString();
            memo.text = ((EditText) findViewById(R.id.text)).getText().toString();
        }

        //Checking if the title is empty
        if(memo.title.length() == 0)
            showToast("Title cannot be empty");

        //Writing the memo to file
        String fileName = memo.writeToFile(this);

        //Checking if data was saved
        if(fileName == null) return false;

        //Updating the memo list in case of new memo
        return updateMemoList(fileName, memo.title,  toEdit);
    }

    private void showToast(String msg)
    {
        /*Creates a toast to display the given message*/

        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    private boolean updateMemoList(String fileName, String memoTitle, boolean overwrite)
    {
        /*Updates the database containing the memos list*/

        if(overwrite) MemosDisplayActivity.dbManager.update(fileName, memoTitle);
        else MemosDisplayActivity.dbManager.insert(memoTitle, fileName);

        return true;
    }

}