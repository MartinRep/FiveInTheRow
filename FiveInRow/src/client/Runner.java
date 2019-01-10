package client;

import java.io.IOException;

public class Runner {
	private static String serverURL = "http://localhost:8080/FiveInRow/";
	public static void main(String[] args) {
		 
		ServConnect fir = new ServConnect(serverURL);
		fir.startGame("Repo");
		System.out.println(fir.gameId);
		System.out.println(fir.playerId);
	}
	
	
}
