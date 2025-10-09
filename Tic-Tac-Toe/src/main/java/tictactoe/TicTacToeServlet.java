package tictactoe;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class TicTacToeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Game game;

    @Override
    public void init() throws ServletException {
        game = new Game();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");

        if (action == null) {
            out.println("Usage:");
            out.println("1. ?action=move&row=0&col=1");
            out.println("2. ?action=board");
            out.println("3. ?action=reset");
            return;
        }

        switch (action) {
            case "move":
                try {
                    int row = Integer.parseInt(request.getParameter("row"));
                    int col = Integer.parseInt(request.getParameter("col"));
                    out.println(game.makeMove(row, col));
                } catch (Exception e) {
                    out.println("Invalid parameters. Example: ?action=move&row=1&col=2");
                }
                break;

            case "board":
                out.println(game.getBoardAsString());
                break;

            case "reset":
                out.println(game.reset());
                break;

            default:
                out.println("Unknown action.");
        }
    }
}
