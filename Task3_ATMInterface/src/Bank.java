import java.util.HashMap;
import java.util.Map;

/**
 * Represents the bank as a whole: holds all accounts and provides
 * lookup / authentication / transfer helper logic shared between accounts.
 */
public class Bank {

    private final Map<String, Account> accounts = new HashMap<>();

    public Bank() {
        seedDemoAccounts();
    }

    /** Pre-populate a couple of demo accounts so the ATM is usable out of the box. */
    private void seedDemoAccounts() {
        addAccount(new Account("ACC1001", "user1", "1234", 5000.00));
        addAccount(new Account("ACC1002", "user2", "4321", 2500.00));
    }

    public void addAccount(Account account) {
        accounts.put(account.getAccountId(), account);
    }

    public Account getAccount(String accountId) {
        return accounts.get(accountId);
    }

    public boolean accountExists(String accountId) {
        return accounts.containsKey(accountId);
    }

    /**
     * Authenticates a user by account ID and PIN.
     * @return the matching Account, or null if authentication fails.
     */
    public Account authenticate(String accountId, String pin) {
        Account acc = accounts.get(accountId);
        if (acc != null && acc.checkPin(pin)) {
            return acc;
        }
        return null;
    }

    /**
     * Transfers money between two accounts, logging a transaction on each side.
     * @return true if the transfer succeeded, false if funds were insufficient
     *         or the recipient account does not exist.
     */
    public boolean transfer(Account from, String toAccountId, double amount) {
        Account to = accounts.get(toAccountId);
        if (to == null || from.getAccountId().equals(toAccountId)) {
            return false;
        }
        if (!from.withdraw(amount)) {
            return false;
        }
        to.deposit(amount);
        from.addTransaction(new Transaction(Transaction.Type.TRANSFER_OUT, amount, "to " + toAccountId));
        to.addTransaction(new Transaction(Transaction.Type.TRANSFER_IN, amount, "from " + from.getAccountId()));
        return true;
    }
}
