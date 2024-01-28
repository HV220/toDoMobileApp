package com.example.todo.MVVM;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ComputableLiveData;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todo.Note;
import com.example.todo.NoteDataBase;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private NoteDataBase noteDataBase_;
    private CompositeDisposable compositeDisposable_ = new CompositeDisposable();


    public MainViewModel(@NonNull Application application) {
        super(application);
        noteDataBase_ = NoteDataBase.getInstance(application);
    }

    public LiveData<List<Note>> getNotes() {
        return noteDataBase_.notesDao().getNotes();
    }

    public void remove(Note note) {
        Disposable disposable = noteDataBase_.notesDao().remove(note.getId_())
                .delay(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    Log.d("MainViewModel", "disposable");
                }, (throwable -> {
                    Log.d("MainViewModel", "remove" + throwable.getMessage());
                }));

        compositeDisposable_.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable_.dispose();
    }
}
