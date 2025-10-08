package tictactoe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/tic-tac-toe")
public class TicTacToeServlet extends HttpServlet {
    private Game game = new Game();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html><body>");
        out.println("<h2>Tic Tac Toe</h2>");
        out.println(game.getBoardHtml());
        out.println("<form method='post'>");
        out.println("Row: <input name='row' type='number' min='1' max='3' required />");
        out.println("Col: <input name='col' type='number' min='1' max='3' required />");
        out.println("<input type='submit' value='Move'/>");
        out.println("</form>");
        if (game.getWinner() != null) {
            out.println("<h3>Winner: " + game.getWinner() + "</h3>");
            out.println("<form method='post'><input type='hidden' name='reset' value='1'/>");
            out.println("<input type='submit' value='Restart' /></form>");
        } else if (game.isDraw()) {
            out.println("<h3>It's a draw!</h3>");
            out.println("<form method='post'><input type='hidden' name='reset' value='1'/>");
            out.println("<input type='submit' value='Restart' /></form>");
        }
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (req.getParameter("reset") != null) {
            game = new Game();
        } else {
            try {
                int row = Integer.parseInt(req.getParameter("row")) - 1;
                int col = Integer.parseInt(req.getParameter("col")) - 1;
                game.playMove(row, col);
            } catch (Exception ignored) {}
        }
        resp.sendRedirect("tic-tac-toe");
    }
}