package server;

import java.util.ArrayList;
import java.util.UUID;

/**
 * The Class Util. Singleton class. Holds ArrayList of Game objects. Servlets gets the game object through this class. 
 */
public class Util {
	
	/** The games. The ArrayList of Game objects*/
	private static ArrayList<Game> games = null;
	
	/**
	 * Singleton.
	 */
	private Util(){
		
	}
	
	/**
	 * Initializes the the games ArrayList.
	 */
	public static synchronized void init() {
		if (games == null) games = new ArrayList<Game>();
	}

	/**
	 * Find game object by the unique UUID. This is used by PlayGame servlet.
	 *
	 * @param gameId. Unique game ID(UUID).
	 * @return game object.
	 */
	public static Game findGame(UUID gameId) {				// return game if exists, or null
		for (Game game : games) {
			if(game.getGameId().equals(gameId)) return game;
		}
		return null;
	}
	
	/**
	 * Starts game. This initialize the game object with First player name. Checks for the existing game for the player to join,
	 * or creates new one. As new game is generated every time there is no game or all games have both player, the number of players
	 * who wants to play is not limited to just 2 per server.  
	 *
	 * @param playerName the player's name who wants to start/join the game.
	 * @return the game object.
	 */
	public static Game startGame(String playerName) {
		if(!games.isEmpty()) {
			Game latestGame = games.get(games.size()-1);	// Get's the latest game spawned.
			if(latestGame.isReadyToPlay()) {				// Checks if the latest game have both players
				Game newGame = new Game(playerName);		// If so creates new game
				games.add(newGame);
			} else latestGame.join(playerName);				// If game is waiting for second player, joins the existing game 
			return games.get(games.size()-1);								
		} else {
			Game newGame = new Game(playerName);			// Creates new game if the array is empty. No game to join. 
			games.add(newGame);
			return newGame;
		}
	}
	
	/**
	 * End game when one of the players disconnect or the winning condition is met and both players have got the results. 
	 *
	 * @param gameId. Unique game's ID(UUID).
	 */
	public static void endGame(UUID gameId) {				// Finish the game and deletes it from array. In case of player disconnect or wins 
		Game game = findGame(gameId);
		games.remove(game);
	}

}
