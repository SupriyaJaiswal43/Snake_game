import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SnakeGame {
    private static final int BOARD_SIZE = 10;
    private static final char EMPTY_CELL = '.';
    private static final char SNAKE_BODY = 'O';
    private static final char FOOD = 'F';

    private char[][] board;
    private List<int[]> snake;
    private int[] food;

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private Direction currentDirection;

    public SnakeGame() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        snake = new ArrayList<>();
        initializeBoard();
        spawnSnake();
        spawnFood();
        currentDirection = Direction.RIGHT;
    }

    private void initializeBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY_CELL;
            }
        }
    }

    private void spawnSnake() {
        int initialRow = BOARD_SIZE / 2;
        int initialCol = BOARD_SIZE / 2;
        snake.add(new int[]{initialRow, initialCol});
        board[initialRow][initialCol] = SNAKE_BODY;
    }

    private void spawnFood() {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(BOARD_SIZE);
            col = random.nextInt(BOARD_SIZE);
        } while (board[row][col] != EMPTY_CELL);

        food = new int[]{row, col};
        board[row][col] = FOOD;
    }

    private void move() {
        int[] head = snake.get(0);
        int newRow = head[0];
        int newCol = head[1];

        switch (currentDirection) {
            case UP:
                newRow--;
                break;
            case DOWN:
                newRow++;
                break;
            case LEFT:
                newCol--;
                break;
            case RIGHT:
                newCol++;
                break;
        }

        if (newRow < 0 || newRow >= BOARD_SIZE || newCol < 0 || newCol >= BOARD_SIZE) {
            System.out.println("Game over! Out of bounds.");
            System.exit(0);
        }

        if (board[newRow][newCol] == SNAKE_BODY) {
            System.out.println("Game over! Snake collided with itself.");
            System.exit(0);
        }

        snake.add(0, new int[]{newRow, newCol});

        if (newRow == food[0] && newCol == food[1]) {
            spawnFood();
        } else {
            int[] tail = snake.remove(snake.size() - 1);
            board[tail[0]][tail[1]] = EMPTY_CELL;
        }

        board[newRow][newCol] = SNAKE_BODY;
    }

    private void displayBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayBoard();
            System.out.print("Enter direction (UP, DOWN, LEFT, RIGHT): ");
            String input = scanner.nextLine().toUpperCase();

            switch (input) {
                case "UP":
                    currentDirection = Direction.UP;
                    break;
                case "DOWN":
                    currentDirection = Direction.DOWN;
                    break;
                case "LEFT":
                    currentDirection = Direction.LEFT;
                    break;
                case "RIGHT":
                    currentDirection = Direction.RIGHT;
                    break;
                default:
                    System.out.println("Invalid direction. Please try again.");
                    continue;
            }

            move();
        }
    }

    public static void main(String[] args) {
        SnakeGame snakeGame = new SnakeGame();
        snakeGame.play();
    }
}
