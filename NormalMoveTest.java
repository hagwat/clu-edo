package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;

import game.Game;
import game.Player;

public class NormalMoveTest {

	@Test
	public void test1() {
		Game game = new Game("test");
		Player testGuy = new Player(1, "James", game.getBoard());
		// --------------------------------------------------------|
		System.out.println(testGuy.validMove(new String[] { "w", "w", "w", "d" }));
		System.out.println();
		if (testGuy.getToken().getLocation()[0] != 8 || testGuy.getToken().getLocation()[1] != 21) {
			fail("incorrect position");
		}
	}

	@Test
	public void test2() {
		Game game = new Game("test");
		Player testGuy = new Player(2, "James", game.getBoard());
		// --------------------------------------------------------|
		System.out.println(testGuy.validMove(new String[] { "d", "w", "d", "s", "s"}));
		System.out.println();
		if (testGuy.getToken().getLocation()[0] != 1 || testGuy.getToken().getLocation()[1] != 16) {
			fail("incorrect position");
		}
	}

	@Test
	public void test3() {
		Game game = new Game("test");
		Player testGuy = new Player(2, "James", game.getBoard(), 8, 12);
		// --------------------------------------------------------|
		System.out.println(testGuy.validMove(new String[] { "s", "s", "s", "s", "s" }));
		System.out.println();
		if (testGuy.getToken().getLocation()[0] != 8 || testGuy.getToken().getLocation()[1] != 17) {
			fail("incorrect position");
		}
	}

	@Test
	public void test4() {
		Game game = new Game("test");
		Player testGuy = new Player(2, "James", game.getBoard(), 8, 12);
		// --------------------------------------------------------|
		System.out.println(testGuy.validMove(new String[] { "s", "s", "a" }));
		System.out.println();
		if (testGuy.getToken().getLocation()[0] != 7 || testGuy.getToken().getLocation()[1] != 14) {
			fail("incorrect position");
		}
	}

	@Test
	public void testInvalid1() {
		Game game = new Game("test");
		Player testGuy = new Player(2, "James", game.getBoard());
		// --------------------------------------------------------|
		try {
			System.out.println(testGuy.validMove(new String[] { "a", "w", "d", "a", "a" }));
			System.out.println();
			fail("went off map but continued");
		} catch (AssertionError e) {
		}
	}
	
	
	

}