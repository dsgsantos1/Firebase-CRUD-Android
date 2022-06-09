package com.danhenrico.firebasecrud.model;

import androidx.annotation.NonNull;

public class Team {
    private String id;
    private String name;

    public Team() {}

    public Team(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return name;
    }
}
