import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single bank account belonging to a user.
 * Uses encapsulation (private fields + getters/setters).
 */
public class Account {

    private final String accountId;
    private final String userId;
    private String pin;
    private double balance;
    private final List<Transaction> history;

    public Account(String accountId, String userId, String pin, double initialBalance) {
        this.accountId = accountId;
        this.userId = userId;
        this.pin = pin;
        this.balance = initialBalance;
        this.history = new ArrayList<>();
    }

    public String getAccountId() {
        return accountId;
    }

    public String getUserId() {
        return userId;
    }

    public boolean checkPin(String enteredPin) {
        return this.pin.equals(enteredPin);
    }

    public void setPin(String newPin) {
        this.pin = newPin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    /** @return true if the withdrawal succeeded (sufficient funds), false otherwise. */
    public boolean withdraw(double amount) {
        if (amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }

    public void addTransaction(Transaction t) {
        history.add(t);
    }

    public List<Transaction> getHistory() {
        return history;
    }

    @Override
    public String toString() {
        return "Account[" + accountId + ", user=" + userId + ", balance=" + String.format("%.2f", balance) + "]";
    }
}
