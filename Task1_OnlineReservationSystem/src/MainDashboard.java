import javax.swing.*;
import java.awt.*;

/**
 * Simple hub window shown after login, linking to the Reservation
 * and Cancellation forms.
 */
public class MainDashboard extends JFrame {

    public MainDashboard(String username) {
        setTitle("Train Reservation System - Dashboard");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel welcome = new JLabel("Welcome, " + username + "!");
        welcome.setFont(new Font("SansSerif", Font.BOLD, 16));
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton bookButton = new JButton("Book a Ticket");
        JButton cancelButton = new JButton("Cancel a Booking");
        JButton exitButton = new JButton("Exit");

        for (JButton b : new JButton[]{bookButton, cancelButton, exitButton}) {
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            b.setMaximumSize(new Dimension(200, 35));
        }

        panel.add(welcome);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(bookButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(cancelButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(exitButton);

        add(panel);

        bookButton.addActionListener(e -> new ReservationForm().setVisible(true));
        cancelButton.addActionListener(e -> new CancellationForm().setVisible(true));
        exitButton.addActionListener(e -> System.exit(0));
    }
}
