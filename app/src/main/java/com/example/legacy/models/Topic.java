package com.example.legacy.models;

public class Topic {
    private final String title;
    private final String description;
    private final String colorHex;
    private final Class<?> activityClass;

    public Topic(String title, String description, String colorHex,
            Class<?> activityClass) {
        this.title = title;
        this.description = description;
        this.colorHex = colorHex;
        this.activityClass = activityClass;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getColorHex() {
        return colorHex;
    }

    public Class<?> getActivityClass() {
        return activityClass;
    }
}
