package com.example.legacy.repository;

// SQLite-backed implementation of TaskRepository.
// Depends on TaskDataSource (not the concrete TaskDbHelper) so the data
// layer can be swapped without touching this class.

import com.example.legacy.database.TaskDataSource;
import com.example.legacy.models.Task;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TaskRepositoryImpl implements TaskRepository {

    private final TaskDataSource dataSource;

    @Inject
    public TaskRepositoryImpl(TaskDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Task> getAll() {
        return dataSource.queryAll();
    }

    @Override
    public void add(String title) {
        dataSource.insert(title);
    }

    @Override
    public void setDone(long id, boolean done) {
        dataSource.setDone(id, done);
    }

    @Override
    public void remove(long id) {
        dataSource.delete(id);
    }
}
