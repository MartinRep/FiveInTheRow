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
		Game game = null;
		try {
			UUID gameId = UUID.fromString(request.getParameter("gameId"));
			game = Util.play(gameId);
			UUID playerId = UUID.fromString(request.getParameter("playerId"));
			int command = Integer.valueOf(request.getParameter("command"));
			command = Integer.valueOf(command);
			if(command == 0) endGame(gameId);
			if(game.getPlayers().get(game.getCurrPlayer()).getUuid() == playerId) {
				Util.play(gameId);
				response.getWriter().append(game.getBoard());
			} else {
				response.sendError(400, "Error! Not your turn!");
			}
		} catch(Exception nullException) {
			response.getWriter().append("Other player disconected / Game doesn't exists.");
		}
	}
	
	private void endGame(UUID gameId) {
		Util.endGame(gameId);
	}
	
}
