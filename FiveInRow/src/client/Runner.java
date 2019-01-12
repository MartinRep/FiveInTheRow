package client;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

// TODO: Auto-generated Javadoc
/**
 * The Class Runner.
 */
public class Runner {
	
	/** The Constant serverURL. */
	final private static String serverURL = "http://localhost:8080/FiveInRow/";
	
	/** The game. */
	private static Game game;
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		 
		int column = 255;
		Scanner keyboard = new Scanner(System.in);
		game = new Game(serverURL);
		String error = "";
		String name;
		ServerResponse initial;
		List<String> errorHeader;
		List<String> winnerHeader;
		List<String> otherHeader;
		try {
			do {
				System.out.println(error);
				System.out.println("\nEnter your name: ");
				name = keyboard.nextLine();
			} while((error = game.start(name)) != null);
			System.out.println("Waiting for Another player...");
			do {		// query server for Another player to join the game
				initial = game.play(255);	// empty command query
				errorHeader = initial.getHeaders().getOrDefault("ERROR", null);
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (errorHeader.get(0).contains("Waiting for another player"));
			System.out.println(initial.getBoard());		// display initial game board
			otherHeader = initial.getHeaders().getOrDefault("OTHERPLAYER", null);
			do {	
				System.out.println("You're playing agains: " + otherHeader.get(0).toString());
				System.out.println("Enter Column number(1-9) 10 to Quit: ");
				column = keyboard.nextInt();
				if(column != 10) {		// Player doesn't wants to quit the game
					ServerResponse sr = game.play(column - 1);
					System.out.println(sr.getBoard().toString());
					errorHeader = sr.getHeaders().getOrDefault("ERROR", null);		// readout all the headers send by server
					winnerHeader = sr.getHeaders().getOrDefault("WINNER", null);
					otherHeader = sr.getHeaders().getOrDefault("OTHERPLAYER", null);
					if(winnerHeader != null) {		// Winning conditions were met and Winner is announced.
						System.out.println("Game over. The winner is: " + winnerHeader.get(0).toString());
						column = 10;
					}else if(errorHeader != null)	System.out.println(errorHeader.get(0).toString());		// Error message found and displayed
						else {
							System.out.printf("Waiting for %s turn...\n", otherHeader);
							do {			// keep querying server until other player makes a move
								sr = game.play(255);
								errorHeader = sr.getHeaders().getOrDefault("ERROR", null);
								try {
									TimeUnit.SECONDS.sleep(1);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							} while (errorHeader.contains("Not your turn!"));
							if(errorHeader.contains("Other player disconected / Game doesn't exists.")) {		// Another played disconnected from the game
								System.out.println(errorHeader);
								column = 10;
							}
							System.out.println(sr.getBoard().toString());		// display the game board representation of the Matrix.
						}
				}
			} while (column != 10);		// keep prompting for new move, until quit command is entered by the player.
			game.play(9);		// send Quit command to the server
			keyboard.close();
			game = null;
		} catch (IOException e) {
			System.out.println("Error connecting to Server. " + e.getMessage());
		}
	}
	
	/* 
	 * Makes sure the game finish in orderly manner even when the application is interrupted.
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if(game != null) game.play(9);
	}
	
	
	
}
