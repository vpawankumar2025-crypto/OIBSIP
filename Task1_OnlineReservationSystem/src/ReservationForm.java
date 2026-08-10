import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

/**
 * Booking form: collects passenger and journey details, validates them,
 * inserts the reservation into the database, and shows a confirmation
 * dialog with the generated PNR.
 */
public class ReservationForm extends JFrame {

    private final JTextField passengerNameField = new JTextField(18);
    private final JTextField trainNumberField = new JTextField(18);
    private final JTextField trainNameField = new JTextField(18);
    private final JComboBox<String> classTypeBox = new JComboBox<>(new String[]{"Sleeper", "AC 3-Tier", "AC 2-Tier", "AC First Class", "General"});
    private final JTextField dateField = new JTextField(18);
    private final JTextField sourceField = new JTextField(18);
    private final JTextField destinationField = new JTextField(18);

    public ReservationForm() {
        setTitle("Book a Ticket");
        setSize(450, 430);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        addRow(panel, gbc, row++, "Passenger Name:", passengerNameField);
        addRow(panel, gbc, row++, "Train Number:", trainNumberField);
        addRow(panel, gbc, row++, "Train Name:", trainNameField);
        addRow(panel, gbc, row++, "Class Type:", classTypeBox);
        addRow(panel, gbc, row++, "Date (yyyy-MM-dd):", dateField);
        addRow(panel, gbc, row++, "Source Station:", sourceField);
        addRow(panel, gbc, row++, "Destination Station:", destinationField);

        JButton lookupButton = new JButton("Auto-fill Train Name");
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        panel.add(lookupButton, gbc);
        row++;

        JButton bookButton = new JButton("Book Ticket");
        gbc.gridy = row;
        panel.add(bookButton, gbc);

        add(panel);

        lookupButton.addActionListener(e -> autoFillTrainName());
        bookButton.addActionListener(e -> bookTicket());
    }

    private void addRow(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    /** Looks up the train name for the entered train number and fills it in. */
    private void autoFillTrainName() {
        String trainNumber = trainNumberField.getText().trim();
        if (trainNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a train number first.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT train_name FROM trains WHERE train_number = ?")) {
            ps.setString(1, trainNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    trainNameField.setText(rs.getString("train_name"));
                } else {
                    JOptionPane.showMessageDialog(this, "No train found with that number. You may enter the name manually.",
                            "Not Found", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void bookTicket() {
        String passengerName = passengerNameField.getText().trim();
        String trainNumber = trainNumberField.getText().trim();
        String trainName = trainNameField.getText().trim();
        String classType = (String) classTypeBox.getSelectedItem();
        String date = dateField.getText().trim();
        String source = sourceField.getText().trim();
        String destination = destinationField.getText().trim();

        // --- Validation ---
        if (passengerName.isEmpty() || trainNumber.isEmpty() || trainName.isEmpty()
                || date.isEmpty() || source.isEmpty() || destination.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!trainNumber.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Train number must be numeric.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(date, fmt);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Date must be in yyyy-MM-dd format.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String pnr = generatePNR();

        String sql = "INSERT INTO reservations(pnr, passenger_name, train_number, train_name, class_type, " +
                "date_of_journey, source_station, destination_station) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pnr);
            ps.setString(2, passengerName);
            ps.setString(3, trainNumber);
            ps.setString(4, trainName);
            ps.setString(5, classType);
            ps.setString(6, date);
            ps.setString(7, source);
            ps.setString(8, destination);
            ps.executeUpdate();

            String confirmation = "Booking Confirmed!\n\n" +
                    "PNR: " + pnr + "\n" +
                    "Passenger: " + passengerName + "\n" +
                    "Train: " + trainNumber + " - " + trainName + "\n" +
                    "Class: " + classType + "\n" +
                    "Date: " + date + "\n" +
                    "From: " + source + "  To: " + destination;
            JOptionPane.showMessageDialog(this, confirmation, "Reservation Successful", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Generates a unique 10-character alphanumeric PNR. */
    private String generatePNR() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
    }
}
