package game;

public class Tile {
	
	//directions are represented by 4 booleans, up, right, down and left respectively. true means movement is possible in that direction.
	private boolean[] possibleDirections = new boolean[4];
	
	private Room room; // what room the tile is in, if any.
	
	
	/**
	 * Tiles Are Cool
	 * @param dirs
	 */
	public Tile(boolean[] dirs) {
		this.possibleDirections = dirs;
	}
	
	public Room getRoom(){
		return this.room;
	}
}
