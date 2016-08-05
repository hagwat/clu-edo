package game;

import java.lang.Character;

import game.Tile.TileType;

public class Tile {

	private Room room; // what room the tile is in, if any.
	private Room connectsTo;
	private int characterNumber = -1;
	private TileType type; // where the tile is located, e.g. corridor,
							// inaccessible. Only tiles at the entrance to rooms
							// are in a room and are of type ROOM.
	private String arg;// A one or two character String which is used to create
						// tiles. Shown on map.

	public enum TileType {
		INACCESSABLE, CORRIDOR, ROOM, DOOR, SPAWN
	}

	/**
	 * Tiles Are Cool
	 *
	 * @param dirs
	 */
	public Tile(String arg, Board board) {
		processArg(arg, board);
	}

	public void processArg(String arg, Board board) {
		this.arg = arg;
		// An exhaustive series of switch statements describing behaviour for
		// each "arg".
		if (arg.length() == 1) {
			switch (arg) {
			case "n":
				type = TileType.INACCESSABLE;
				break;
			case "c":
				type = TileType.CORRIDOR;
				break;
			case "D":
				type = TileType.DOOR;
				break;
			case "k":
				type = TileType.ROOM;
				room = board.getRoom("Kitchen");
				break;
			case "b":
				type = TileType.ROOM;
				room = board.getRoom("Ball Room");
				break;
			case "o":
				type = TileType.ROOM;
				room = board.getRoom("Conservatory");
				break;
			case "i":
				type = TileType.ROOM;
				room = board.getRoom("Billiard Room");
				break;
			case "l":
				type = TileType.ROOM;
				room = board.getRoom("Library");
				break;
			case "s":
				type = TileType.ROOM;
				room = board.getRoom("Study");
				break;
			case "h":
				type = TileType.ROOM;
				room = board.getRoom("Hall");
				break;
			case "u":
				type = TileType.ROOM;
				room = board.getRoom("Lounge");
				break;
			case "d":
				type = TileType.ROOM;
				room = board.getRoom("Dining Room");
				break;
			}
		} else if (arg.length() == 2) {
			char c = arg.substring(1, 2).toCharArray()[0];
			java.lang.Character d = new Character(c);
			if (c >= 48 && c <= 57) {// ascii number for digits 0 to 9.
				type = TileType.SPAWN;
				characterNumber = Integer.parseInt(arg.substring(1, 2));
			} else {
				throw new Error("arg 2nd char not digit.");
			}
		} else {
			throw new Error("Tile arg length not 1 or 2.");
		}
	}

	public Room getRoom() {
		return this.room;
	}

	public int getCharacterNumber() {
		return characterNumber;

	}

	public TileType getType() {
		return this.type;
	}

	/**
	 * Displays a single tile. Called by the board to display all tiles at once.
	 */
	public void display() {
		if (room == null) {
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