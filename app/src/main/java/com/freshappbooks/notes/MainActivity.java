package com.freshappbooks.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerViewNotes;
    private final ArrayList<Note> mNotes = new ArrayList<>();
    NotesAdapter adapter;
    private NotesDBHelper mDBHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDBHelper = new NotesDBHelper(this);
        database = mDBHelper.getWritableDatabase(); //SQLDB
        getData();
        mRecyclerViewNotes = findViewById(R.id.recycler_notes);
        adapter = new NotesAdapter(mNotes);
        mRecyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewNotes.setAdapter(adapter);
        adapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongNoteClick(int position) {
                remove(position);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback
                (0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView
                    , @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                remove(viewHolder.getAdapterPosition());
            }
        });
        itemTouchHelper.attachToRecyclerView(mRecyclerViewNotes);
    }

    private void remove(int position) {
        int id = mNotes.get(position).getId();
        String where = NotesContract.NotesEntry._ID + " =? ";
        String[] wgereArgs = new String[]{Integer.toString(id)};
        database.delete(NotesContract.NotesEntry.TABLE_NAME, where, wgereArgs);
        getData();
        adapter.notifyDataSetChanged();
    }


    public void onClickAddNote(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    private void getData() {
        mNotes.clear();
        Cursor cursor = database.query(NotesContract.NotesEntry.TABLE_NAME,
                null, null, null, null, null,
                NotesContract.NotesEntry.COLUMN_PRIORITY, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry._ID));
            String title = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_TITLE));
            String desc = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DESC));
            String dayOfWeek = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK));
            int priority = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_PRIORITY));
            Note note = new Note(id, title, desc, dayOfWeek, priority);
            mNotes.add(note);
        }
        cursor.close();
    }
}
