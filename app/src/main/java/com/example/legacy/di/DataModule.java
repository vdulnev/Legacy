package com.example.legacy.di;

// Hilt module that binds each interface to its concrete implementation.
//
// @Binds is a zero-overhead alternative to @Provides for simple delegation.
// Hilt reads the method signature:
//   abstract InterfaceType bindX(ConcreteType impl)
// and generates code that satisfies InterfaceType requests using ConcreteType.

import com.example.legacy.database.TaskDataSource;
import com.example.legacy.database.TaskDbHelper;
import com.example.legacy.repository.TaskRepository;
import com.example.legacy.repository.TaskRepositoryImpl;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public abstract class DataModule {

    @Binds
    @Singleton
    abstract TaskDataSource bindTaskDataSource(TaskDbHelper impl);

    @Binds
    @Singleton
    abstract TaskRepository bindTaskRepository(TaskRepositoryImpl impl);
}
