package com.freshappbooks.notes;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NotesDao {  //Database access object

    @Query("SELECT * FROM notes ORDER BY priority")
    LiveData<List<Note>> getAllNotes();

    @Insert
    void insertNote(Note note);

    @Delete
    void deleteNote (Note note);

    @Query("DELETE FROM notes")
    void deleteAllNotes();
}
