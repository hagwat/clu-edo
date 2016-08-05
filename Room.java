package game;

public class Room {

	private String name;
	private String tileKey;
	private Weapon wep; 	//Weapon currently in this room

	public Room(String name, Weapon wep) {
		this.name = name;
		this.wep = wep;
	}

	public Room(String name) {
		this.name = name;
		this.wep = wep;
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
}