/**
 * Represents a single multiple-choice question.
 */
public class Question {

    private final String text;
    private final String[] options; // exactly 4 options
    private final int correctIndex; // 0-based index into options

    public Question(String text, String[] options, int correctIndex) {
        if (options.length != 4) {
            throw new IllegalArgumentException("Each question must have exactly 4 options.");
        }
        this.text = text;
        this.options = options;
        this.correctIndex = correctIndex;
    }

    public String getText() {
        return text;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }

    public String getCorrectAnswerText() {
        return options[correctIndex];
    }
}
