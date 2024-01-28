
package com.example.todo;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id_;
    private int priority_;
    private String text_;

    public Note(int id_, int priority_, String text_) {
        this.id_ = id_;
        this.priority_ = priority_;
        this.text_ = text_;
    }

    @Ignore
    public Note(int priority_, String text_) {
        this.id_ = 0;
        this.priority_ = priority_;
        this.text_ = text_;
    }

    public int getId_() {
        return id_;
    }

    public int getPriority_() {
        return priority_;
    }

    public String getText_() {
        return text_;
    }

}
