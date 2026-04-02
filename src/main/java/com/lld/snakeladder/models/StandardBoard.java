package com.lld.snakeladder.models;

import com.lld.snakeladder.interfaces.Board;
import com.lld.snakeladder.interfaces.BoardEffect;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StandardBoard implements Board {
    private final int size;
    private final Map<Integer, BoardEffect> effects;

    public StandardBoard(int size) {
        this.size = size;
        this.effects = new HashMap<>();
    }

    public void addEffect(BoardEffect effect) {
        effects.put(effect.getStartPosition(), effect);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public BoardEffect getEffect(int position) {
        return effects.get(position);
    }

    @Override
    public List<BoardEffect> getAllEffects() {
        return new ArrayList<>(effects.values());
    }

    @Override
    public boolean isValidPosition(int position) {
        return position >= 0 && position <= size;
    }

    public static StandardBoard createDefault() {
        StandardBoard board = new StandardBoard(100);
        // Default Snakes
        board.addEffect(new Snake(99, 54));
        board.addEffect(new Snake(70, 55));
        board.addEffect(new Snake(52, 42));
        board.addEffect(new Snake(25, 2));
        board.addEffect(new Snake(95, 72));
        
        // Default Ladders
        board.addEffect(new Ladder(6, 25));
        board.addEffect(new Ladder(11, 40));
        board.addEffect(new Ladder(60, 85));
        board.addEffect(new Ladder(46, 90));
        board.addEffect(new Ladder(17, 69));
        
        return board;
    }
}
