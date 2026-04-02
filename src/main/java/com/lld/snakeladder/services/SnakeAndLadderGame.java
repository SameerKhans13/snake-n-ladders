package com.lld.snakeladder.services;

import com.lld.snakeladder.enums.GameState;
import com.lld.snakeladder.interfaces.Board;
import com.lld.snakeladder.interfaces.GameObserver;
import com.lld.snakeladder.interfaces.GameRules;
import com.lld.snakeladder.models.MoveResult;
import com.lld.snakeladder.models.Player;
import com.lld.snakeladder.strategies.DiceStrategy;

import java.util.ArrayList;
import java.util.List;

public class SnakeAndLadderGame {
    private final List<Player> players;
    private final Board board;
    private final DiceStrategy dice;
    private final GameRules rules;
    private final List<GameObserver> observers;
    
    private int currentPlayerIndex;
    private GameState state;
    private Player winner;

    public SnakeAndLadderGame(List<Player> players, Board board, DiceStrategy dice) {
        this.players = new ArrayList<>(players);
        this.board = board;
        this.dice = dice;
        this.rules = new StandardGameRules();
        this.observers = new ArrayList<>();
        this.currentPlayerIndex = 0;
        this.state = GameState.WAITING_FOR_ROLL;
    }

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void startGame() {
        if (players.isEmpty()) {
            throw new IllegalStateException("Cannot start game with no players");
        }
        notifyTurnStarted(getCurrentPlayer());
    }

    public void playTurn() {
        if (state == GameState.GAME_OVER) return;

        Player currentPlayer = getCurrentPlayer();
        
        // 1. Roll Dice
        state = GameState.ROLLING;
        int rollResult = dice.roll();
        notifyDiceRolled(rollResult);

        // 2. Move Player
        state = GameState.MOVING;
        MoveResult moveResult = rules.applyMove(currentPlayer, rollResult, board);
        notifyMoveCompleted(moveResult);

        // 3. Check for Win
        if (moveResult.isWinner()) {
            state = GameState.GAME_OVER;
            winner = currentPlayer;
            notifyGameOver(winner, players);
        } else {
            // Next turn
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            state = GameState.WAITING_FOR_ROLL;
            notifyTurnStarted(getCurrentPlayer());
        }
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public boolean isGameOver() {
        return state == GameState.GAME_OVER;
    }

    public Player getWinner() {
        return winner;
    }

    // Notifications
    private void notifyTurnStarted(Player p) {
        for (GameObserver o : observers) o.onTurnStarted(p);
    }

    private void notifyDiceRolled(int v) {
        for (GameObserver o : observers) o.onDiceRolled(v);
    }

    private void notifyMoveCompleted(MoveResult r) {
        for (GameObserver o : observers) o.onMoveCompleted(r);
    }

    private void notifyGameOver(Player w, List<Player> p) {
        for (GameObserver o : observers) o.onGameOver(w, p);
    }
}
