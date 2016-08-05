package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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

	@Override
	public int[] getLocation() {
		int[] xy = new int[] { xPos, yPos };
		return xy;
	}

	public void display() {
		System.out.println("Player " + playerName + ", Character " + characterId + ": " + getCharacterName() + ", ["
				+ xPos + "," + yPos + "]");
	}

	public String getCharacterName() {
		try {
			Scanner sc = new Scanner(new File("src/game/characters.txt"));
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

	public boolean validMove(String[] moves) {
		int x = xPos;
		int y = yPos;
		System.out.println("before:");
		System.out.print("[");
		System.out.print(xPos+", ");
		System.out.println(yPos+"]");

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
			try{
			if (board.getTile(x, y).getType().equals(Tile.TileType.INACCESSIBLE)) {
				System.out.println("inaccessible "+x+","+y);
				return false;
			}
			}catch(ArrayIndexOutOfBoundsException e){
				System.out.print("exception, and ");
				return false;
			}
		}

		xPos = x;
		yPos = y;
		
		System.out.println("after:");
		System.out.print("[");
		System.out.print(xPos+", ");
		System.out.println(yPos+"]");
		
		return true;
	}

}