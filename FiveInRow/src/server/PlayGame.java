package server;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Takes care of actual play, after player acquire game id and player in from NewGame servlet.
 * takes game id, player id and command as arguments. Command between 0 - 8 places disk to respective column in the matrix. 10 ends the game.
 * returns board in body and error, winner, other player's name in headers. 
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

	/** Consists of API logic. Finds the game object according to game Id argument. Player id confirms if the right player is taking the turn.
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
	
	/**
	 * Orderly ends the game specified by game Id
	 * @param gameId. Unique game id.
	 */
	
	private void endGame(UUID gameId) {
		Util.endGame(gameId);
	}
	
}
