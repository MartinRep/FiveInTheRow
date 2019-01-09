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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		Util.init();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// make if statement for request.GetParameter("playerUUID") to read the column for disk insertion
		
		Game game = Util.startGame("TestPlayer1");
		response.getWriter().append("Game UUID: " + String.valueOf(game.getGameId())).append(" Player number: " + String.valueOf(game.getPlayers().get(game.getCurrPlayer()).getName())).append(" Game board: " + game.getBoard());
		response.addHeader("gameId", String.valueOf(game.getGameId()));
		response.addHeader("playerId", String.valueOf(game.getPlayers().get(game.getCurrPlayer()).getUuid()));
		response.addIntHeader("playersTurn", game.getCurrPlayer());
		response.addIntHeader("turn", game.getTurn());
	}
}
