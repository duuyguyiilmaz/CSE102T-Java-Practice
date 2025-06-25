import java.util.*;

public class Lab07 {
    public static void main(String args[]) {
        QuizManager quiz = new QuizManager(5);
        quiz.addQuestion(new TrueFalseQuestion(1, "Java is platform-independent?", true, 5));
        quiz.addQuestion(new MultipleChoiceQuestion(2, "Which one is not a Java keyword?",
                new String[] { "class", "interface", "goto", "define" }, 4, 10));
        quiz.addQuestion(new ShortAnswerQuestion(3, "What does JVM stand for?", "Java Virtual Machine", 10));

        quiz.displayAllQuestion();
        quiz.answerQuestion(1, "true");
        quiz.answerQuestion(2, "4");
        quiz.answerQuestion(3, "java virtual machine");
        quiz.showScore();

    }
}

interface Displayable {
    void displayQuestion();
}

interface Answerable {
    boolean checkAnswer(String userAnswer);
}

abstract class Question<T> implements Displayable, Answerable {
    protected int id;
    protected String questionText;
    protected T correctAnswer;
    protected int points;

    public Question(int id, String questionText, T correctAnswer, int points) {
        this.id = id;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public int getPoints() {
        return points;
    }

    protected abstract T parseAnswer(String userAnswer);

    @Override
    public boolean checkAnswer(String userAnswer) {
        T parsed = parseAnswer(userAnswer);
        return correctAnswer.equals(parsed);
    }
}

class TrueFalseQuestion extends Question<Boolean> {
    public TrueFalseQuestion(int id, String text, Boolean correctAnswer, int points) {
        super(id, text, correctAnswer, points);
    }

    @Override
    protected Boolean parseAnswer(String userAnswer) {
        return Boolean.parseBoolean(userAnswer.trim().toLowerCase());
    }

    @Override
    public void displayQuestion() {
        System.out.println("[" + id + "] (" + points + " pts) " + questionText + " (true/false)");
    }

}

class MultipleChoiceQuestion extends Question<Integer> {
    private String[] options;

    public MultipleChoiceQuestion(int id, String text, String[] options, int correctOptionNumber, int points) {
        super(id, text, correctOptionNumber, points);
        this.options = options;

    }

    @Override
    public Integer parseAnswer(String userAnswer) {
        return Integer.parseInt(userAnswer.trim());
    }

    @Override
    public void displayQuestion() {
        System.out.println("[" + id + "] (" + points + " pts) " + questionText);
        for (int i = 0; i < options.length; i++) {
            System.out.println(" " + (i + 1) + " )" + options[i]);
        }
    }
}

class ShortAnswerQuestion extends Question<String> {
    public ShortAnswerQuestion(int id, String text, String correctAnswer, int points) {
        super(id, text, correctAnswer.trim().toLowerCase(), points);
    }

    @Override
    protected String parseAnswer(String userAnswer) {
        return userAnswer.trim().toLowerCase();
    }

    @Override
    public void displayQuestion() {
        System.out.println("[" + id + "] (" + points + " pts) " + questionText + " (short answer)");
    }
}

class QuizManager {
    private Question<?>[] questions;
    private int count;
    private int totalScore;

    public QuizManager(int capacity) {
        questions = new Question<?>[capacity];
        count = 0;
        totalScore = 0;
    }

    public void addQuestion(Question<?> q) {
        if (count < questions.length) {
            questions[count++] = q;
        } else {
            System.out.println("Quiz is full! Cannot add more questions.");
        }
    }

    public void removeQuestionById(int id) {
        for (int i = 0; i < count; i++) {
            if (questions[i].getId() == id) {
                for (int j = i; j < count - 1; j++) {
                    questions[j] = questions[j + 1];
                }
                questions[--count] = null;
                System.out.println("Question removed.");
                return;
            }
        }
        System.out.println("Question not found.");

    }

    public void displayAllQuestion() {
        for (int i = 0; i < count; i++) {
            questions[i].displayQuestion();
            System.out.println();
        }
    }

    public void answerQuestion(int id, String userAnswer) {
        for (int i = 0; i < count; i++) {
            if (questions[i].getId() == id) {
                boolean correct = questions[i].checkAnswer(userAnswer);
                if (correct) {
                    totalScore += questions[i].getPoints();
                    System.out.println("Correct! +" + questions[i].getPoints() + " points.");
                } else {
                    System.out.println("Incorrect.");
                }
                return;
            }
        }
        System.out.println("Question not found.");

    }

    public void showScore() {
        int maxScore = 0;
        for (int i = 0; i < count; i++) {
            maxScore += questions[i].getPoints();
        }
        System.out.println("Score: " + totalScore + " / " + maxScore);

    }
}