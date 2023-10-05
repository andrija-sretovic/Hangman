import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Hangman implements ActionListener {

    private JFrame frame = new JFrame("Movie Guessing Game");
    private JPanel mainPanel = new JPanel(new BorderLayout());
    private JPanel topRowPanel = new JPanel(new FlowLayout());
    private JTextField textBox = new JTextField(1);
    private JButton guessButton = new JButton("Guess");
    private JPanel centralPanel = new JPanel(new BorderLayout());
    private JLabel guessLabel = new JLabel("");
    private JLabel errorLabel = new JLabel("***");
    private ImageIcon hangImage = new ImageIcon("0.jpg");
    private JLabel imageLabel = new JLabel(hangImage);
    private JLabel wrongLettersLabel = new JLabel("Letter not present: ");
    private ImageIcon[] hangImages = {
            new ImageIcon("1.jpg"), new ImageIcon("3.jpg"),
            new ImageIcon("4.jpg"), new ImageIcon("5.jpg"),
            new ImageIcon("6.jpg"), new ImageIcon("7.jpg"),
            new ImageIcon("8.jpg"), new ImageIcon("9.jpg"),
            new ImageIcon("10.jpg")
    };

    private String randomMovie;
    private String underscore;
    private int hangOrder = 0;
    private ArrayList<Character> letterMissed = new ArrayList<>();

    public Hangman() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);

        guessButton.addActionListener(this);

        topRowPanel.add(textBox);
        topRowPanel.add(guessButton);

        guessLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);

        guessLabel.setPreferredSize(new Dimension(400, 40));
        guessLabel.setFont(new Font("Ariel", Font.PLAIN, 20));
        guessLabel.setBackground(Color.YELLOW);
        guessLabel.setOpaque(true);

        wrongLettersLabel.setHorizontalAlignment(SwingConstants.CENTER);
        wrongLettersLabel.setFont(new Font("Ariel", Font.BOLD, 15));

        centralPanel.add(errorLabel, BorderLayout.NORTH);
        centralPanel.add(guessLabel, BorderLayout.CENTER);
        centralPanel.add(wrongLettersLabel, BorderLayout.SOUTH);

        mainPanel.add(topRowPanel, BorderLayout.NORTH);
        mainPanel.add(centralPanel, BorderLayout.CENTER);
        mainPanel.add(imageLabel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Movies movies = new Movies();

        ArrayList <String> movieDatabase = new ArrayList<>();

        movieDatabase.add("Shawshank Redemption");
        movieDatabase.add("The Godfather");
        movieDatabase.add("Forrest Gump");
        movieDatabase.add("Twelve Angry Men");
        movieDatabase.add("Schindler´s List");
        movieDatabase.add("The Dark Knight");
        movieDatabase.add("The Lord of the Rings: The Return of the King");
        movieDatabase.add("Pulp Fiction");
        movieDatabase.add("The Good, the Bad and the Ugly");
        movieDatabase.add("Fight Club");

        randomMovie = movies.getRandomMovie(movieDatabase);
        underscore = movies.movieToUnderscore(randomMovie);

        guessLabel.setText(underscore);
}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guessButton) {
            if (textBox.getText().isEmpty()) {
                errorLabel.setText("Please enter a letter");
                textBox.requestFocus();
            }
            else if (Character.isDigit(textBox.getText().charAt(0))) {
                errorLabel.setText("This is a number, please enter a letter");
                textBox.requestFocus();
            }
            else if (Character.isLetter(textBox.getText().charAt(0))) {
                letterPresent();
                winCondition();
                loseCondition();
                textBox.requestFocus();
            }
        }
    }
    public void letterPresent() {
        errorLabel.setText("***");
        boolean hasLetter = false;
        String current = guessLabel.getText();
        StringBuilder temporary = new StringBuilder();
        StringBuilder missingLetters = new StringBuilder(wrongLettersLabel.getText());
        char letter = textBox.getText().charAt(0);

        for (int i = 0; i < randomMovie.length(); i++) {
            if (randomMovie.toLowerCase().charAt(i) == letter) {
                temporary.append(randomMovie.charAt(i));
                hasLetter = true;
            } else {
                temporary.append(current.charAt(i));
            }
        }
        if(!hasLetter && !hasChar(letter, letterMissed)) {
            letterMissed.add(letter);
            missingLetters.append(' ');
            missingLetters.append(textBox.getText().toUpperCase());
            imageLabel.setIcon(hangImages[hangOrder]);
            hangOrder++;
        }
        guessLabel.setText(temporary.toString());
        textBox.setText("");
        wrongLettersLabel.setText(missingLetters.toString());
    }
    public void winCondition() {
        if (guessLabel.getText().equals(randomMovie)) {
            wrongLettersLabel.setText("WELL DONE!!!");
            textBox.setEnabled(false);
            guessButton.setEnabled(false);
        }
    }
    public void loseCondition() {
        if (hangOrder == hangImages.length) {
            guessLabel.setText(randomMovie);
            wrongLettersLabel.setText("Better luck next time");
            textBox.setEnabled(false);
            guessButton.setEnabled(false);

        }
    }
    public boolean hasChar (char c, ArrayList<Character> arrayList) {
        for (char ch : arrayList) {
            if (ch == c) {
                return true;
            }
        }
        return false;
    }
}