package game;
import java.lang.Character;

public class Tile {

	private Room room; // what room the tile is in, if any.
	private Room connectsTo;
	private TileType type; // where the tile is located, e.g. corridor,
							// inaccessible. Only tiles at the entrance to rooms
							// are in a room and are of type ROOM.
	private String arg;// A one or two character String which is used to create
						// tiles. Shown on map.

	public enum TileType {
		INACCESSABLE, CORRIDOR, ROOM, DOOR, TELEPORTER, START
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
			String secondLetter = arg.substring(1, 1);
			switch (secondLetter) {
			case "k":
				type = TileType.TELEPORTER;
				connectsTo = board.getRoom("Kitchen");
				break;
			case "b":
				type = TileType.TELEPORTER;
				room = board.getRoom("Ball Room");
				break;
			case "o":
				type = TileType.TELEPORTER;
				room = board.getRoom("Conservatory");
				break;
			case "i":
				type = TileType.TELEPORTER;
				room = board.getRoom("Billiard Room");
				break;
			case "l":
				type = TileType.TELEPORTER;
				room = board.getRoom("Library");
				break;
			case "s":
				type = TileType.TELEPORTER;
				room = board.getRoom("Study");
				break;
			case "h":
				type = TileType.TELEPORTER;
				room = board.getRoom("Hall");
				break;
			case "u":
				type = TileType.TELEPORTER;
				room = board.getRoom("Lounge");
				break;
			case "d":
				type = TileType.TELEPORTER;
				room = board.getRoom("Dining Room");
				break;
			}
			
			char c = secondLetter.toCharArray()[0];
			System.out.println(c);
			Character d = new Character(c);
			System.out.println(d);
				if (d >= 48 && d <= 57) {
					System.out.println(d);
				}
			
		} else {
			throw new Error("Tile arg length not 1 or 2.");
		}
	}

	public Room getRoom() {
		return this.room;
	}

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
