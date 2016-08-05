package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import game.Game;

public class WeaponSwapTest {

	@Test
		public void test1() {
			Game game = new Game("test");
			System.out.println(game.getWeapons().get(0).getName());
			System.out.println("got here");
			game.swapWeaponTokens(game.getWeapons().get(0).getName(), game.getBoard().getRooms()[0]);
			

	}

}
