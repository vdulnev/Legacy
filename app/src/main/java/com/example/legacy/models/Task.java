package com.example.legacy.models;

public class Task {

    private final long id;
    private final String title;
    private final boolean done;
    private final long timestamp;

    public Task(long id, String title, boolean done, long timestamp) {
        this.id = id;
        this.title = title;
        this.done = done;
        this.timestamp = timestamp;
    }

    public long getId() { return id; }
    public String getTitle() { return title; }
    public boolean isDone() { return done; }
    public long getTimestamp() { return timestamp; }
}