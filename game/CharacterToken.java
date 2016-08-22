package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents a Player's Character token in the game of Cluedo.
 *
 */
public class CharacterToken implements Locatable {

	private int characterId;// aka characterNum
	private String playerName;
	private int xPos;
	private int yPos;
	private Board board;

	public CharacterToken(String playerName, Board board, int characterId) {
		this.playerName = playerName;
		this.board = board;
		this.xPos = board.findSpawn(characterId)[0];
		this.yPos = board.findSpawn(characterId)[1];
		this.characterId = characterId;

		display();
	}

	public CharacterToken(Board board, int characterId){
		System.out.println("Filling in character "+characterId);
		this.board = board;
		this.xPos = board.findSpawn(characterId)[0];
		this.yPos = board.findSpawn(characterId)[1];
		this.characterId = characterId;
	}

	@Override
	public int[] getLocation() {
		int[] xy = new int[] { xPos, yPos };
		return xy;
	}

	/**
	 * Displays the CharacterToken's current co-ordinates to the console
	 */
	public void display() {
		System.out.println("Player " + playerName + ", Character " + characterId + ": " + getCharacterName() + ", ["
				+ xPos + "," + yPos + "]");
	}

	/**
	 * Uses the character ID to determine what character it is from the text file
	 * @return
	 */
	public String getCharacterName() {
		try {
			Scanner sc = new Scanner(new File("src/resources/characters.txt"));
			while (sc.hasNextLine()) {
				int nextInt = sc.nextInt();
				if (characterId == nextInt) {
					sc.nextLine();
					String s = sc.nextLine();
					return s;
				} else {
					sc.nextLine();
					sc.nextLine();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Character not found");
		return null;
	}

	/**
	 * Returns a boolean on whether the array of moves passed is valid for this player.
	 * @param moves
	 * @return
	 */
	public boolean validMove(String[] moves) {
		int x = xPos;
		int y = yPos;
		System.out.println("before:");
		System.out.print("[");
		System.out.print(xPos + ", ");
		System.out.println(yPos + "]");

		for (int i = 0; i < moves.length; i++) {
			if (moves[i].equals("w")) {
				y -= 1;
			} else if (moves[i].equals("a")) {
				x -= 1;
			} else if (moves[i].equals("s")) {
				y += 1;
			} else if (moves[i].equals("d")) {
				x += 1;
			}
			try {
				if (board.getTile(x, y).getType().equals(Tile.TileType.INACCESSABLE)) {
					System.out.println("inaccessible " + x + "," + y);
					return false;
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.print("exception, and ");
				return false;
			}
		}

		Tile tile = board.getTile(xPos, yPos);
		
		// updates the position on the board
		xPos = x;
		yPos = y;

		System.out.println("after:");
		System.out.print("[");
		System.out.print(xPos + ", ");
		System.out.print(yPos + "] ");
		tile = board.getTile(xPos, yPos);
		if (tile.getType().equals(Tile.TileType.ROOM)) {
			System.out.print("The " + tile.getRoom().getName());
			if (tile.getRoom().getWep() != null) {
				System.out.println(" contains the " + tile.getRoom().getWep().getName() + ".");
			} else {
				System.out.println(" is empty.");
			}

		} else {
			System.out.println();
		}

		return true;
	}

	/**
	 * Moves this CharacterToken to a room's co-ordinates
	 * @param coords
	 */
	public void roomMove(int[] coords) {
		setPos(coords[0], coords[1]);
	}

	public Room getRoom() {
		return board.getTile(xPos, yPos).getRoom();
	}

	public void setRoom(String r) {
		int[] coordinates = board.getRoomLocation(r);
		xPos = coordinates[0];
		yPos = coordinates[1];
	}

	public void setPos(int x, int y) {
		xPos = x;
		yPos = y;
		System.out.println("Change to [" + xPos + "," + yPos + "]");
		Tile tile = board.getTile(xPos, yPos);
		if (tile.getType().equals(Tile.TileType.ROOM)) {
			System.out.print("The " + tile.getRoom().getName());
			if (tile.getRoom().getWep() != null) {
				System.out.println(" contains the " + tile.getRoom().getWep().getName() + ".");
			} else {
				System.out.println(" is empty.");
			}

		} else {
			System.out.println();
		}
	}

	public int getId(){
		return this.characterId;
	}

}
