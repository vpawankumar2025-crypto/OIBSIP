import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single logged transaction (withdrawal, deposit, transfer).
 */
public class Transaction {

    public enum Type { WITHDRAW, DEPOSIT, TRANSFER_OUT, TRANSFER_IN }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final Type type;
    private final double amount;
    private final String detail;
    private final LocalDateTime timestamp;

    public Transaction(Type type, double amount, String detail) {
        this.type = type;
        this.amount = amount;
        this.detail = detail;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("[%s] %-13s $%-10.2f %s",
                timestamp.format(FORMATTER), type, amount, detail == null ? "" : detail);
    }
}
