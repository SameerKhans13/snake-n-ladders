package com.lld.snakeladder.models;

public class MoveResult {
    private final Player player;
    private final int diceValue;
    private final int positionBefore;
    private final int positionAfter;
    private final String effectApplied;
    private final boolean isBounceBack;
    private final boolean isWinner;

    public MoveResult(Player player, int diceValue, int positionBefore, int positionAfter, String effectApplied, boolean isBounceBack, boolean isWinner) {
        this.player = player;
        this.diceValue = diceValue;
        this.positionBefore = positionBefore;
        this.positionAfter = positionAfter;
        this.effectApplied = effectApplied;
        this.isBounceBack = isBounceBack;
        this.isWinner = isWinner;
    }

    public Player getPlayer() {
        return player;
    }

    public int getDiceValue() {
        return diceValue;
    }

    public int getPositionBefore() {
        return positionBefore;
    }

    public int getPositionAfter() {
        return positionAfter;
    }

    public String getEffectApplied() {
        return effectApplied;
    }

    public boolean isBounceBack() {
        return isBounceBack;
    }

    public boolean isWinner() {
        return isWinner;
    }
}
