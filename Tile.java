package game;

public class Tile {

	private Room room; // what room the tile is in, if any.
	private TileType type; //where the tile is located, e.g. corridor, inaccessible. Only tiles at the entrace to rooms are in a room and are of type ROOM.
	private String arg;//A one or two character String which is used to create tiles. Shown on map board.txt.

	public enum TileType {
		INACCESSABLE, CORRIDOR, ROOM, DOOR, TELEPORTER
	}


	/**
	 * Tiles Are Cool
	 *
	 * @param dirs
	 */
	public Tile(String arg, Board board) { 
		processArg(arg, board);
	}
	
	public void processArg(String arg, Board board){
		this.arg = arg;
		if (arg.length() == 1) {
			switch (arg) {
			case "n":
				type = TileType.INACCESSABLE;
				break;
			case "c":
				type = TileType.CORRIDOR;
				break;
			case "D":
				break;
			case "k":
				type = TileType.ROOM;
				break;
			case "b":
				type = TileType.ROOM;
				room = board.getRoom("Ball Room");
				break;
			}
		} else if (arg.length() == 2) {

		} else {
			throw new Error("Tile arg length not 1 or 2.");
		}
	}

	public Room getRoom() {
		return this.room;
	}

	public void display() {
		if(room==null){	
		}
		if (type == TileType.ROOM) {
			System.out.print("|" + arg);
			if (arg.length() == 1) {
				System.out.print(" ");
			}
		} else {
			System.out.print("|" + arg);
			if (arg.length() == 1) {
				System.out.print(" ");
			}
		}
	}
}
