package tictactoe;

public class Game {
    private char[][] board;
    private char currentPlayer;
    private boolean gameOver;

    public Game() {
        board = new char[3][3];
        currentPlayer = 'X';
        gameOver = false;
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = '-';
    }

    public String makeMove(int row, int col) {
        if (gameOver) return "Game is over.";
        if (row < 0 || row > 2 || col < 0 || col > 2)
            return "Invalid move.";
        if (board[row][col] != '-')
            return "Cell already occupied.";

        board[row][col] = currentPlayer;

        if (checkWinner()) {
            gameOver = true;
            return "Player " + currentPlayer + " wins!";
        }

        if (isBoardFull()) {
            gameOver = true;
            return "It's a draw!";
        }

        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        return "Move accepted. Next player: " + currentPlayer;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == '-') return false;
        return true;
    }

    private boolean checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != '-' && board[i][0] == board[i][1] && board[i][1] == board[i][2])
                return true;
            if (board[0][i] != '-' && board[0][i] == board[1][i] && board[1][i] == board[2][i])
                return true;
        }

        if (board[0][0] != '-' && board[0][0] == board[1][1] && board[1][1] == board[2][2])
            return true;
        if (board[0][2] != '-' && board[0][2] == board[1][1] && board[1][1] == board[2][0])
            return true;

        return false;
    }

    public String getBoardAsString() {
        StringBuilder sb = new StringBuilder();
        for (char[] row : board) {
            for (char c : row) sb.append(c).append(' ');
            sb.append('\n');
        }
        return sb.toString();
    }

    public String reset() {
        initializeBoard();
        gameOver = false;
        currentPlayer = 'X';
        return "Game reset.";
    }
}
