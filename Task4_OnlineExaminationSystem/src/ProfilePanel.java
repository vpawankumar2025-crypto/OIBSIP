import javax.swing.*;
import java.awt.*;

/**
 * Lets the user update their display name and/or password before starting the exam.
 * Both fields are optional — leaving them blank keeps the existing value.
 */
public class ProfilePanel extends JPanel {

    private final JTextField displayNameField = new JTextField(18);
    private final JPasswordField newPasswordField = new JPasswordField(18);
    private String username;

    public ProfilePanel(ExamApp app, UserStore userStore) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        JLabel title = new JLabel("Update Your Profile", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0;
        add(new JLabel("Display Name:"), gbc);
        gbc.gridx = 1;
        add(displayNameField, gbc);

        gbc.gridy = 2; gbc.gridx = 0;
        add(new JLabel("New Password (optional):"), gbc);
        gbc.gridx = 1;
        add(newPasswordField, gbc);

        JButton continueButton = new JButton("Save & Start Exam");
        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        add(continueButton, gbc);

        continueButton.addActionListener(e -> {
            String newDisplayName = displayNameField.getText().trim();
            String newPassword = new String(newPasswordField.getPassword()).trim();
            userStore.updateProfile(username, newDisplayName, newPassword);
            JOptionPane.showMessageDialog(this, "Profile updated. Starting exam...");
            app.onProfileDone();
        });
    }

    /** Called each time a user logs in, to pre-fill the current display name. */
    public void refreshFor(String username) {
        this.username = username;
        displayNameField.setText(username);
        newPasswordField.setText("");
    }
}
