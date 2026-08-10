import java.util.Scanner;

/**
 * Entry point for the ATM Interface console application.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();
        ATM atm = new ATM(bank, scanner);
        atm.start();
        scanner.close();
    }
}
