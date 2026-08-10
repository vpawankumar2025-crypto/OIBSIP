import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Login screen. On success, opens the main application window.
 * Demo credentials: admin / admin123
 */
public class LoginForm extends JFrame {

    private final JTextField usernameField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);

    public LoginForm() {
        setTitle("Train Reservation System - Login");
        setSize(380, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Train Reservation Login", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        JLabel hint = new JLabel("(demo login: admin / admin123)", SwingConstants.CENTER);
        hint.setFont(new Font("SansSerif", Font.ITALIC, 11));
        gbc.gridy = 4;
        panel.add(hint, gbc);

        add(panel);

        loginButton.addActionListener(e -> attemptLogin());
        passwordField.addActionListener(e -> attemptLogin());
    }

    private void attemptLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, password);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(this, "Login successful!");
                        new MainDashboard(username).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Access Denied: Invalid username or password.",
                                "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
