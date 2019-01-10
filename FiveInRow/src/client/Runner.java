package client;

import java.io.IOException;

public class Runner {
	private static String serverURL = "http://localhost:8080/FiveInRow/";
	public static void main(String[] args) {
		 
		Game game = new Game(serverURL);
		try {
			String error = game.start("Repo");
			System.out.println(error);
			System.out.println(game.play(0));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	
}
