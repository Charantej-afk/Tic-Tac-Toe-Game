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
                    resetBoard(); // reset for new game
                } else if (isBoardFull()) {
                    message = "It's a draw!";
                    resetBoard(); // reset for new game
                } else {
                    switchPlayer();
                }
            } else {
                message = "Invalid move! Try again.";
            }
        }

        // Generate HTML
        out.println("<html><head><title>Tic Tac Toe</title></head>");
        out.println("<body style='text-align:center; font-family:Arial;'>");
        out.println("<h1>Tic Tac Toe Game 🎮</h1>");

        if (!message.isEmpty()) {
            out.println("<h2>" + message + "</h2>");
        }

        // Show board with clickable links
        out.println("<table style='margin:auto; border-collapse: collapse;'>");
        for (int i = 0; i < 3; i++) {
            out.println("<tr>");
            for (int j = 0; j < 3; j++) {
                out.println("<td style='border:1px solid black; width:50px; height:50px; text-align:center;'>");
                if (board[i][j] == '-') {
                    out.println("<a href='?row=" + i + "&col=" + j + "' style='text-decoration:none; font-size:24px;'>-</a>");
                } else {
                    out.println("<span style='font-size:24px;'>" + board[i][j] + "</span>");
                }
                out.println("</td>");
            }
            out.println("</tr>");
        }
        out.println("</table>");

        out.println("<p>Current Player: " + currentPlayer + "</p>");
        out.println("</body></html>");

        out.close();
    }
}
