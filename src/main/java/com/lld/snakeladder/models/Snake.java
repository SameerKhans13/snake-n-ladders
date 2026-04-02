package com.lld.snakeladder.models;

import com.lld.snakeladder.interfaces.BoardEffect;

public class Snake implements BoardEffect {
    private final int startPosition;
    private final int endPosition;

    public Snake(int start, int end) {
        if (start <= end) {
            throw new IllegalArgumentException("Snake start must be greater than end");
        }
        this.startPosition = start;
        this.endPosition = end;
    }

    @Override
    public String getType() {
        return "snake";
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
        return "Snake at " + startPosition + " slips you down to " + endPosition;
    }
}
