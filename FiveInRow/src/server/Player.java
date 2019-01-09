package server;

import java.util.UUID;

public class Player {
	private String name;
	private UUID uuid;
	
	public Player(String name) {
		super();
		this.name = name;
		this.uuid = UUID.randomUUID();
	}
	public String getName() {
		return name;
	}
	public UUID getUuid() {
		return uuid;
	}
	
}
