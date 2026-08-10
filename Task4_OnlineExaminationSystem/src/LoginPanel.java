import javax.swing.*;
import java.awt.*;

/**
 * Login screen: username + password. On success, hands control to the app.
 */
public class LoginPanel extends JPanel {

    private final JTextField usernameField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);

    public LoginPanel(ExamApp app, UserStore userStore) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        JLabel title = new JLabel("Online Examination System", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridy = 2; gbc.gridx = 0;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        add(loginButton, gbc);

        JLabel hint = new JLabel("(demo login: student / pass123)");
        hint.setFont(new Font("SansSerif", Font.ITALIC, 11));
        gbc.gridy = 4;
        add(hint, gbc);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both username and password.",
                        "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (userStore.authenticate(username, password)) {
                app.onLoginSuccess(username);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.",
                        "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /** Clears fields, e.g. after logout. */
    public void reset() {
        usernameField.setText("");
        passwordField.setText("");
    }
}
