package com.lld.snakeladder.services;

import com.lld.snakeladder.interfaces.Board;
import com.lld.snakeladder.interfaces.BoardEffect;
import com.lld.snakeladder.interfaces.GameRules;
import com.lld.snakeladder.models.MoveResult;
import com.lld.snakeladder.models.Player;

public class StandardGameRules implements GameRules {
    @Override
    public MoveResult applyMove(Player player, int diceValue, Board board) {
        int oldPosition = player.getPosition();
        int newPosition = oldPosition + diceValue;
        boolean isBounceBack = false;

        // Bounce back rule: If move exceeds board size
        if (newPosition > board.getSize()) {
            int overflow = newPosition - board.getSize();
            newPosition = board.getSize() - overflow;
            isBounceBack = true;
        }

        // Apply board effects (snakes/ladders)
        BoardEffect effect = board.getEffect(newPosition);
        String effectType = null;
        if (effect != null) {
            newPosition = effect.apply(newPosition);
            effectType = effect.getType();
        }

        boolean isWinner = isWinnerFound(player, board, newPosition);
        player.setPosition(newPosition);

        return new MoveResult(player, diceValue, oldPosition, newPosition, effectType, isBounceBack, isWinner);
    }

    @Override
    public boolean isWinnerFound(Player player, Board board) {
        return player.getPosition() == board.getSize();
    }

    private boolean isWinnerFound(Player player, Board board, int position) {
        return position == board.getSize();
    }
}
