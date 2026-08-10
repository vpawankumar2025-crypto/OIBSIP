import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Central place to obtain a JDBC connection to the local SQLite database
 * and make sure the required tables exist.
 *
 * Requires the sqlite-jdbc driver on the classpath, e.g.:
 *   https://github.com/xerial/sqlite-jdbc  (org.xerial:sqlite-jdbc)
 */
public class DBConnection {

    private static final String DB_URL = "jdbc:sqlite:reservation.db";

    /** Opens (and lazily initializes) the SQLite database. */
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL);
        initializeSchema(conn);
        return conn;
    }

    private static void initializeSchema(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "username TEXT PRIMARY KEY, " +
                    "password TEXT NOT NULL)");

            st.execute("CREATE TABLE IF NOT EXISTS trains (" +
                    "train_number TEXT PRIMARY KEY, " +
                    "train_name TEXT NOT NULL)");

            st.execute("CREATE TABLE IF NOT EXISTS reservations (" +
                    "pnr TEXT PRIMARY KEY, " +
                    "passenger_name TEXT NOT NULL, " +
                    "train_number TEXT NOT NULL, " +
                    "train_name TEXT NOT NULL, " +
                    "class_type TEXT NOT NULL, " +
                    "date_of_journey TEXT NOT NULL, " +
                    "source_station TEXT NOT NULL, " +
                    "destination_station TEXT NOT NULL)");

            // Seed a demo login and a few trains if the tables are empty.
            try (Statement check = conn.createStatement()) {
                var rs = check.executeQuery("SELECT COUNT(*) AS c FROM users");
                if (rs.next() && rs.getInt("c") == 0) {
                    st.execute("INSERT INTO users(username, password) VALUES ('admin', 'admin123')");
                }
            }
            try (Statement check = conn.createStatement()) {
                var rs = check.executeQuery("SELECT COUNT(*) AS c FROM trains");
                if (rs.next() && rs.getInt("c") == 0) {
                    st.execute("INSERT INTO trains(train_number, train_name) VALUES " +
                            "('12951','Mumbai Rajdhani')," +
                            "('12301','Howrah Rajdhani')," +
                            "('12621','Tamil Nadu Express')," +
                            "('12009','Shatabdi Express')");
                }
            }
        }
    }
}
