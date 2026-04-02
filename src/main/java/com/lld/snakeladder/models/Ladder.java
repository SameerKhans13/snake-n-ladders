package com.lld.snakeladder.models;

import com.lld.snakeladder.interfaces.BoardEffect;

public class Ladder implements BoardEffect {
    private final int startPosition;
    private final int endPosition;

    public Ladder(int start, int end) {
        if (start >= end) {
            throw new IllegalArgumentException("Ladder start must be less than end");
        }
        this.startPosition = start;
        this.endPosition = end;
    }

    @Override
    public String getType() {
        return "ladder";
    }

    @Override
    public int getStartPosition() {
        return startPosition;
    }

    @Override
    public int getEndPosition() {
        return endPosition;
    }

    @Override
    public int apply(int currentPosition) {
        return (currentPosition == startPosition) ? endPosition : currentPosition;
    }

    @Override
    public String describe() {
        return "Ladder at " + startPosition + " climbs you up to " + endPosition;
    }
}
