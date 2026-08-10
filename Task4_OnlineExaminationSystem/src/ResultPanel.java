import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Displays the final score, time taken, and a breakdown of correct/incorrect
 * answers. Provides a Logout button to return to the login screen.
 */
public class ResultPanel extends JPanel {

    private final JLabel scoreLabel = new JLabel();
    private final JLabel timeLabel = new JLabel();
    private final JTextArea breakdownArea = new JTextArea();

    public ResultPanel(ExamApp app) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        timeLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        topPanel.add(scoreLabel);
        topPanel.add(timeLabel);
        add(topPanel, BorderLayout.NORTH);

        breakdownArea.setEditable(false);
        breakdownArea.setLineWrap(true);
        breakdownArea.setWrapStyleWord(true);
        add(new JScrollPane(breakdownArea), BorderLayout.CENTER);

        JButton logoutButton = new JButton("Logout");
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(logoutButton);
        add(bottomPanel, BorderLayout.SOUTH);

        logoutButton.addActionListener(e -> app.logout());
    }

    public void showResult(int score, int total, long timeTakenSeconds, List<String> breakdown) {
        scoreLabel.setText("Your Score: " + score + " out of " + total);
        long mins = timeTakenSeconds / 60;
        long secs = timeTakenSeconds % 60;
        timeLabel.setText(String.format("Time Taken: %d min %d sec", mins, secs));

        StringBuilder sb = new StringBuilder();
        for (String line : breakdown) {
            sb.append(line).append("\n\n");
        }
        breakdownArea.setText(sb.toString());
        breakdownArea.setCaretPosition(0);
    }
}
