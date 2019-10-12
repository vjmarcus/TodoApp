package com.freshappbooks.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

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

    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
//        mDBHelper = new NotesDBHelper(this);
//        mSQLiteDatabase = mDBHelper.getWritableDatabase();
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
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
            Note note = new Note(title, desc, dayOfWeek, priority);
            mViewModel.insertNote(note);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Field all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isField(String title, String desc) {
        return !title.isEmpty() && !desc.isEmpty();
    }
}
