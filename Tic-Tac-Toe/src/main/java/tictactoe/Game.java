package mypackage;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/play")
public class Game extends HttpServlet {

    private char[][] board = new char[3][3];
    private char currentPlayer = 'X';

    @Override
    public void init() throws ServletException {
        resetBoard();
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
        currentPlayer = 'X';
    }

    private boolean makeMove(int row, int col) {
        if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == '-') {
            board[row][col] = currentPlayer;
            return true;
        }
        return false;
    }

    private boolean checkWin() {
        // rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != '-' && board[i][0] == board[i][1] && board[i][1] == board[i][2])
                return true;
        }
        // cols
        for (int i = 0; i < 3; i++) {
            if (board[0][i] != '-' && board[0][i] == board[1][i] && board[1][i] == board[2][i])
                return true;
        }
        // diagonals
        if (board[0][0] != '-' && board[0][0] == board[1][1] && board[1][1] == board[2][2])
            return true;
        if (board[0][2] != '-' && board[0][2] == board[1][1] && board[1][1] == board[2][0])
            return true;
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == '-')
                    return false;
        return true;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String moveRow = request.getParameter("row");
        String moveCol = request.getParameter("col");

        String message = "";

        if (moveRow != null && moveCol != null) {
            int row = Integer.parseInt(moveRow);
            int col = Integer.parseInt(moveCol);
            if (makeMove(row, col)) {
                if (checkWin()) {
                    message = "Player " + currentPlayer + " wins! 🎉";
                } else if (isBoardFull()) {
                    message = "It's a draw!";
                } else {
                    switchPlayer();
                }
            } else {
                message = "Invalid move! Try again.";
            }
        }

        out.println("<html><head><title>Tic Tac Toe</title></head>");
        out.println("<body style='text-align:center; font-family:Arial;'>");
        out.println("<h1>Tic Tac Toe Game 🎮</h1>");
        out
