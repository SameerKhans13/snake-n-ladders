package com.lld.snakeladder.ui;

import com.lld.snakeladder.interfaces.Board;
import com.lld.snakeladder.interfaces.BoardEffect;
import com.lld.snakeladder.interfaces.GameObserver;
import com.lld.snakeladder.models.MoveResult;
import com.lld.snakeladder.models.Player;

import java.util.List;
import java.util.Scanner;

public class GameCLI implements GameObserver {
    private final Scanner scanner;
    private final Board board;

    public GameCLI(Board board) {
        this.scanner = new Scanner(System.in);
        this.board = board;
    }

    public int promptPlayerCount() {
        System.out.print("Enter number of players (2-4): ");
        int count = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return count;
    }

    public String promptPlayerName(int index) {
        System.out.print("Enter name for Player " + (index + 1) + ": ");
        return scanner.nextLine();
    }

    public void displayGameStart(List<Player> players) {
        System.out.println("\n=== Game Starting ===");
        for (Player p : players) {
            System.out.println("Enrolled " + p.getName() + " starting at position 0");
        }
        System.out.println("Board Size: " + board.getSize());
        displayBoardConfiguration();
    }

    public void displayBoardConfiguration() {
        System.out.println("\n--- Board Configuration ---");
        for (BoardEffect effect : board.getAllEffects()) {
            System.out.println(effect.describe());
        }
        System.out.println("---------------------------\n");
    }

    public boolean promptRoll() {
        System.out.print("Press [Enter] to roll dice (or type 'q' to quit): ");
        String input = scanner.nextLine();
        return !input.equalsIgnoreCase("q");
    }

    @Override
    public void onTurnStarted(Player player) {
        System.out.println("\nNext Turn: " + player.getName() + " (Position: " + player.getPosition() + ")");
    }

    @Override
    public void onDiceRolled(int value) {
        System.out.println("Rolling dice... Rolled a " + value + "!");
    }

    @Override
    public void onMoveCompleted(MoveResult result) {
        System.out.println(result.getPlayer().getName() + " moves from " + result.getPositionBefore() + " to " + result.getPositionAfter());
        if (result.isBounceBack()) {
            System.out.println("BOUNCE BACK! Too far, moving backwards from the edge.");
        }
        if (result.getEffectApplied() != null) {
            System.out.println("- Applied board effect: " + result.getEffectApplied() + "!");
        }
    }

    @Override
    public void onGameOver(Player winner, List<Player> finalPositions) {
        System.out.println("\n=========================");
        System.out.println("WINNER: " + winner.getName() + "!");
        System.out.println("=========================");
        System.out.println("\nFinal Leaderboard:");
        for (Player p : finalPositions) {
            System.out.println(p.getName() + ": Position " + p.getPosition());
        }
    }

    public void close() {
        scanner.close();
    }
}
