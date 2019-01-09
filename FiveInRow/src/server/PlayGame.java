package server;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PlayGame
 */
@WebServlet(asyncSupported = true, description = "Five in the row Play Excisting Game", urlPatterns = { "/PlayGame" })
public class PlayGame extends HttpServlet {
	private static final long serialVersionUID = 131245234L;
    private Game game;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlayGame() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			UUID gameId = UUID.fromString(request.getParameter("gameId"));
			game = Util.play(gameId);
			String command = request.getParameter("command");
			int cmd;
			if(command != null) {
				cmd = Integer.valueOf(command);
				if(cmd == 0) endGame(gameId);
			}	
			UUID playerId = UUID.fromString(request.getParameter("playerId"));
			if(game.getPlayers().get(game.getCurrPlayer()).getUuid() != playerId) {
				// not your turn!
			}
		} catch(Exception nullException) {
			response.getWriter().append("Other player disconected / Game doesn't exists.");
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	private void endGame(UUID gameId) {
		Util.endGame(gameId);
	}
	
}
