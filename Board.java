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
			loadRooms();
			loadTiles();

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("board created");
	}

	public void loadRooms() throws IOException {
		Scanner sc = new Scanner(new File("src/game/rooms.txt"));
		for (int i = 0; i < 9; i++) {
			Room room = new Room(sc.nextLine());
			room.setTileKey(sc.nextLine());
			room.setExits(sc.nextLine(), sc.nextLine());
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
		Scanner sc = new Scanner(new File("src/game/board.txt"));
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
				if(i == 0 && j < 10){
+					System.out.print(j + "  ");
+				}else if(i == 0 && j >= 10){
+					System.out.print(j + " ");
+				}
				tiles[i][j].display();
				if (i == tiles.length - 1) {
					System.out.println("|");
				}
			}
		}
		System.out.print("   ");
+		for(int i = 0; i < tiles.length; i++){
+			if(i < 10){
+			System.out.print(" " + i + " ");
+			if(i == 9) System.out.print(" ");
+			}else{
+				System.out.print(i + " ");
+			}
+		}
+		System.out.println("");
	}

	public int[] findSpawn(int characterId) {
		for (int j = 0; j < tiles[0].length; j++) {
			for (int i = 0; i < tiles.length; i++) {
				if (tiles[i][j].getCharacterNumber() == characterId) {
					return new int[] { i, j };
				}
			}
		}
		System.out.println("Spawn not found.");
		return null;
	}

	/**
	 * returns a room by name
	 *
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


	public int[] getRoomLocation(String name) {
		for (int j = 0; j < tiles[0].length; j++) {
			for (int i = 0; i < tiles.length; i++) {
				if (tiles[i][j].getType().equals(Tile.TileType.ROOM)) {
					if (tiles[i][j].getRoom().getName().equals(name)) {
						return new int[] { i, j };
					}
				}
			}
		}
		return null;
	}

	public Tile getTile(int x, int y) {
		return this.tiles[x][y];
	}

}
