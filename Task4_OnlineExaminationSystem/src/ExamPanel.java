import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Displays one MCQ at a time with Next/Previous navigation, a live
 * countdown timer that auto-submits at zero, and a manual submit button.
 */
public class ExamPanel extends JPanel {

    private static final int EXAM_DURATION_SECONDS = 30 * 60; // 30 minutes

    private final ExamApp app;
    private final List<Question> questions;
    private final Integer[] selectedAnswers; // index of selected option per question, or null

    private int currentIndex = 0;
    private int remainingSeconds;
    private javax.swing.Timer countdownTimer;
    private long examStartMillis;

    private final JLabel timerLabel = new JLabel();
    private final JLabel questionLabel = new JLabel();
    private final JLabel questionNumberLabel = new JLabel();
    private final ButtonGroup optionGroup = new ButtonGroup();
    private final JRadioButton[] optionButtons = new JRadioButton[4];

    private final JButton prevButton = new JButton("Previous");
    private final JButton nextButton = new JButton("Next");
    private final JButton submitButton = new JButton("Submit Exam");

    public ExamPanel(ExamApp app, List<Question> questions) {
        this.app = app;
        this.questions = questions;
        this.selectedAnswers = new Integer[questions.size()];

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // --- Top: timer + question number ---
        JPanel topPanel = new JPanel(new BorderLayout());
        timerLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        timerLabel.setForeground(new Color(180, 0, 0));
        questionNumberLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        topPanel.add(questionNumberLabel, BorderLayout.WEST);
        topPanel.add(timerLabel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // --- Center: question + options ---
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        questionLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        centerPanel.add(questionLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionGroup.add(optionButtons[i]);
            centerPanel.add(optionButtons[i]);
            centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            final int optionIndex = i;
            optionButtons[i].addActionListener(e -> selectedAnswers[currentIndex] = optionIndex);
        }
        add(centerPanel, BorderLayout.CENTER);

        // --- Bottom: navigation ---
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(prevButton);
        bottomPanel.add(nextButton);
        bottomPanel.add(submitButton);
        add(bottomPanel, BorderLayout.SOUTH);

        prevButton.addActionListener(e -> { saveNothingJustNavigate(); currentIndex--; displayQuestion(); });
        nextButton.addActionListener(e -> { currentIndex++; displayQuestion(); });
        submitButton.addActionListener(e -> confirmManualSubmit());
    }

    private void saveNothingJustNavigate() {
        // Selection is already saved via the radio button listener; nothing extra needed.
    }

    /** Resets exam state and starts the countdown. Call each time an exam session begins. */
    public void startExam() {
        currentIndex = 0;
        for (int i = 0; i < selectedAnswers.length; i++) selectedAnswers[i] = null;
        remainingSeconds = EXAM_DURATION_SECONDS;
        examStartMillis = System.currentTimeMillis();
        app.setExamInProgress(true);
        displayQuestion();
        updateTimerLabel();

        if (countdownTimer != null) countdownTimer.stop();
        countdownTimer = new javax.swing.Timer(1000, e -> {
            remainingSeconds--;
            updateTimerLabel();
            if (remainingSeconds <= 0) {
                countdownTimer.stop();
                JOptionPane.showMessageDialog(this, "Time's up! Your exam is being submitted automatically.",
                        "Time Expired", JOptionPane.WARNING_MESSAGE);
                finishExam();
            }
        });
        countdownTimer.start();
    }

    private void updateTimerLabel() {
        int mins = Math.max(remainingSeconds, 0) / 60;
        int secs = Math.max(remainingSeconds, 0) % 60;
        timerLabel.setText(String.format("Time Left: %02d:%02d", mins, secs));
    }

    private void displayQuestion() {
        Question q = questions.get(currentIndex);
        questionNumberLabel.setText("Question " + (currentIndex + 1) + " of " + questions.size());
        questionLabel.setText("<html><body style='width:400px'>" + q.getText() + "</body></html>");

        String[] options = q.getOptions();
        optionGroup.clearSelection();
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(options[i]);
        }
        if (selectedAnswers[currentIndex] != null) {
            optionButtons[selectedAnswers[currentIndex]].setSelected(true);
        }

        prevButton.setEnabled(currentIndex > 0);
        nextButton.setEnabled(currentIndex < questions.size() - 1);
    }

    private void confirmManualSubmit() {
        long unanswered = 0;
        for (Integer a : selectedAnswers) if (a == null) unanswered++;

        String msg = unanswered > 0
                ? "You have " + unanswered + " unanswered question(s). Submit anyway?"
                : "Are you sure you want to submit the exam?";
        int choice = JOptionPane.showConfirmDialog(this, msg, "Confirm Submit",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            if (countdownTimer != null) countdownTimer.stop();
            finishExam();
        }
    }

    private void finishExam() {
        int score = 0;
        List<String> breakdown = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            Integer selected = selectedAnswers[i];
            boolean correct = selected != null && selected == q.getCorrectIndex();
            if (correct) score++;
            String selectedText = selected == null ? "(No answer)" : q.getOptions()[selected];
            breakdown.add(String.format("Q%d: %s | Your answer: %s | Correct answer: %s [%s]",
                    i + 1, q.getText(), selectedText, q.getCorrectAnswerText(),
                    correct ? "CORRECT" : "INCORRECT"));
        }
        long timeTakenSeconds = (System.currentTimeMillis() - examStartMillis) / 1000;
        app.onExamFinished(score, questions.size(), timeTakenSeconds, breakdown);
    }
}
