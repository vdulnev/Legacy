package com.example.legacy.repository;

// Layer 2 — Repository
// Single source of truth for task data. The ViewModel talks only to this
// class and never touches TaskDbHelper directly. If you later swap SQLite
// for a remote API, only this file changes.

import com.example.legacy.database.TaskDbHelper;
import com.example.legacy.models.Task;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TaskRepository {

    private final TaskDbHelper dbHelper;

    // Hilt sees @Inject here and resolves TaskDbHelper from its own binding.
    @Inject
    public TaskRepository(TaskDbHelper dbHelper) {
        this.dbHelper = dbHelper;
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