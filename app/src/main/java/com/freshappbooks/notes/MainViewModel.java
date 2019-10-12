package com.freshappbooks.notes;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static NotesDatabase mNotesDatabase;
    private LiveData<List<Note>> notes;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mNotesDatabase = NotesDatabase.getInstance(getApplication());
        notes = mNotesDatabase.mNotesDao().getAllNotes();
    }

    public NotesDatabase getNotesDatabase() {
        return mNotesDatabase;
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }

    public void insertNote(Note note) {
        new InsertTask().execute(note);
    }

    public void deleteNote(Note note) {
        new DeleteTask().execute(note);
    }

    private static class InsertTask extends AsyncTask<Note, Void, Void> {
        @Override
        protected Void doInBackground(Note... notes) {
            if (notes != null && notes.length > 0) {
                mNotesDatabase.mNotesDao().insertNote(notes[0]);
            }
            return null;
        }
    }
    private static class DeleteTask extends AsyncTask<Note, Void, Void> {
        @Override
        protected Void doInBackground(Note... notes) {
            if (notes != null && notes.length > 0) {
                mNotesDatabase.mNotesDao().deleteNote(notes[0]);
            }
            return null;
        }
    }
}
