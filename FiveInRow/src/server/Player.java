package server;

import java.util.UUID;

/**
 * The Class Player. Model class holding all player's data with unique UUID generated at construction.
 */
public class Player {
	
	/** The name of the player. */
	private String name;
	
	/** The uuid. Unique player's ID */
	private UUID uuid;
	
	/**
	 * Instantiates a new player and generates his unique ID an UUID.
	 *
	 * @param name Player's name
	 */
	public Player(String name) {
		super();
		this.name = name;
		this.uuid = UUID.randomUUID();
	}
	
	/**
	 * Gets the name.
	 *
	 * @return Player's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the UUID.
	 *
	 * @return player's UUID
	 */
	public UUID getUuid() {
		return uuid;
	}
	
}
