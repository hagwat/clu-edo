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
	private Weapon wep; 	//Weapon currently in this room
+
+	public Room(String name, Weapon wep) {
+		this.name = name;
+		this.wep = wep;
+	}

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
	
	public Weapon getWep(){
		return wep;
	}
	
	public void setWep(Weapon w){
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
