package com.danhenrico.firebasecrud.model;

import androidx.annotation.NonNull;

public class Team {
    private String name;

    public Team() {}

    public Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return "Time: " + name;
    }
}
