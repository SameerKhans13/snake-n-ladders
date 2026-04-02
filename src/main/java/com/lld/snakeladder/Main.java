package com.lld.snakeladder;

import com.lld.snakeladder.models.Player;
import com.lld.snakeladder.models.StandardBoard;
import com.lld.snakeladder.services.SnakeAndLadderGame;
import com.lld.snakeladder.strategies.StandardDice;
import com.lld.snakeladder.ui.GameCLI;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Step 1: Initialization
        StandardBoard board = StandardBoard.createDefault();
        StandardDice dice = new StandardDice();
        GameCLI cli = new GameCLI(board);

        try {
            // Step 2: Get Player Info
            int playerCount = cli.promptPlayerCount();
            if (playerCount < 1) {
                System.out.println("Invalid number of players.");
                return;
            }

            List<Player> players = new ArrayList<>();
            for (int i = 0; i < playerCount; i++) {
                String name = cli.promptPlayerName(i);
                players.add(new Player("P-" + (i + 1), name));
            }

            // Step 3: Game Setup
            SnakeAndLadderGame game = new SnakeAndLadderGame(players, board, dice);
            game.addObserver(cli);

            // Step 4: Run Game
            cli.displayGameStart(players);
            game.startGame();

            while (!game.isGameOver()) {
                if (!cli.promptRoll()) break;
                game.playTurn();
            }

        } catch (Exception e) {
            System.err.println("Fatal Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cli.close();
            System.out.println("\nExiting game. Thanks for playing!");
        }
    }
}
