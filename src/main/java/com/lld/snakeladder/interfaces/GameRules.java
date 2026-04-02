package com.lld.snakeladder.interfaces;

import com.lld.snakeladder.models.Player;
import com.lld.snakeladder.models.MoveResult;

public interface GameRules {
    MoveResult applyMove(Player player, int diceValue, Board board);
    boolean isWinnerFound(Player player, Board board);
}
