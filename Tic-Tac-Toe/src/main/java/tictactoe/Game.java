package tictactoe;

public class Game {
    private final char[][] board = new char[3][3];
    private char currentPlayer = 'X';
    private Character winner = null;
    private int moves = 0;

    public Game() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = ' ';
    }

    public boolean playMove(int row, int col) {
        if (winner != null || board[row][col] != ' ') return false;
        board[row][col] = currentPlayer;
        moves++;
        if (checkWin(row, col)) {
            winner = currentPlayer;
        } else if (moves < 9) {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
        return true;
    }

    public String getBoardHtml() {
        StringBuilder sb = new StringBuilder("<table border='1' style='font-size:2em'>");
        for (int i = 0; i < 3; i++) {
            sb.append("<tr>");
            for (int j = 0; j < 3; j++) {
                sb.append("<td style='width:40px;height:40px;text-align:center;'>")
                  .append(board[i][j])
                  .append("</td>");
            }
            sb.append("</tr>");
        }
        sb.append("</table>");
        return sb.toString();
    }

    public Character getWinner() {
        return winner;
    }

    public boolean isDraw() {
        return moves == 9 && winner == null;
    }

    private boolean checkWin(int row, int col) {
        char p = currentPlayer;
        return (board[row][0] == p && board[row][1] == p && board[row][2] == p) ||
               (board[0][col] == p && board[1][col] == p && board[2][col] == p) ||
               (row == col && board[0][0] == p && board[1][1] == p && board[2][2] == p) ||
               (row + col == 2 && board[0][2] == p && board[1][1] == p && board[2][0] == p);
    }
}