package com.example.legacy.repository;

import com.example.legacy.models.Task;
import java.util.List;

/**
 * Contract for the repository layer.
 * ViewModel depends only on this interface, not on the SQLite implementation.
 */
public interface TaskRepository {
    List<Task> getAll();
    void add(String title);
    void setDone(long id, boolean done);
    void remove(long id);
}
