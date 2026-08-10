import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Top-level application frame. Uses CardLayout to switch between
 * Login -> Profile Update -> Exam -> Result screens.
 */
public class ExamApp extends JFrame {

    public static final String CARD_LOGIN = "login";
    public static final String CARD_PROFILE = "profile";
    public static final String CARD_EXAM = "exam";
    public static final String CARD_RESULT = "result";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel();

    private final UserStore userStore = new UserStore();
    private final List<Question> questionBank = buildQuestionBank();

    // Session state carried between panels
    private String currentUsername;
    private boolean examInProgress = false;

    private LoginPanel loginPanel;
    private ProfilePanel profilePanel;
    private ExamPanel examPanel;
    private ResultPanel resultPanel;

    public ExamApp() {
        setTitle("Online Examination System");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        cardPanel.setLayout(cardLayout);

        loginPanel = new LoginPanel(this, userStore);
        profilePanel = new ProfilePanel(this, userStore);
        examPanel = new ExamPanel(this, questionBank);
        resultPanel = new ResultPanel(this);

        cardPanel.add(loginPanel, CARD_LOGIN);
        cardPanel.add(profilePanel, CARD_PROFILE);
        cardPanel.add(examPanel, CARD_EXAM);
        cardPanel.add(resultPanel, CARD_RESULT);

        add(cardPanel);
        showCard(CARD_LOGIN);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (examInProgress) {
                    int choice = JOptionPane.showConfirmDialog(ExamApp.this,
                            "An exam is in progress. Are you sure you want to quit?",
                            "Confirm Exit", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (choice == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                } else {
                    System.exit(0);
                }
            }
        });
    }

    public void showCard(String name) {
        cardLayout.show(cardPanel, name);
    }

    public void onLoginSuccess(String username) {
        this.currentUsername = username;
        profilePanel.refreshFor(username);
        showCard(CARD_PROFILE);
    }

    public void onProfileDone() {
        examPanel.startExam();
        showCard(CARD_EXAM);
    }

    public void setExamInProgress(boolean inProgress) {
        this.examInProgress = inProgress;
    }

    public void onExamFinished(int score, int total, long timeTakenSeconds, List<String> breakdown) {
        setExamInProgress(false);
        resultPanel.showResult(score, total, timeTakenSeconds, breakdown);
        showCard(CARD_RESULT);
    }

    public void logout() {
        currentUsername = null;
        loginPanel.reset();
        showCard(CARD_LOGIN);
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    private List<Question> buildQuestionBank() {
        List<Question> list = new ArrayList<>();
        list.add(new Question("Which keyword is used to inherit a class in Java?",
                new String[]{"implements", "extends", "inherits", "super"}, 1));
        list.add(new Question("Which collection class allows duplicate elements and maintains insertion order?",
                new String[]{"HashSet", "TreeSet", "ArrayList", "HashMap"}, 2));
        list.add(new Question("What is the default value of a boolean instance variable in Java?",
                new String[]{"true", "false", "0", "null"}, 1));
        list.add(new Question("Which keyword prevents a class from being subclassed?",
                new String[]{"static", "final", "private", "abstract"}, 1));
        list.add(new Question("Which operator is used for logical AND in Java?",
                new String[]{"&", "&&", "AND", "|"}, 1));
        list.add(new Question("Which of these is NOT a Java primitive type?",
                new String[]{"int", "boolean", "String", "char"}, 2));
        list.add(new Question("Which method starts thread execution in Java?",
                new String[]{"run()", "start()", "execute()", "init()"}, 1));
        list.add(new Question("What does JVM stand for?",
                new String[]{"Java Virtual Machine", "Java Verified Method", "Java Visual Model", "Joint Virtual Machine"}, 0));
        list.add(new Question("Which layout manager arranges components in a grid of equal-size cells?",
                new String[]{"BorderLayout", "FlowLayout", "GridLayout", "CardLayout"}, 2));
        list.add(new Question("Which SQL clause is used to filter rows before grouping?",
                new String[]{"HAVING", "WHERE", "GROUP BY", "ORDER BY"}, 1));
        return list;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExamApp().setVisible(true));
    }
}
