package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Room {

	private String name;
	private String tileKey;
	private Map<String, int[]> exits;

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

	public void setExits(String exitLine, String exitCoords) {
		exits = new HashMap<String, int[]>();
		Scanner ex = new Scanner(exitLine);
		Scanner co = new Scanner(exitCoords);
		while (ex.hasNext()) {
			exits.put(ex.next(), new int[] { co.nextInt(), co.nextInt() });
		}
	}

	public Map<String, int[]> getExits() {
		return exits;
	}



}