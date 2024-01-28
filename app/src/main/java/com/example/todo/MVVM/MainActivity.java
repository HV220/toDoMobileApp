package com.example.todo.MVVM;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.todo.AddNoteActivity;
import com.example.todo.Note;
import com.example.todo.NotesAdapter;
import com.example.todo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNotes_;
    private FloatingActionButton floatingActionButtonAddNoteMainActivity_;

    private NotesAdapter notesAdapter_;

    private MainViewModel viewModel_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        makeNote();
    }

    private void initViews() {
        viewModel_ = new ViewModelProvider(this).get(MainViewModel.class);
        recyclerViewNotes_ = findViewById(R.id.recyclerViewNotes);
        floatingActionButtonAddNoteMainActivity_ = findViewById(
                R.id.floatingActionButtonAddNoteMainActivity
        );

        notesAdapter_ = new NotesAdapter();

        recyclerViewNotes_.setAdapter(notesAdapter_);

        viewModel_.getNotes().observe(this, notes -> notesAdapter_.setNotes(notes));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Note note = notesAdapter_.getNotes().get(position);
                viewModel_.remove(note);
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerViewNotes_);
    }

    private void makeNote() {
        floatingActionButtonAddNoteMainActivity_.setOnClickListener(v -> {

            Intent intent = AddNoteActivity.createIntend(MainActivity.this);
            startActivity(intent);
        });
    }
}