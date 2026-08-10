import javax.swing.*;
import java.awt.*;
import java.sql.*;

/**
 * Cancellation form: user enters a PNR, fetches the booking details,
 * and can confirm cancellation (which deletes the record from the DB).
 */
public class CancellationForm extends JFrame {

    private final JTextField pnrField = new JTextField(15);
    private final JTextArea detailsArea = new JTextArea(8, 30);
    private String fetchedPnr = null;

    public CancellationForm() {
        setTitle("Cancel a Booking");
        setSize(430, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("PNR Number:"));
        topPanel.add(pnrField);
        JButton fetchButton = new JButton("Fetch");
        topPanel.add(fetchButton);

        detailsArea.setEditable(false);
        detailsArea.setBorder(BorderFactory.createTitledBorder("Booking Details"));

        JButton cancelButton = new JButton("Confirm Cancellation");
        cancelButton.setEnabled(false);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(cancelButton);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(detailsArea), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        fetchButton.addActionListener(e -> {
            boolean found = fetchBooking();
            cancelButton.setEnabled(found);
        });

        cancelButton.addActionListener(e -> confirmCancellation(cancelButton));
    }

    /** @return true if a booking was found and displayed. */
    private boolean fetchBooking() {
        String pnr = pnrField.getText().trim();
        if (pnr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a PNR number.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String sql = "SELECT * FROM reservations WHERE pnr = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pnr);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    fetchedPnr = pnr;
                    detailsArea.setText(
                            "PNR: " + rs.getString("pnr") + "\n" +
                            "Passenger: " + rs.getString("passenger_name") + "\n" +
                            "Train: " + rs.getString("train_number") + " - " + rs.getString("train_name") + "\n" +
                            "Class: " + rs.getString("class_type") + "\n" +
                            "Date: " + rs.getString("date_of_journey") + "\n" +
                            "From: " + rs.getString("source_station") + "\n" +
                            "To: " + rs.getString("destination_station"));
                    return true;
                } else {
                    detailsArea.setText("");
                    fetchedPnr = null;
                    JOptionPane.showMessageDialog(this, "No booking found for PNR: " + pnr, "Not Found", JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void confirmCancellation(JButton cancelButton) {
        if (fetchedPnr == null) return;

        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to cancel booking " + fetchedPnr + "?",
                "Confirm Cancellation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (choice != JOptionPane.YES_OPTION) return;

        String sql = "DELETE FROM reservations WHERE pnr = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fetchedPnr);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Booking " + fetchedPnr + " has been cancelled.",
                        "Cancelled", JOptionPane.INFORMATION_MESSAGE);
                detailsArea.setText("");
                pnrField.setText("");
                fetchedPnr = null;
                cancelButton.setEnabled(false);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
