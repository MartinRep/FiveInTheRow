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
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		Util.init();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String playerName = request.getParameter("playerName");
			if(playerName == null) response.addHeader("ERROR", "No player's name given.");
			else {
				System.out.println("Players name: " + playerName);
				Game game = Util.startGame(playerName);	
				response.getWriter().append(game.getBoard());
				response.addHeader("gameId", String.valueOf(game.getGameId()));
				response.addHeader("playerId", String.valueOf(game.getPlayers().get(game.getPlayers().size()-1).getUuid()));				
			}
		} catch(NullPointerException exp) {
			response.addHeader("ERROR", "No player's name given.");
		}
		
	}
}
