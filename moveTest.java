package tests;

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
		//--------------------------------------------------------|
		System.out.println(testGuy.validMove(new String[]{"w", "w", "w", "d"}));
		if(testGuy.getToken().getLocation()[0]!=8||testGuy.getToken().getLocation()[1]!=21){
			fail("incorrect position");
		}		
	}
	
	@Test
	public void test2() {
		Game game = new Game("test");
		Player testGuy = new Player(2, "James", game.getBoard());
		//--------------------------------------------------------|
		System.out.println(testGuy.validMove(new String[]{"d", "w", "d", "a", "a"}));
		if(testGuy.getToken().getLocation()[0]!=2||testGuy.getToken().getLocation()[1]!=18){
			fail("incorrect position");
		}		
	}

}
