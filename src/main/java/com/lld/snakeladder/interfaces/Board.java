package com.lld.snakeladder.interfaces;

import java.util.List;

public interface Board {
    int getSize();
    BoardEffect getEffect(int position);
    List<BoardEffect> getAllEffects();
    boolean isValidPosition(int position);
}
