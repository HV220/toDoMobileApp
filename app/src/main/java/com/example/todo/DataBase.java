package com.example.todo;

import java.util.ArrayList;
import java.util.Random;

public class DataBase {
    private ArrayList<Note> noteArrayList_ = new ArrayList<>();
    private static DataBase instance_ = null;

    private DataBase() {
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            Note note = new Note(i, random.nextInt(3), "Note" + i);
            noteArrayList_.add(note);
        }
    }

    public static DataBase getInstance() {
        if (instance_ == null) {
            DataBase dataBase = new DataBase();
            instance_ = dataBase;

            return dataBase;
        } else return instance_;

    }

    public void addNote(Note note) {
        noteArrayList_.add(note);
    }

    public void removeNoteById(int id) {
        for (int i = 0; i < this.noteArrayList_.size(); i++) {
            if (this.noteArrayList_.get(i).getId_() == id) {
                this.noteArrayList_.remove(id);
            }
        }
    }

    public ArrayList<Note> getArrayNotes() {
        return new ArrayList<>(this.noteArrayList_);
    }
}
