import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Number Guessing Game
 * ---------------------
 * The computer picks a random number and the user tries to guess it,
 * receiving "Too High!" / "Too Low!" hints until they guess correctly
 * or run out of attempts.
 *
 * Features implemented:
 *  - Random number generation within a chosen difficulty range
 *  - Console input via Scanner
 *  - Too High / Too Low / Correct feedback
 *  - Visible attempt counter
 *  - Maximum attempt limit with "You Lost!" message revealing the number
 *  - Play Again prompt
 *  - Score tracking across rounds (Round X - guessed in Y attempts)
 *  - Difficulty levels: Easy / Medium / Hard
 */
public class NumberGuessingGame {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    public static void main(String[] args) {
        System.out.println("=======================================");
        System.out.println("      WELCOME TO NUMBER GUESSING GAME   ");
        System.out.println("=======================================");

        int roundNumber = 1;
        boolean playAgain = true;

        while (playAgain) {
            System.out.println("\n----- ROUND " + roundNumber + " -----");
            Difficulty difficulty = chooseDifficulty();
            int attemptsUsed = playRound(difficulty);

            if (attemptsUsed > 0) {
                System.out.println("Round " + roundNumber + " - guessed in " + attemptsUsed + " attempts");
            } else {
                System.out.println("Round " + roundNumber + " - not guessed (lost)");
            }

            playAgain = askYesNo("\nPlay again? (yes/no): ");
            roundNumber++;
        }

        System.out.println("\nThanks for playing! Goodbye.");
        scanner.close();
    }

    /** Lets the user pick a difficulty level and returns the chosen enum. */
    private static Difficulty chooseDifficulty() {
        System.out.println("Choose difficulty:");
        System.out.println("  1. Easy   (1-50,  10 attempts)");
        System.out.println("  2. Medium (1-100, 7 attempts)");
        System.out.println("  3. Hard   (1-200, 5 attempts)");

        while (true) {
            System.out.print("Enter choice (1-3): ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": return Difficulty.EASY;
                case "2": return Difficulty.MEDIUM;
                case "3": return Difficulty.HARD;
                default:
                    System.out.println("Invalid choice, please enter 1, 2, or 3.");
            }
        }
    }

    /**
     * Plays a single round of the game.
     * @return the number of attempts used to guess correctly, or 0 if the player lost.
     */
    private static int playRound(Difficulty difficulty) {
        int target = random.nextInt(difficulty.max - difficulty.min + 1) + difficulty.min;
        int attempts = 0;
        boolean guessedCorrectly = false;

        System.out.println("I'm thinking of a number between " + difficulty.min +
                " and " + difficulty.max + ". You have " + difficulty.maxAttempts + " attempts.");

        while (attempts < difficulty.maxAttempts && !guessedCorrectly) {
            int guess = readIntInRange(difficulty.min, difficulty.max,
                    "Attempt " + (attempts + 1) + "/" + difficulty.maxAttempts + " - Enter your guess: ");
            attempts++;

            if (guess == target) {
                System.out.println("Correct! You guessed it in " + attempts + " attempts.");
                guessedCorrectly = true;
            } else if (guess < target) {
                System.out.println("Too Low!");
            } else {
                System.out.println("Too High!");
            }
        }

        if (!guessedCorrectly) {
            System.out.println("You Lost! The number was: " + target);
            return 0;
        }
        return attempts;
    }

    /** Reads an integer guess, re-prompting on invalid or out-of-range input. */
    private static int readIntInRange(int min, int max, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value < min || value > max) {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("That's not a valid number. Try again.");
            }
        }
    }

    /** Simple yes/no prompt returning true for yes. */
    private static boolean askYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim().toLowerCase();
            if (line.equals("yes") || line.equals("y")) return true;
            if (line.equals("no") || line.equals("n")) return false;
            System.out.println("Please answer yes or no.");
        }
    }

    /** Difficulty presets. */
    private enum Difficulty {
        EASY(1, 50, 10),
        MEDIUM(1, 100, 7),
        HARD(1, 200, 5);

        final int min, max, maxAttempts;

        Difficulty(int min, int max, int maxAttempts) {
            this.min = min;
            this.max = max;
            this.maxAttempts = maxAttempts;
        }
    }
}
