package com.lld.snakeladder.models;

public class Player {
    private final String id;
    private final String name;
    private int position;

    public Player(String id, String name) {
        this(id, name, 0);
    }

    public Player(String id, String name, int position) {
        this.id = id;
        this.name = name;
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void reset() {
        this.position = 0;
    }

    @Override
    public String toString() {
        return name + " (" + id + ")";
    }
}
