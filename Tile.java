package game;

public class Tile {

	private Room room; // what room the tile is in, if any.
	private RoomType type;
	private static int count = 0;
	private String arg;

	public enum RoomType {
		INACCESSABLE, CORRIDOR, ROOM, DOOR
	}

	/**
	 * Tiles Are Cool
	 *
	 * @param dirs
	 */
	public Tile(String arg, Board board) { //WORKING HERE
		this.arg = arg;
		if (arg.length() == 1) {
			switch (arg) {
			case "n":
				type = RoomType.INACCESSABLE;
			case "c":
				type = RoomType.CORRIDOR;
			case "D":
				type = RoomType.DOOR;
			case "k":
				type = RoomType.ROOM;
				room = board.getRoom("Kitchen");
			case "b":
				type = RoomType.ROOM;
				room = board.getRoom("Ball Room");
			}
		} else if (arg.length() == 2) {

		} else {
			throw new Error("Tile arg length not 1 or 2.");
		}
	}

	public Room getRoom() {
		return this.room;
	}

	public void display() { //AND HERE
		if (type == RoomType.ROOM) {
			System.out.print("|" + arg + " " + room.getName());
			if (arg.length() == 1) {
				System.out.print(" ");
			}
		}else{
			System.out.print("|" + arg);
			if (arg.length() == 1) {
				System.out.print(" ");
			}
		}
	}
}
