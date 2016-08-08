package game;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Represents a room in the game of Cluedo.
 *
 */
public class Room {

	private String name;	//Name of room
	private String tileKey;	//The key of the room
	private Map<String, int[]> exits;	//The co-ordinates of the exit(s)
	private WeaponToken wep = null; 	//Weapon currently in this room

	public Room(String name, WeaponToken wep) {
		this.name = name;
		this.wep = wep;
	}

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

	public WeaponToken getWep(){
		return wep;
	}

	public void setWep(WeaponToken w){
		this.wep = w;
	}

	@Override

	public boolean equals(Object o){
		if(o instanceof Room){
			Room r = (Room)o;
			if(r.getName().equalsIgnoreCase(this.name) && r.getWep().equals(this.wep)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((exits == null) ? 0 : exits.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((tileKey == null) ? 0 : tileKey.hashCode());
		result = prime * result + ((wep == null) ? 0 : wep.hashCode());
		return result;
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
