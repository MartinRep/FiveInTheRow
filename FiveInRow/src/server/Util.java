package server;

import java.util.ArrayList;
import java.util.UUID;

public class Util {
	
	private static ArrayList<Game> games = null;
	
	private Util(){
		
	}
	
	public static synchronized void init() {
		games = new ArrayList<Game>();
	}

	public static Game findGame(UUID gameId) {				// return game if exists, or null
		for (Game game : games) {
			if(game.getGameId().equals(gameId)) return game;
		}
		return null;
	}
	
	public static Game startGame(String playerName) {
		if(!games.isEmpty()) {
			Game latestGame = games.get(games.size()-1);	// Get's the latest game spawned.
			if(latestGame.isReadyToPlay()) {				// Checks if the latest game have both players
				Game newGame = new Game(playerName);		// If so creates new game
				games.add(newGame);
			} else latestGame.join(playerName);				// If game is waiting for second player, joins the existing game 
			return latestGame;								
		} else {
			Game newGame = new Game(playerName);			// Creates new game if the array is empty. No game to join. 
			games.add(newGame);
			return newGame;
		}
	}
	
	public static void endGame(UUID gameId) {				// Finish the game and deletes it from array. In case of player disconnect or wins 
		Game game = findGame(gameId);
		games.remove(game);
	}

}
