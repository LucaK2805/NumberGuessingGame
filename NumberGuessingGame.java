import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class NumberGuessingGame {
    private static int targetNumber;
    private static int attemptsLeft;
    private static int lowerBound;
    private static int upperBound;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SetupFrame().setVisible(true));
    }

    static class SetupFrame extends JFrame {
        private JTextField lowerBoundField;
        private JTextField upperBoundField;
        private JTextField attemptsField;

        public SetupFrame() {
            setTitle("Setup Number Guessing Game");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            panel.setBackground(Color.DARK_GRAY);
            panel.setLayout(null);
            add(panel);

            JLabel lowerBoundLabel = new JLabel("Lower Bound:");
            lowerBoundLabel.setBounds(10, 20, 120, 25);
            lowerBoundLabel.setForeground(Color.WHITE);
            panel.add(lowerBoundLabel);

            lowerBoundField = new JTextField(20);
            lowerBoundField.setBounds(150, 20, 200, 25);
            panel.add(lowerBoundField);

            JLabel upperBoundLabel = new JLabel("Upper Bound:");
            upperBoundLabel.setBounds(10, 60, 120, 25);
            upperBoundLabel.setForeground(Color.WHITE);
            panel.add(upperBoundLabel);

            upperBoundField = new JTextField(20);
            upperBoundField.setBounds(150, 60, 200, 25);
            panel.add(upperBoundField);

            JLabel attemptsLabel = new JLabel("Attempts:");
            attemptsLabel.setBounds(10, 100, 120, 25);
            attemptsLabel.setForeground(Color.WHITE);
            panel.add(attemptsLabel);

            attemptsField = new JTextField(20);
            attemptsField.setBounds(150, 100, 200, 25);
            panel.add(attemptsField);

            JButton startButton = new JButton("Start Game");
            startButton.setBounds(150, 150, 100, 25);
            startButton.setBackground(Color.GRAY);
            startButton.setForeground(Color.WHITE);
            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startGame();
                }
            });
            panel.add(startButton);

            // Pressing Enter to start the game
            panel.getRootPane().setDefaultButton(startButton);
        }

        private void startGame() {
            lowerBound = Integer.parseInt(lowerBoundField.getText());
            upperBound = Integer.parseInt(upperBoundField.getText());
            attemptsLeft = Integer.parseInt(attemptsField.getText());

            targetNumber = new Random().nextInt(upperBound - lowerBound + 1) + lowerBound;
            new GameFrame().setVisible(true);
            dispose();
        }
    }

    static class GameFrame extends JFrame {
        private JTextField guessField;
        private JLabel feedbackLabel;
        private JLabel attemptsLabel;
        private JButton guessButton;
        private JButton resetButton;

        public GameFrame() {
            setTitle("Number Guessing Game");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            panel.setBackground(Color.DARK_GRAY);
            panel.setLayout(null);
            add(panel);

            JLabel guessLabel = new JLabel("Enter your guess:");
            guessLabel.setBounds(10, 20, 150, 25);
            guessLabel.setForeground(Color.WHITE);
            panel.add(guessLabel);

            guessField = new JTextField(20);
            guessField.setBounds(170, 20, 200, 25);
            panel.add(guessField);

            guessField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        processGuess();
                    }
                }
            });

            feedbackLabel = new JLabel("");
            feedbackLabel.setBounds(10, 60, 360, 25);
            feedbackLabel.setForeground(Color.WHITE);
            panel.add(feedbackLabel);

            attemptsLabel = new JLabel("Attempts left: " + attemptsLeft);
            attemptsLabel.setBounds(10, 100, 360, 25);
            attemptsLabel.setForeground(Color.WHITE);
            panel.add(attemptsLabel);

            guessButton = new JButton("Guess");
            guessButton.setBounds(10, 150, 80, 25);
            guessButton.setBackground(Color.GRAY);
            guessButton.setForeground(Color.WHITE);
            guessButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    processGuess();
                }
            });
            panel.add(guessButton);

            resetButton = new JButton("Reset");
            resetButton.setBounds(100, 150, 80, 25);
            resetButton.setBackground(Color.GRAY);
            resetButton.setForeground(Color.WHITE);
            resetButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    resetGame();
                }
            });
            panel.add(resetButton);
        }

        private void processGuess() {
            int guess = Integer.parseInt(guessField.getText());
            attemptsLeft--;

            if (guess == targetNumber) {
                feedbackLabel.setText("Correct! You guessed the number.");
                guessButton.setEnabled(false);
            } else if (attemptsLeft <= 0) {
                feedbackLabel.setText("No attempts left. You lose! The correct number was: " + targetNumber);
                guessButton.setEnabled(false);
            } else if (guess < targetNumber) {
                feedbackLabel.setText("Too low!");
            } else {
                feedbackLabel.setText("Too high!");
            }

            attemptsLabel.setText("Attempts left: " + attemptsLeft);
            guessField.setText(""); // Clear the guess field
        }

        private void resetGame() {
            new SetupFrame().setVisible(true);
            dispose();
        }
    }
}
