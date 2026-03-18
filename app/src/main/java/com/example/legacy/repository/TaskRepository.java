package com.example.legacy.repository;

// Layer 2 — Repository
// Single source of truth for task data. The ViewModel talks only to this
// class and never touches TaskDbHelper directly. If you later swap SQLite
// for a remote API, only this file changes.

import android.content.Context;
import com.example.legacy.database.TaskDbHelper;
import com.example.legacy.models.Task;
import java.util.List;

public class TaskRepository {

    private final TaskDbHelper dbHelper;

    public TaskRepository(Context context) {
        // Use application context to avoid leaking Activity references
        dbHelper = new TaskDbHelper(context.getApplicationContext());
    }

    public List<Task> getAll() {
        return dbHelper.queryAll();
    }

    public void add(String title) {
        dbHelper.insert(title);
    }

    public void setDone(long id, boolean done) {
        dbHelper.setDone(id, done);
    }

    public void remove(long id) {
        dbHelper.delete(id);
    }
}