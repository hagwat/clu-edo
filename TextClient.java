
package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.InputMismatchException;
import java.util.Scanner;


public class TextClient {

	private Game game;

	public TextClient(Game game) {
		this.game = game;
		startup();
	}

	public static String readString(String msg){
		System.out.println(msg);
		String s;
		Scanner sc = new Scanner(System.in);
		s = sc.next();
		return s;
	}

	public static int readInt(String msg){
		System.out.println("");
		System.out.println(msg);
		try{
			Scanner sc = new Scanner(System.in);
			int i = sc.nextInt();
			return i;

		}
		catch(InputMismatchException e){
			System.out.println("Must be a number!");
			return readInt(msg);
		}

	}

	public static void setPlayers(){
		int players = readInt("How many players? (Must be between 3-6)");
		while(players > 6 || players < 3){
			System.out.println("Must be between 3 and 6!");
			players = readInt("How many players? (Must be between 3-6)");
		}
		String[] playersNames = new String[players];
		for(int i = 1; i <= players; i++){
			playersNames[i - 1] = readString("Player " + i + " name?");
		}

	}

	public void startup() {
		System.out.println("********************************");
		System.out.println("       Welcome to Cluedo");
		System.out.println("********************************");
		setPlayers();
	}
}
