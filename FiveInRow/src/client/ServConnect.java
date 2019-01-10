package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class ServConnect {
	
	String serverURL;
	String playerId = "";
	String gameId = "";
	
	public ServConnect(String serverURL) {
		super();
		setServerURL(serverURL);
	}
	
	private void setGameId(String gameId) {
		this.gameId = gameId;
	}
	
	private void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}

	private void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public boolean startGame(String name) {
		try {
			ServerResponse sr = getResponse("?playerName=" + name);
			List<String> gameHeader = sr.getHeaders().getOrDefault("gameId", null);
			setGameId(gameHeader.get(0));
			List<String> playerHeader = sr.getHeaders().getOrDefault("playerId", null);
			setPlayerId(playerHeader.get(0));
			return true;
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	

	private ServerResponse getResponse(String command) throws IOException {
		ServerResponse servResponse = new ServerResponse();
		StringBuilder body = new StringBuilder();
		URL fiveInRow = new URL(serverURL + command);
		URLConnection fir = fiveInRow.openConnection();
		BufferedReader in = new BufferedReader(
				new InputStreamReader(
						fir.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			body.append(inputLine);
		}
		servResponse.setBody(body.toString());
		servResponse.setHeaders(fir.getHeaderFields());
		in.close();
		return servResponse;
	}


}