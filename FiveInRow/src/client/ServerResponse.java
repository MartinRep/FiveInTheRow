package client;

import java.util.List;
import java.util.Map;

/**
 * Model Class ServerResponse. Every server query returns this object. Consist of body and map of all the relevant headers. 
 */
public class ServerResponse {
	
	/** The body. This contains board representation of Matrix
	 * 
	 *  */
	private String body;
	
	/** The headers map. This is used for Error, Winner, other player's name information */
	private Map<String, List<String>> headers;
	
	/**
	 * Gets the board representation of game matrix.
	 *
	 * @return the board representation. String
	 */
	public String getBoard() {
		StringBuilder boardBuilder = new StringBuilder();
		String[] lines = body.split("\\|");
		for (String line : lines) {
			boardBuilder.append(line);
			boardBuilder.append(System.getProperty("line.separator"));
		}
		return boardBuilder.toString();
	}
	
	/**
	 * Gets raw body. 2d Integer matrix.
	 *
	 * @return 2d Integer Matrix
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Sets the body from HTML response of the server.
	 *
	 * @param body the new body
	 */
	public void setBody(String body) {
		this.body = body;
	}
	
	/**
	 * Gets all the HTML headers map.
	 *
	 * @return the headers map String, List of Strings
	 */
	public Map<String, List<String>> getHeaders() {
		return headers;
	}
	
	/**
	 * Sets HTML headers from server response.
	 *
	 * @param headers from server response. 
	 */
	public void setHeaders(Map<String, List<String>> headers) {
		this.headers = headers;
	}
	
}
