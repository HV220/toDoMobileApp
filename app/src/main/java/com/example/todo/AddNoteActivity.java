package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.todo.MVVM.AddNoteViewModel;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextNoteAddNote_;
    private RadioButton radioButtonGreenNoteAddNote_;
    private RadioButton radioButtonYellowNoteAddNote_;
    private RadioButton radioButtonRedNoteAddNote_;
    private Button buttonMakeNoteAddNote_;

    private AddNoteViewModel addNoteViewModel_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        initViews();

        buttonMakeNoteAddNote_.setOnClickListener(v -> saveNote());
    }

    public static Intent createIntend(Context context) {
        Intent intent = new Intent(context, AddNoteActivity.class);

        return intent;
    }

    private void initViews() {
        addNoteViewModel_ = new ViewModelProvider(this).get(AddNoteViewModel.class);
        editTextNoteAddNote_ = findViewById(R.id.editTextNoteAddNote);
        radioButtonGreenNoteAddNote_ = findViewById(R.id.radioButtonGreenNoteAddNote);
        radioButtonYellowNoteAddNote_ = findViewById(R.id.radioButtonYellowNoteAddNote);
        radioButtonRedNoteAddNote_ = findViewById(R.id.radioButtonRedNoteAddNote);
        buttonMakeNoteAddNote_ = findViewById(R.id.buttonMakeNoteAddNote);
        addNoteViewModel_.getShouldCloseScreen_().observe(this, (shouldCloseScreen) -> {
            if (shouldCloseScreen) finish();
        });
    }

    private void saveNote() {
        String text = this.editTextNoteAddNote_.getText().toString().trim();
        int priority = getPriority();

        new Thread(() -> {
            addNoteViewModel_.saveNote(new Note(priority, text));
            finish();
        }).start();
    }

    private int getPriority() {
        int priority = 0;

        if (this.radioButtonGreenNoteAddNote_.isChecked()) {
            priority = 0;
        } else if (this.radioButtonYellowNoteAddNote_.isChecked()) {
            priority = 1;
        } else if (this.radioButtonRedNoteAddNote_.isChecked()) {
            priority = 2;
        }
        return priority;
    }


}