package com.lld.snakeladder.interfaces;

import com.lld.snakeladder.models.MoveResult;
import com.lld.snakeladder.models.Player;
import java.util.List;

public interface GameObserver {
    void onTurnStarted(Player player);
    void onDiceRolled(int value);
    void onMoveCompleted(MoveResult result);
    void onGameOver(Player winner, List<Player> finalPositions);
}
