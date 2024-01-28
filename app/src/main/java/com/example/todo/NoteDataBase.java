package com.example.todo;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDataBase extends RoomDatabase {

    private static NoteDataBase instance_ = null;
    private static final String NOTE_DATABASE_ = "note.db";

    public static NoteDataBase getInstance(Application application) {
        if (instance_ == null) {
            instance_ = Room.databaseBuilder(application, NoteDataBase.class, NOTE_DATABASE_).
                    build();
        }
        return instance_;
    }

    public abstract NotesDao notesDao();
}
