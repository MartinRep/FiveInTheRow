package server;

import java.util.ArrayList;
import java.util.UUID;

public class Game {
	
	private ArrayList<Player> players;
	private Matrix matrix;
	private int turn;
	private int currPlayer;
	private boolean readyToPlay;
	private UUID gameId;
	
	public Game(String playerName) {
		matrix = new Matrix();
		players = new ArrayList<Player>();
		turn = 0;
		currPlayer = 0;
		readyToPlay = false;
		players.add(new Player(playerName));
		gameId = UUID.randomUUID();
	}
		
	public ArrayList<Player> getPlayers() {
		return players;
	}

	public  int getTurn() {
		return turn;
	}

	public  int getCurrPlayer() {
		return currPlayer;
	}

	public  boolean isReadyToPlay() {
		return readyToPlay;
	}

	public UUID getGameId() {
		return gameId;
	}

	public  String getBoard() {
		return matrix.toString();
	}

	public Player join(String playerName) {
		players.add(new Player(playerName));
		readyToPlay = true;
		return players.get(1);
	}
	
	public boolean insertDisk(int column) {
		if(!isReadyToPlay()) return false;
		int row = matrix.insertDisk(column, currPlayer);
		if(row != -1) {
			turn++;
			if(currPlayer == 0) currPlayer = 1;
			else currPlayer = 0;
		}
		return true;
	}
	
	public boolean checkWinner(int row, int column) {
		return matrix.checkWinner(row, column);
	}

}
