package com.lld.snakeladder.interfaces;

public interface BoardEffect {
    String getType();
    int getStartPosition();
    int getEndPosition();
    int apply(int currentPosition);
    String describe();
}
