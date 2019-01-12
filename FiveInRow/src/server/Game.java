package server;

import java.util.ArrayList;
import java.util.UUID;

/**
 * The Class Game.
 */
public class Game {
	
	/** The players.
	 *  Player object arrayList
	 */
	private ArrayList<Player> players;
	
	/** The matrix object. */
	private Matrix matrix;
	
	/** The turn. Number of all palyers moves*/
	private int turn;
	
	/** The curr player. Current player number 0 or 1*/
	private int currPlayer;
	
	/** The ready to play. If both players joined turns true.*/
	private boolean readyToPlay;
	
	/** The game id. Used for finding the game in Util class games arraylist*/
	private UUID gameId;
	
	/** The winner. This is triggered once the winning condition is met, which is checked by checkWinner function*/
	private int winner = 255;
	
	/**
	 * Instantiates a new game. Generates unique UUID for the game and initialize all variables and adds first Player object to the players ArrayList.
	 *
	 * @param playerName. First player name
	 */
	public Game(String playerName) {
		matrix = new Matrix();
		players = new ArrayList<Player>();
		turn = 0;
		currPlayer = 0;
		readyToPlay = false;
		players.add(new Player(playerName));
		gameId = UUID.randomUUID();
	}
		
	/**
	 * Gets the players ArrayList.
	 *
	 * @return the players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * Gets the number of turns by all players.
	 *
	 * @return the turn number
	 */
	public  int getTurn() {
		return turn;
	}

	/**
	 * Gets the current player. 0 or 1.
	 *
	 * @return the current player number
	 */
	public  Player getCurrPlayer() {
		return players.get(currPlayer);
	}

	/**
	 * Checks if is ready to play.
	 *
	 * @return true, if is ready to play (Both players joined).
	 */
	public  boolean isReadyToPlay() {
		return readyToPlay;
	}

	/**
	 * Gets the game id.
	 *
	 * @return the game id
	 */
	public UUID getGameId() {
		return gameId;
	}

	/**
	 * Gets the winner.
	 *
	 * @return the winner
	 */
	public int getWinner() {
		return winner;
	}

	/**
	 * Gets the board.
	 *
	 * @return the board
	 */
	public  String getBoard() {
		return matrix.toString();
	}

	/**
	 * Join. Used by second player to join already existing game.
	 *
	 * @param playerName Second player name
	 * @return second player object
	 */
	public Player join(String playerName) {
		players.add(new Player(playerName));
		readyToPlay = true;
		return players.get(1);
	}
	
	/**
	 * Gets the other player object. Player 1 gets Player 2 object and vice versa. Used for displaying other players name.
	 *
	 * @param thisPlayer Player asking for another player.
	 * @return The other player object.
	 */
	public String getOtherPlayer(UUID thisPlayer) {
		for (Player player : players) {
			if(!player.getUuid().equals(thisPlayer)) return String.valueOf(player.getName());
		}
		return null;
	}
	
	/**
	 * Insert disk in the matrix at specific column. 
	 *
	 * @param column. Player chosen column to drop their disk.
	 * @return true, if successful. False if column was full
	 */
	public boolean insertDisk(int column) {
		if(column > 8 || column < 0) return false;	// illegal column number selected 
		int row = matrix.insertDisk(column, currPlayer);
		if(row != 255) {
			if(checkWinner(row, column)) return true;
			turn++;
			if(currPlayer == 0) currPlayer = 1;
			else currPlayer = 0;
			return true;
		}
		return false;
	}
	
	/**
	 * Check winner. Checks for winning condition for specific disk at row, column position.
	 *
	 * @param row. The row position of the disk
	 * @param column. The column position of the disk.
	 * @return true, if winning condition is met.
	 */
	private boolean checkWinner(int row, int column) {
		if(matrix.checkWinner(row, column)) {
			winner = currPlayer;
			return true;
		}
		else return false;
	}

}
