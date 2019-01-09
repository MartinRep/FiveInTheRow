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
@WebServlet(asyncSupported = true, description = "Five in the row Play Excisting Game", urlPatterns = { "/play" })
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
		try {
			UUID gameId = UUID.fromString(request.getParameter("gameId"));
			Game game = Util.findGame(gameId);
			UUID playerId = UUID.fromString(request.getParameter("playerId"));
			int command = Integer.valueOf(request.getParameter("command"));
			command = Integer.valueOf(command);
			if(command == 255) endGame(gameId);	// One of the players disconnected
			if(game.getPlayers().get(game.getCurrPlayer()).getUuid().equals(playerId)) {		// Checks if it is players turn
				if(game.getWinner() != 255) {
					// Initial check if other player didn't win already
				}
				if(!game.insertDisk(command)) {
					response.addHeader("ERROR", "Ilegal move");
				}
				if(game.getWinner() != 255) {
					// Check if latest move didn't resulted in winning condition
				}
				
				response.getWriter().append(game.getBoard());
			} else {
				response.addHeader("ERROR", "Not your turn!");
			}
		} catch(Exception nullException) {
			response.addHeader("ERROR", "Other player disconected / Game doesn't exists.");
		}
	}
	
	private void endGame(UUID gameId) {
		Util.endGame(gameId);
	}
	
}
