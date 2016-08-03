package game;

public class Room {

	private String name;
	private String tileKey;

	public Room(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setTileKey(String key) {
		this.tileKey = key;
	}

	public String getTileKey() {
		return this.tileKey;
	}

}