package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Board {

	private Tile[][] tiles = new Tile[24][25]; // tiles[i][j]
	private Room[] rooms = new Room[9];

	public Board() {

		try {			
			loadTiles();
			loadRooms();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("board created");
	}

	public void loadRooms() throws IOException {
		Scanner sc = new Scanner(new File("src/rooms.txt"));
		for (int i = 0; i < 9; i++) {
			Room room = new Room(sc.nextLine());
			room.setTileKey(sc.nextLine());
			rooms[i] = room;
		}
		System.out.println("rooms created");
	}

	/**
	 * Reads board.txt to construct the two-dimensional array of tiles
	 *
	 * @throws IOException
	 */
	public void loadTiles() throws IOException {
		Scanner sc = new Scanner(new File("src/board.txt"));
		for (int j = 0; j < tiles[0].length; j++) {
			for (int i = 0; i < tiles.length; i++) {
				Tile tile = new Tile(sc.next(), this);
				tiles[i][j] = tile;
			}
		}
		System.out.println("tiles created");
	}

	/**
	 * Displays a text based representation of the board by displaying all tiles
	 * in their locations.
	 */
	public void displayTiles() {
		for (int j = 0; j < tiles[0].length; j++) {
			for (int i = 0; i < tiles.length; i++) {
				tiles[i][j].display();
				if (i == tiles.length - 1) {
					System.out.println("|");
				}
			}
		}
	}

	/**
	 * returns a room by name
	 * @param name
	 * @return
	 */
	public Room getRoom(String name) {
		for (int i = 0; i < rooms.length; i++) {
			if (rooms[i].getName().equals(name)) {
				return rooms[i];
			}
		}
		return null;
	}

}
