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

	public static Game findGame(UUID gameId) {
		for (Game game : games) {
			if(game.getGameId() == gameId) return game;
		}
		return null;
	}
	
	public static boolean play(Game game , int column) {
		if(game != null) {
			game.insertDisk(column);
			return true;
		} else return false;
	}
	
	public static Game startGame(String playerName) {
		if(!games.isEmpty()) {
			Game latestGame = games.get(games.size()-1);
			if(latestGame.isReadyToPlay()) {
				Game newGame = new Game(playerName);
				games.add(newGame);
			} else latestGame.join(playerName);
			return latestGame;
		} else {
			Game newGame = new Game(playerName);
			games.add(newGame);
			return newGame;
		}
	}
	
	public static void endGame(UUID gameId) {
		Game game = findGame(gameId);
		games.remove(game);
	}

}
