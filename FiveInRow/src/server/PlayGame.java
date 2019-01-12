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
			UUID playerId = UUID.fromString(request.getParameter("playerId"));
			int command = Integer.valueOf(request.getParameter("command"));	// 0-9 column selection, 255 end of game command
			Game game = Util.findGame(gameId);	// return game or null, if game not found. This will be catch by exception and returns error
			if(!game.isReadyToPlay()) response.addHeader("ERROR", "Waiting for another player");
			else {
				response.addHeader("OTHERPLAYER", game.getOtherPlayer(playerId));	// always return the name of other player
				if(game.getWinner() != 255) {	// Check if other player didn't win already
					response.addHeader("WINNER", game.getOtherPlayer(playerId));
					endGame(gameId);
				}else {
					if(command == 9) endGame(gameId);	// Command for ending the game received
					if(game.getCurrPlayer().getUuid().equals(playerId)) {		// Checks if it is players turn
						if(!game.insertDisk(command)) {	// Insert a disk or return error
							response.addHeader("ERROR", "Ilegal move");
						} else if(game.getWinner() != 255) {	// Check if latest move didn't resulted in winning condition
							response.addHeader("WINNER", game.getCurrPlayer().getName());
						}
					} else {
						response.addHeader("ERROR", "Not your turn!");
					}				
				}
			}
			response.getWriter().append(game.getBoard());
		} catch(Exception nullException) {
			response.addHeader("ERROR", "Other player disconected / Game doesn't exists.");
		}
	}
	
	private void endGame(UUID gameId) {
		Util.endGame(gameId);
	}
	
}
