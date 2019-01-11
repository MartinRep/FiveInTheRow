package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class Game {
	
	private String serverURL;
	private String playerId = "";
	private String gameId = "";
	
	public Game(String serverURL) {
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

	public String start(String name) throws IOException {
		try {
			ServerResponse sr = getResponse("?playerName=" + name);
			List<String> errorHeader = sr.getHeaders().getOrDefault("ERROR", null);
			if(errorHeader != null)	return errorHeader.get(0);
			else {
				List<String> gameHeader = sr.getHeaders().getOrDefault("gameId", null);
				setGameId(gameHeader.get(0));
				List<String> playerHeader = sr.getHeaders().getOrDefault("playerId", null);
				setPlayerId(playerHeader.get(0));
				return null;				
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String play(int column) throws IOException {
		StringBuilder response = new StringBuilder();
		ServerResponse sr = getResponse("/play?gameId=" + gameId + "&playerId=" + playerId + "&command=" + String.valueOf(column));
		List<String> errorHeader = sr.getHeaders().getOrDefault("ERROR", null);
		if(errorHeader != null)	response.append(errorHeader.get(0)).toString();
		List<String> winnerHeader = sr.getHeaders().getOrDefault("WINNER", null);
		if(winnerHeader != null) response.append(winnerHeader.get(0)).toString();
		List<String> otherHeader = sr.getHeaders().getOrDefault("OTHERPLAYER", null);
		if(otherHeader != null)	response.append(otherHeader.get(0)).toString();
		return response.append(sr.getBoard()).toString();
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
