package com.example.legacy.viewmodel;

// Layer 3 — ViewModel
// Survives configuration changes (rotation). Exposes immutable LiveData so
// the Activity can observe state without the ViewModel holding a View reference.
// All business decisions live here; the Activity is just a display.

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.legacy.models.Task;
import com.example.legacy.repository.TaskRepository;
import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private final TaskRepository repository;

    // MutableLiveData is private — only this class can push new values.
    // The Activity receives the read-only LiveData<> interface.
    private final MutableLiveData<List<Task>> tasksLiveData = new MutableLiveData<>();

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
        refresh();
    }

    /** Observed by the Activity. Emits a new list on every change. */
    public LiveData<List<Task>> getTasks() {
        return tasksLiveData;
    }

    public void addTask(String title) {
        repository.add(title);
        refresh();
    }

    public void toggleDone(long id, boolean done) {
        repository.setDone(id, done);
        refresh();
    }

    public void deleteTask(long id) {
        repository.remove(id);
        refresh();
    }

    // NOTE: In production, run DB work on a background thread (e.g. with
    // Executors or Kotlin coroutines) and use postValue() instead of setValue().
    private void refresh() {
        tasksLiveData.setValue(repository.getAll());
    }
}