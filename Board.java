package game;

public class Board {
	private Tile[][] tiles;

	public void display() {
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				System.out.print(tiles[i][j].toString());
				if (i == tiles.length - 1) {
					System.out.println("|");
				}
			}
		}
	}

}
