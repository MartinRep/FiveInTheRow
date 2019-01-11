package client;

import java.io.IOException;
import java.util.Scanner;

public class Runner {
	private static String serverURL = "http://localhost:8080/FiveInRow/";
	public static void main(String[] args) {
		 
		Game game = new Game(serverURL);
		int column = 255;
		try {
			System.out.println(game.start("Repo"));
			Scanner keyboard = new Scanner(System.in);
			do {
				System.out.println("enter Column: ");
				column = keyboard.nextInt();
				System.out.println(game.play(column));
				
			} while (column != 255);
			keyboard.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	
}
