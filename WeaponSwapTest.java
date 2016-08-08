package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import game.Game;
import game.Room;
import game.WeaponToken;

public class WeaponSwapTest {

	
	
	@Test
	public void test1() {
		
		testHelp(0,0);
		testHelp(0,1);
		testHelp(1,2);
		testHelp(2,3);
		testHelp(3,4);
		testHelp(6,5);
		testHelp(5,6);
}
	
	public void testHelp(int wep,int room){
		Game game = new Game("test");		
		WeaponToken wepInRoom = game.getWeapons().get(wep);
		Room anotherRoom = game.getBoard().getRooms()[room];
		System.out.print("This; Weapon "+wep+"; ");
		System.out.println(wepInRoom.getName());
		System.out.print("This; Weapon "+room+"; ");
		System.out.println(anotherRoom.getWep().getName());
		game.swapWeaponTokens(wepInRoom.getName(), anotherRoom);
	}
	
	@Test
	public void test2() {
		int wep = 1;
		int room=1;
		
		Game game = new Game("test");
		WeaponToken wepInRoom = game.getWeapons().get(wep);
		Room anotherRoom = game.getBoard().getRooms()[room];
		System.out.println(wepInRoom.getName());
		System.out.println(anotherRoom.getWep().getName());
		game.swapWeaponTokens(wepInRoom.getName(), anotherRoom);
}
	@Test
	public void test3() {
		Game game = new Game("test");
		int wep = 1;
		int room=0;
		WeaponToken wepInRoom = game.getWeapons().get(wep);
		Room anotherRoom = game.getBoard().getRooms()[room];
		System.out.println(wepInRoom.getName());
		System.out.println(anotherRoom.getWep().getName());
		game.swapWeaponTokens(wepInRoom.getName(), anotherRoom);
}
	@Test
	public void test4() {
		Game game = new Game("test");
		int wep = 2;
		int room=1;
		WeaponToken wepInRoom = game.getWeapons().get(wep);
		Room anotherRoom = game.getBoard().getRooms()[room];
		System.out.println(wepInRoom.getName());
		System.out.println(anotherRoom.getWep().getName());
		game.swapWeaponTokens(wepInRoom.getName(), anotherRoom);
}
	@Test
	public void test5() {
		Game game = new Game("test");
		int wep = 3;
		int roomNum=2;
		WeaponToken wepInRoom = game.getWeapons().get(wep);
		Room anotherRoom = game.getBoard().getRooms()[roomNum];
		System.out.println(wepInRoom.getName());
		System.out.println(anotherRoom.getWep().getName());
		game.swapWeaponTokens(wepInRoom.getName(), anotherRoom);
}
	

}
