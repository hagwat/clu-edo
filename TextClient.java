package game;

import java.util.Scanner;

public class TextClient {

	private Game game;

	public TextClient(Game game) {
		this.game = game;
		startup();
	}

	public void startup() {
		Scanner sc = new Scanner(System.in);

		System.out.println();
		System.out.print("create board? ");
		System.out.println();
		if (sc.hasNext("y")) {
			sc.next();
			game.createBoard();
		} else {
			System.out.println("nevermind");
		}

		System.out.println();
		System.out.print("display board? ");
		if (sc.hasNext("y")) {
			game.getBoard().displayTiles();
		} else {
			System.out.println("nevermind");
		}
		sc.close();
	}
}
