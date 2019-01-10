package client;

import java.io.IOException;

public class Runner {
	private static String serverURL = "http://localhost:8080/FiveInRow/";
	public static void main(String[] args) {
		 
		ServConnect fir = new ServConnect(serverURL);
		try {
			String error = fir.startGame("Repo");
			System.out.println(error);
			System.out.println(fir.playGame(0));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	
}
