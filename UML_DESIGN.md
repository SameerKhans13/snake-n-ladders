# Snakes & Ladders - UML Design

This document describes the low-level design of the Snake & Ladder game using Merlin UML. It leverages core design patterns like Strategy, Observer, and SOLID principles.

## 🧱 Key Design Patterns
- **Strategy Pattern**: Used for `DiceStrategy` (swappable dice behavior) and `GameRules` (swappable win/movement rules).
- **Observer Pattern**: Used to decouple the `SnakeAndLadderGame` core from the UI/CLI via `GameObserver`.
- **Factory/Static Creation**: Utilized in `StandardBoard` for default configuration.

## 📊 Class Diagram

```mermaid
classDiagram
    %% Core Enums
    class GameState {
        <<enumeration>>
        WAITING_FOR_ROLL
        ROLLING
        MOVING
        CHECKING_WIN
        GAME_OVER
    }

    %% Interfaces
    class Board {
        <<interface>>
        +getSize() int
        +getEffect(int position) BoardEffect
        +getAllEffects() List~BoardEffect~
        +isValidPosition(int position) boolean
    }

    class BoardEffect {
        <<interface>>
        +getType() String
        +getStartPosition() int
        +getEndPosition() int
        +apply(int currentPosition) int
        +describe() String
    }

    class DiceStrategy {
        <<interface>>
        +roll() int
    }

    class GameRules {
        <<interface>>
        +applyMove(Player player, int diceValue, Board board) MoveResult
        +isWinnerFound(Player player, Board board) boolean
    }

    class GameObserver {
        <<interface>>
        +onTurnStarted(Player player)
        +onDiceRolled(int value)
        +onMoveCompleted(MoveResult result)
        +onGameOver(Player winner, List~Player~ finalPositions)
    }

    %% Models
    class Player {
        -String id
        -String name
        -int position
        +getId() String
        +getName() String
        +getPosition() int
        +setPosition(int pos)
        +reset()
    }

    class MoveResult {
        -Player player
        -int diceValue
        -int positionBefore
        -int positionAfter
        -String effectApplied
        -boolean isBounceBack
        -boolean isWinner
        +getPlayer() Player
        +getDiceValue() int
        +getPositionBefore() int
        +getPositionAfter() int
        +getEffectApplied() String
        +isBounceBack() boolean
        +isWinner() boolean
    }

    %% Implementations
    class Snake {
        -int startPosition
        -int endPosition
        +apply(int currentPosition) int
    }

    class Ladder {
        -int startPosition
        -int endPosition
        +apply(int currentPosition) int
    }

    class StandardBoard {
        -int size
        -Map~Integer, BoardEffect~ effects
        +addEffect(BoardEffect effect)
        +static createDefault() StandardBoard
    }

    class StandardDice {
        -Random random
        +roll() int
    }

    class StandardGameRules {
        +applyMove(Player player, int diceVal, Board board) MoveResult
    }

    class GameCLI {
        -Scanner scanner
        -Board board
        +promptPlayerCount() int
        +promptRoll() boolean
    }

    %% Core Engine
    class SnakeAndLadderGame {
        -List~Player~ players
        -Board board
        -DiceStrategy dice
        -GameRules rules
        -List~GameObserver~ observers
        -GameState state
        +addObserver(GameObserver observer)
        +startGame()
        +playTurn()
        +getCurrentPlayer() Player
        +isGameOver() boolean
    }

    %% Relationships
    BoardEffect <|.. Snake : implements
    BoardEffect <|.. Ladder : implements
    Board <|.. StandardBoard : implements
    DiceStrategy <|.. StandardDice : implements
    GameRules <|.. StandardGameRules : implements
    GameObserver <|.. GameCLI : implements

    StandardBoard "1" *-- "many" BoardEffect : contains
    SnakeAndLadderGame "1" o-- "1" Board : has
    SnakeAndLadderGame "1" o-- "1" DiceStrategy : has
    SnakeAndLadderGame "1" o-- "1" GameRules : has
    SnakeAndLadderGame "1" o-- "many" Player : manages
    SnakeAndLadderGame "1" o-- "many" GameObserver : notifies
    SnakeAndLadderGame ..> MoveResult : creates
    StandardGameRules ..> MoveResult : creates
    
    SnakeAndLadderGame --> GameState : state
```

## 🛠️ Design Rationale

1. **Board decoupling**: The `Board` doesn't know *how* to apply moves; it only knows its configuration and size. 
2. **Strategy for Rules**: `GameRules` allows us to change the winning condition (e.g., must land exactly on 100 vs. cross 100) or movement mechanics without altering the `Game` class.
3. **Observability**: The `GameObserver` allows external UI components to hook into game events. This makes it trivial to swap the CLI for a GUI or web interface later.
4. **Immutability of Results**: `MoveResult` acts as a Data Transfer Object (DTO), ensuring that once a move is calculated, its details are preserved and cannot be tampered with by other components.
