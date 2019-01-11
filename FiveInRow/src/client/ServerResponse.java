package client;

import java.util.List;
import java.util.Map;

public class ServerResponse {
	
	private String body;
	private Map<String, List<String>> headers;
	
	public String getBoard() {
		StringBuilder boardBuilder = new StringBuilder();
		String[] lines = body.split("\\|");
		for (String line : lines) {
			boardBuilder.append(line);
			boardBuilder.append(System.getProperty("line.separator"));
		}
		return boardBuilder.toString();
	}
	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	public Map<String, List<String>> getHeaders() {
		return headers;
	}
	public void setHeaders(Map<String, List<String>> headers) {
		this.headers = headers;
	}
	
}
