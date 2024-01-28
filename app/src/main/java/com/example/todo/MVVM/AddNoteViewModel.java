package com.example.todo.MVVM;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todo.Note;
import com.example.todo.NoteDataBase;
import com.example.todo.NotesDao;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddNoteViewModel extends AndroidViewModel {

    private NotesDao notesDao_;

    private MutableLiveData<Boolean> shouldCloseScreen_ = new MutableLiveData<>();

    private CompositeDisposable compositeDisposable_ = new CompositeDisposable();

    public AddNoteViewModel(@NonNull Application application) {
        super(application);
        notesDao_ = NoteDataBase.getInstance(application).notesDao();
    }

    public LiveData<Boolean> getShouldCloseScreen_() {
        return shouldCloseScreen_;
    }

    public void saveNote(Note note) {
        Disposable disposable = notesDao_.add(note)
                .delay(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    Log.d("AddNoteViewModel", "subscribe");
                    shouldCloseScreen_.setValue(true);
                }, throwable -> {
                    Log.d("AddNoteViewModel", "saveNote" + throwable.getMessage());
                });

        compositeDisposable_.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable_.dispose();
    }
}
