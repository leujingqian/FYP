package com.example.myapplication1.Model;

public class Scoreboard {
    private int points;
    private String name;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Scoreboard(int points, String name) {
        this.points = points;
        this.name = name;
    }
}
