package com.example.legacy.database;

import com.example.legacy.models.Task;
import java.util.List;

/**
 * Contract for the raw data-access layer.
 * Swap SQLite for Room, an in-memory store, or a remote API by providing a
 * different implementation — nothing above this interface needs to change.
 */
public interface TaskDataSource {
    long insert(String title);
    int  setDone(long id, boolean done);
    int  delete(long id);
    List<Task> queryAll();
}
