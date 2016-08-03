package game;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
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
		Scanner sc = new Scanner(System.in);
		String s = sc.next();
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

	public void setPlayers(){
		List<String> names = getPlayerNames();
		List<Integer> tokens = getPlayerTokens(names);
		Queue<Player> players = new LinkedList<Player>();
		for(String s : names){
			for(int i : tokens){
				players.add(new Player(i, s));
				break;
			}
		}
		game.setPlayers(players);

	}

	public static List<String> getPlayerNames(){
		int players = readInt("How many players? (Must be between 3-6)");
		while(players > 6 || players < 3){
			System.out.println("Must be between 3 and 6!");
			players = readInt("How many players? (Must be between 3-6)");
		}
		List<String> playersNames = new ArrayList<String>();
		for(int i = 1; i <= players; i++){
			playersNames.add(readString("Player " + i + " name?"));
		}
		return playersNames;
	}

	public static List<Integer> getPlayerTokens(List<String> names){
		try {
			Scanner sc = new Scanner(new File("src/game/characters.txt"));
			System.out.println("****************CHARACTERS*****************");
			System.out.println("");
			while(sc.hasNextLine()){
				System.out.println(sc.nextLine());
			}
			System.out.println("");
			System.out.println("*******************************************");
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		List<Integer> tokens = new ArrayList<Integer>();

		for(String s : names){
			int number = readInt(s + ", please choose a number that refers to your character choice!");
			while(tokens.contains(number)){
				number = readInt("Token already chosen! Try again");
			}
			tokens.add(number);
		}
		return tokens;
	}


	public void startup() {
		System.out.println("********************************");
		System.out.println("       Welcome to Cluedo");
		System.out.println("********************************");
		setPlayers();
		System.out.println("*********************************");
		game.getBoard().displayTiles();
	}
}
