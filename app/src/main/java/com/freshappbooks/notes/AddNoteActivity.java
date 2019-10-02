package com.freshappbooks.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class AddNoteActivity extends AppCompatActivity {

    EditText mEditTextTitle;
    EditText mEditTextDesc;
    Spinner mSpinnerDaysOfWeek;
    Button mButtonSaveNote;
    RadioGroup mRadioGroupPriority;

    private NotesDBHelper mDBHelper;
    private SQLiteDatabase mSQLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        mDBHelper = new NotesDBHelper(this);
        mSQLiteDatabase = mDBHelper.getWritableDatabase();
    mEditTextTitle = findViewById(R.id.editTextTitle);
    mEditTextDesc = findViewById(R.id.editTextDesc);
    mSpinnerDaysOfWeek = findViewById(R.id.spinnerDaysOfWeek);
    mButtonSaveNote = findViewById(R.id.buttonSaveNote);
    mRadioGroupPriority = findViewById(R.id.radioGroupPriority);

    }
    public void saveNote(View view) {
        String title = mEditTextTitle.getText().toString().trim();
        String desc = mEditTextDesc.getText().toString().trim();
        String dayOfWeek = mSpinnerDaysOfWeek.getSelectedItem().toString();
        int radioButtonId = mRadioGroupPriority.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioButtonId);
        int priority = Integer.parseInt(radioButton.getText().toString());

        if (isField(title, desc)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, title);
            contentValues.put(NotesContract.NotesEntry.COLUMN_DESC, desc);
            contentValues.put(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK, dayOfWeek);
            contentValues.put(NotesContract.NotesEntry.COLUMN_PRIORITY, priority);
            mSQLiteDatabase.insert(NotesContract.NotesEntry.TABLE_NAME, null, contentValues);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Empty fields", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isField (String title, String desc) {
        return !title.isEmpty() && !desc.isEmpty();
    }
}
