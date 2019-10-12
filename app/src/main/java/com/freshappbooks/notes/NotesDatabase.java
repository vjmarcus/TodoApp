package com.freshappbooks.notes;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {

    private static NotesDatabase sDatabase;
    private static final String DB_NAME = "notes2.db";
    private static final Object LOCK = new Object();

    public static NotesDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (sDatabase == null) {
                sDatabase = Room.databaseBuilder(context, NotesDatabase.class, DB_NAME)
                        .build();
            }
        }
        return sDatabase;
    }

    public abstract NotesDao mNotesDao();
}
