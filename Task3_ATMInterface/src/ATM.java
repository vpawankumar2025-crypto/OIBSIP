import java.util.List;
import java.util.Scanner;

/**
 * Drives the interactive ATM session: login, menu, and each transaction type.
 */
public class ATM {

    private static final int MAX_LOGIN_ATTEMPTS = 3;

    private final Bank bank;
    private final Scanner scanner;

    public ATM(Bank bank, Scanner scanner) {
        this.bank = bank;
        this.scanner = scanner;
    }

    /** Runs the full login -> menu -> logout flow. */
    public void start() {
        System.out.println("=========================================");
        System.out.println("           WELCOME TO JAVA ATM           ");
        System.out.println("=========================================");
        System.out.println("(Demo accounts: ACC1001/1234, ACC1002/4321)\n");

        Account account = login();
        if (account == null) {
            System.out.println("Too many incorrect attempts. Card retained. Goodbye.");
            return;
        }

        System.out.println("\nLogin successful. Welcome, " + account.getUserId() + "!");
        runMenu(account);
    }

    private Account login() {
        for (int attempt = 1; attempt <= MAX_LOGIN_ATTEMPTS; attempt++) {
            System.out.print("Enter User/Account ID: ");
            String userId = scanner.nextLine().trim();
            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine().trim();

            Account account = bank.authenticate(userId, pin);
            if (account != null) {
                return account;
            }
            System.out.println("Invalid User ID or PIN. Attempts remaining: " + (MAX_LOGIN_ATTEMPTS - attempt));
        }
        return null;
    }

    private void runMenu(Account account) {
        boolean running = true;
        while (running) {
            System.out.println("\n----------- MAIN MENU -----------");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Choose an option (1-5): ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    showHistory(account);
                    break;
                case "2":
                    withdraw(account);
                    break;
                case "3":
                    deposit(account);
                    break;
                case "4":
                    transfer(account);
                    break;
                case "5":
                    System.out.println("Thank you for using Java ATM. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1-5.");
            }
        }
    }

    private void showHistory(Account account) {
        List<Transaction> history = account.getHistory();
        System.out.println("\n--- Transaction History ---");
        if (history.isEmpty()) {
            System.out.println("No transactions yet this session.");
        } else {
            for (Transaction t : history) {
                System.out.println(t);
            }
        }
        System.out.println("Current Balance: $" + String.format("%.2f", account.getBalance()));
    }

    private void withdraw(Account account) {
        double amount = readAmount("Enter amount to withdraw: ");
        if (amount <= 0) {
            System.out.println("Amount must be positive.");
            return;
        }
        if (account.withdraw(amount)) {
            account.addTransaction(new Transaction(Transaction.Type.WITHDRAW, amount, null));
            System.out.println("Withdrawal successful. New balance: $" + String.format("%.2f", account.getBalance()));
        } else {
            System.out.println("Insufficient Funds");
        }
    }

    private void deposit(Account account) {
        double amount = readAmount("Enter amount to deposit: ");
        if (amount <= 0) {
            System.out.println("Amount must be positive.");
            return;
        }
        account.deposit(amount);
        account.addTransaction(new Transaction(Transaction.Type.DEPOSIT, amount, null));
        System.out.println("Deposit successful. New balance: $" + String.format("%.2f", account.getBalance()));
    }

    private void transfer(Account account) {
        System.out.print("Enter recipient Account ID: ");
        String recipientId = scanner.nextLine().trim();

        if (!bank.accountExists(recipientId)) {
            System.out.println("Recipient account does not exist.");
            return;
        }
        double amount = readAmount("Enter amount to transfer: ");
        if (amount <= 0) {
            System.out.println("Amount must be positive.");
            return;
        }
        if (bank.transfer(account, recipientId, amount)) {
            System.out.println("Transfer successful. New balance: $" + String.format("%.2f", account.getBalance()));
        } else {
            System.out.println("Transfer failed: Insufficient Funds or invalid recipient.");
        }
    }

    private double readAmount(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid numeric amount.");
            }
        }
    }
}
