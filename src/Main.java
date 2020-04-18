import java.util.Arrays;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static ArrayList<Integer> playerPositions = new ArrayList<>();
    static ArrayList<Integer> cpuPositions = new ArrayList<>();

    public static void main(String[] args) {
        char[][] gameBoard = { // single quotes, because char is used.
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '},
        };

        printGameBoard(gameBoard);

        while (true) {
            // PLAYER'S TURN
            // ask input from player
            System.out.println(); // empty line

            boolean error;
            int playerPosition = 0;

            // validate input as acceptable integer value only
            do {
                try {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Enter your placement of 'X' (1-9): Enter position number (1-9):");
                    playerPosition = scanner.nextInt();

                    if (playerPosition < 1 || playerPosition > 9) {
                        throw new InputMismatchException();
                    }

                    // Keep asking while position is not taken
                    while (playerPositions.contains(playerPosition) || cpuPositions.contains(playerPosition)) {
                        System.out.println("Position taken! Enter a correct position:");
                        playerPosition = scanner.nextInt();
                    }

                    error = false;
                } catch (InputMismatchException e) {
                    System.out.print("Invalid input. ");
                    error = true;
                }
            }
            while (error);

            System.out.println("Player picked: " + playerPosition);

            placePiece(gameBoard, playerPosition, "player");
            printGameBoard(gameBoard);
            String result = checkWinner();
            if (result.length() > 0) {
                System.out.println(result);
                // break;
                restartGame();
            }

            // CPU'S TURN
            System.out.println(); // empty line
            Random randomPosition = new Random();
            int cpuPosition = randomPosition.nextInt(9) + 1;
            // Keep generating while position is not good
            while (cpuPositions.contains(cpuPosition) || playerPositions.contains(cpuPosition)) {
                cpuPosition = randomPosition.nextInt(9) + 1; // generate again
            }
            System.out.println("CPU picked: " + cpuPosition);
            placePiece(gameBoard, cpuPosition, "CPU");

            printGameBoard(gameBoard);

            result = checkWinner();
            if (result.length() > 0) {
                System.out.println(result);
                // break;
                restartGame();
            }
        }
    }

    public static void printGameBoard(char[][] gameBoard) {
        // Print the game board with for each loop (shorter than normal FOR loop)
        for (char[] row : gameBoard) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println(); // create empty line for each row
        }
    }

    public static void placePiece(char[][] gameBoard, int positionOfPlacement, String user) {

        // choose 'X' for a player and '0' for CPU
        char symbol = ' ';
        if (user.equals("player")) {
            symbol = 'X';
            playerPositions.add(positionOfPlacement);
        } else if (user.equals("CPU")) {
            symbol = '0';
            cpuPositions.add(positionOfPlacement);
        }

        switch (positionOfPlacement) {
            case 1:
                gameBoard[0][0] = symbol;
                break;
            case 2:
                gameBoard[0][2] = symbol;
                break;
            case 3:
                gameBoard[0][4] = symbol;
                break;
            case 4:
                gameBoard[2][0] = symbol;
                break;
            case 5:
                gameBoard[2][2] = symbol;
                break;
            case 6:
                gameBoard[2][4] = symbol;
                break;
            case 7:
                gameBoard[4][0] = symbol;
                break;
            case 8:
                gameBoard[4][2] = symbol;
                break;
            case 9:
                gameBoard[4][4] = symbol;
                break;
            default:
                System.out.println("Not a valid value for position!");
                // "break;" is not required in "default" switch.
        }
    }

    public static String checkWinner() {

        // 8 wining conditions (positions)
        // for import of "List" use "java.util", not "java.awt"
        List topRow = Arrays.asList(1, 2, 3);
        List middleRow = Arrays.asList(4, 5, 6);
        List bottomRow = Arrays.asList(7, 8, 9);
        List leftColumn = Arrays.asList(1, 4, 7);
        List middleColumn = Arrays.asList(2, 5, 8);
        List rightColumn = Arrays.asList(3, 6, 9);
        List cross1 = Arrays.asList(1, 5, 9);
        List cross2 = Arrays.asList(3, 5, 7);

        // at positions to a list to prepare for looping
        List<List> winningConditions = new ArrayList<>();
        winningConditions.add(topRow);
        winningConditions.add(middleRow);
        winningConditions.add(bottomRow);
        winningConditions.add(leftColumn);
        winningConditions.add(middleColumn);
        winningConditions.add(rightColumn);
        winningConditions.add(cross1);
        winningConditions.add(cross2);


        // for each loop
        for (List l : winningConditions) {
            // if player position contains all items in list of any winning positions
            if (playerPositions.containsAll(l)) {
                return "\nCongratulation you won! Restarting game.";
            } else if (cpuPositions.containsAll(l)) {
                return "\nCPU wins! Sorry :( Restarting game.";
            }
        }

        if (playerPositions.size() + cpuPositions.size() == 9) {
            System.out.println("\nNo winner. Restarting game.");
            restartGame();
        }

        return "";
    }

    public static void restartGame() {
        playerPositions.clear(); // clear positions for new game
        cpuPositions.clear(); // clear positions for new game
        String[] args = new String[0]; // "String[] args = {};" also works
        main(args);
    }
}
