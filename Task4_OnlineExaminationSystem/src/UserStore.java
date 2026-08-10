import java.util.HashMap;
import java.util.Map;

/**
 * Very small in-memory user directory used for login and profile updates.
 * In a production system this would be backed by a database.
 */
public class UserStore {

    private final Map<String, String> credentials = new HashMap<>(); // username -> password
    private final Map<String, String> displayNames = new HashMap<>(); // username -> display name

    public UserStore() {
        credentials.put("student", "pass123");
        displayNames.put("student", "student");

        credentials.put("alice", "alice123");
        displayNames.put("alice", "Alice");
    }

    public boolean authenticate(String username, String password) {
        return credentials.containsKey(username) && credentials.get(username).equals(password);
    }

    public String getDisplayName(String username) {
        return displayNames.getOrDefault(username, username);
    }

    public void updateProfile(String username, String newDisplayName, String newPassword) {
        if (newDisplayName != null && !newDisplayName.isBlank()) {
            displayNames.put(username, newDisplayName);
        }
        if (newPassword != null && !newPassword.isBlank()) {
            credentials.put(username, newPassword);
        }
    }
}
