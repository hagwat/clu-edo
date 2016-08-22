package game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The board class holds the positions of each piece in the game. It also displays the board in the console.
 *
 */
public class Board {

	private Tile[][] tiles = new Tile[24][25]; // tiles[i][j]
	private Room[] rooms = new Room[9];
	private List<CharacterToken> tokens = new ArrayList<CharacterToken>() ;

	public Board() {

		try {
			loadRooms();
			loadTiles();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructs all the rooms from the txt file rooms.txt
	 * @throws IOException
	 */
	public void loadRooms() throws IOException {
		Scanner sc = new Scanner(new File("src/resources/rooms.txt"));
		for (int i = 0; i < 9; i++) {
			Room room = new Room(sc.nextLine());
			room.setTileKey(sc.nextLine());
			room.setExits(sc.nextLine(), sc.nextLine());
			room.setWeaponLoc(sc.nextInt(), sc.nextInt());
			sc.nextLine();// so that the next iteration starts at the right place.
			rooms[i] = room;
		}
	}

	/**
	 * Reads board.txt to construct the two-dimensional array of tiles
	 *
	 * @throws IOException
	 */
	public void loadTiles() throws IOException {
		Scanner sc = new Scanner(new File("src/resources/board.txt"));
		for (int j = 0; j < tiles[0].length; j++) {
			for (int i = 0; i < tiles.length; i++) {
				Tile tile = new Tile(sc.next(), this);
				tiles[i][j] = tile;
			}
		}
	}

	/**
	 * Displays a text based representation of the board by displaying all tiles
	 * in their locations.
	 */
	public void displayTiles() {
	}

	/**
	 * Finds the starting point of each character using their ID.
	 * @param characterId
	 * @return
	 */
	public int[] findSpawn(int characterId) {
		for (int j = 0; j < tiles[0].length; j++) {
			for (int i = 0; i < tiles.length; i++) {
				if (tiles[i][j].getCharacterNumber() == characterId) {
					return new int[] { i, j };
				}
			}
		}
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

	public Room[] getRooms(){
		return rooms;
	}

	/**
	 * Returns the x and y co-ordinates of the room by String.
	 * @param name
	 * @return
	 */
	public int[] getRoomLocation(String name) {
		for (int j = 0; j < tiles[0].length; j++) {
			for (int i = 0; i < tiles.length; i++) {
				if (tiles[i][j].getType().equals(Tile.TileType.ROOM)) {
					if (tiles[i][j].getRoom().getName().equalsIgnoreCase(name)) {
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

	public List<CharacterToken> getCharacterTokens(){
		return this.tokens;
	}

	public Tile[][] getTiles(){
		return this.tiles;
	}

}
