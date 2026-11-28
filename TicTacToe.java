import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel("X's Turn", JLabel.CENTER);
    JPanel boardPanel = new JPanel(new GridLayout(3, 3, 6, 6));
    JButton restartBtn = new JButton("Restart");
    JButton[][] board = new JButton[3][3];

    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;
    boolean gameOver = false;
    int turns = 0;

    Color bgColor = new Color(30, 34, 45);
    Color boxColor = new Color(48, 54, 70);

    // Default constructor
    TicTacToe() {
        setupGame("Tic-Tac-Toe");
    }

    // Overloaded constructor (takes custom window title)
    TicTacToe(String title) {
        setupGame(title);
    }

    void setupGame(String title) {
        frame.setTitle(title);
        frame.setSize(520, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(bgColor);
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        textLabel.setOpaque(true);
        frame.add(textLabel, BorderLayout.NORTH);

        boardPanel.setBackground(bgColor);
        frame.add(boardPanel, BorderLayout.CENTER);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton btn = new JButton("");
                board[r][c] = btn;
                btn.setBackground(boxColor);
                btn.setForeground(Color.WHITE);
                btn.setFont(new Font("SansSerif", Font.BOLD, 95));
                btn.setFocusable(false);

                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver) return;
                        JButton clicked = (JButton) e.getSource();

                        if (clicked.getText().equals("")) {
                            clicked.setText(currentPlayer);
                            if (currentPlayer.equals(playerX)) {
                                clicked.setForeground(Color.CYAN);
                            } else {
                                clicked.setForeground(Color.PINK);
                            }
                            turns++;
                            checkWinner();

                            if (!gameOver) {
                                currentPlayer = currentPlayer.equals(playerX) ? playerO : playerX;
                                updateStatus(currentPlayer + "'s Turn");
                            }
                        }
                    }
                });

                boardPanel.add(btn);
            }
        }

        restartBtn.setFont(new Font("SansSerif", Font.BOLD, 20));
        restartBtn.setBackground(new Color(65, 105, 225));
        restartBtn.setForeground(Color.WHITE);
        restartBtn.setFocusable(false);
        restartBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetBoard();
            }
        });
        frame.add(restartBtn, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // Method overloading 1: updates top label text only
    void updateStatus(String message) {
        textLabel.setText(message);
    }

    // Method overloading 2: updates top label text and color
    void updateStatus(String message, Color textColor) {
        textLabel.setText(message);
        textLabel.setForeground(textColor);
    }

    void checkWinner() {
        for (int i = 0; i < 3; i++) {
            // Check rows
            if (!board[i][0].getText().equals("") &&
                board[i][0].getText().equals(board[i][1].getText()) &&
                board[i][1].getText().equals(board[i][2].getText())) {
                highlightWin(board[i][0], board[i][1], board[i][2]);
                return;
            }
            // Check columns
            if (!board[0][i].getText().equals("") &&
                board[0][i].getText().equals(board[1][i].getText()) &&
                board[1][i].getText().equals(board[2][i].getText())) {
                highlightWin(board[0][i], board[1][i], board[2][i]);
                return;
            }
        }

        // Check main diagonal
        if (!board[0][0].getText().equals("") &&
            board[0][0].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][2].getText())) {
            highlightWin(board[0][0], board[1][1], board[2][2]);
            return;
        }

        // Check opposite diagonal
        if (!board[0][2].getText().equals("") &&
            board[0][2].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][0].getText())) {
            highlightWin(board[0][2], board[1][1], board[2][0]);
            return;
        }

        // Check tie
        if (turns == 9) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    board[r][c].setForeground(Color.YELLOW);
                }
            }
            updateStatus("Match Tie!", Color.YELLOW);
            gameOver = true;
        }
    }

    void highlightWin(JButton b1, JButton b2, JButton b3) {
        b1.setBackground(Color.GREEN);
        b2.setBackground(Color.GREEN);
        b3.setBackground(Color.GREEN);
        b1.setForeground(Color.BLACK);
        b2.setForeground(Color.BLACK);
        b3.setForeground(Color.BLACK);
        updateStatus(currentPlayer + " Wins!", Color.GREEN);
        gameOver = true;
    }

    void resetBoard() {
        currentPlayer = playerX;
        gameOver = false;
        turns = 0;
        updateStatus("X's Turn", Color.WHITE);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setBackground(boxColor);
                board[r][c].setForeground(Color.WHITE);
            }
        }
    }
}