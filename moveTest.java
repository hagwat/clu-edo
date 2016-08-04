package testcases;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;

import game.Game;
import game.Player;

public class moveTest {

	@Test
	public void test1() {
		Game game = new Game("test");
		Player testGuy = new Player(1, "James", game.getBoard());
		System.out.println(testGuy.getToken());
		//--------------------------------------------------------|
		System.out.println(testGuy.validMove(new String[]{"w", "w", "w", "d"}));
		
		
		
	}

}
