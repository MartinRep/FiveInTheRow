package client;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Runner {
	final private static String serverURL = "http://localhost:8080/FiveInRow/";
	public static void main(String[] args) {
		 
		int column = 255;
		Scanner keyboard = new Scanner(System.in);
		Game game = new Game(serverURL);
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
			do {
				initial = game.play(255);
				errorHeader = initial.getHeaders().getOrDefault("ERROR", null);
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (errorHeader.get(0).contains("Waiting for another player"));
			System.out.println(initial.getBoard());
			otherHeader = initial.getHeaders().getOrDefault("OTHERPLAYER", null);
			do {	
				System.out.println("You're playing agains: " + otherHeader.get(0).toString());
				System.out.println("Enter Column number(1-9) 10 to Quit: ");
				column = keyboard.nextInt();
				if(column != 10) {
					ServerResponse sr = game.play(column - 1);
					System.out.println(sr.getBoard().toString());
					errorHeader = sr.getHeaders().getOrDefault("ERROR", null);
					winnerHeader = sr.getHeaders().getOrDefault("WINNER", null);
					otherHeader = sr.getHeaders().getOrDefault("OTHERPLAYER", null);
					if(winnerHeader != null) {
						System.out.println("Game over. The winner is: " + winnerHeader.get(0).toString());
						column = 10;
					}else if(errorHeader != null)	System.out.println(errorHeader.get(0).toString());
						else {
							System.out.printf("Waiting for %s turn...\n", otherHeader);
							do {
								sr = game.play(255);
								errorHeader = sr.getHeaders().getOrDefault("ERROR", null);
							} while (errorHeader.contains("Not your turn!"));
							System.out.println(sr.getBoard().toString());
						}
				}
				else {
					System.out.println("You're playing agains: " + otherHeader.get(0).toString());
				}
			} while (column != 10);
			game.play(9);
			keyboard.close();
		} catch (IOException e) {
			System.out.println("Error connecting to Server. " + e.getMessage());
		}
	}
	
	
}
