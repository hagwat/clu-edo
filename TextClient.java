package game;

import java.util.Scanner;

public class TextClient {

	public TextClient() {
		startup();
	}

	public void startup() {
		System.out.print("create board? ");
		Scanner sc = new Scanner(System.in);
		if (sc.hasNext("y")) {
			Board board = new Board();
		} else {
			System.out.println("nevermind");
		}

	}

	// maybe we should use a listener to listen for wasd input when people move
	// their characters

	public static void main(String[] args) {
		new TextClient();
	}
}
