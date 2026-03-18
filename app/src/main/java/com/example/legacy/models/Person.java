package com.example.legacy.models;

public class Person {
    private static final String[] ROLES =
            {"Developer", "Designer", "Manager", "Tester", "Architect"};
    private static int roleIndex = 0;

    private final int id;
    private final String name;
    private final String role;

    public Person(String name, String role) {
        this.id = roleIndex;
        this.name = name;
        this.role = role;
        roleIndex++;
    }

    public Person(String name) {
        this.id = roleIndex;
        this.name = name;
        this.role = ROLES[roleIndex % ROLES.length];
        roleIndex++;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
