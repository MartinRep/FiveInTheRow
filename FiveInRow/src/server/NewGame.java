package server;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class FiveRow
 */
@WebServlet(asyncSupported = true, description = "Five in the row Start a New Game", urlPatterns = { "/" })
public class NewGame extends HttpServlet {
	private static final long serialVersionUID = 14535435L;
	Matrix matrix;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewGame() {
        super();
    }

	/**
	 * Initialize Unit static class.
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		Util.init();
	}

	/**
	 * Client send request with player's name and receives board as HTML body and 2 unique UUIDs in the header. Player Id and Game Id.
	 * These are used to play the game through Playgame servlet class.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String playerName = request.getParameter("playerName");
		if(playerName == null || playerName.equals("")) response.addHeader("ERROR", "No player's name given.");
		else {
			Game game = Util.startGame(playerName);	
			response.getWriter().append(game.getBoard());
			response.addHeader("gameId", String.valueOf(game.getGameId()));
			response.addHeader("playerId", String.valueOf(game.getPlayers().get(game.getPlayers().size()-1).getUuid()));				
		}
	}
}
