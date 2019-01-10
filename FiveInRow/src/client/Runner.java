package client;

public class Runner {
	private static String serverURL = "http://localhost:8080/FiveInRow/";
	public static void main(String[] args) {
		 
		ServConnect fir = new ServConnect(serverURL);
		String error = fir.startGame("");
		System.out.println(fir.gameId);
		System.out.println(fir.playerId);
		System.out.println(error);
	}
	
	
}
