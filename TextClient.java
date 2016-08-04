package game;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class TextClient {

	private Game game;
	private boolean gameIsOver = false;

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
		if(names.size()!=tokens.size()){throw new IllegalStateException("Number of players not equal to number of tokens.");}
		for(int i=0; i<names.size();i++){
			players.add(new Player(tokens.get(i), names.get(i), game.getBoard()));
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

	public static void startPlayerTurn(Player p){
		System.out.println("********** " + p.toString() + "'s Turn **********");
		System.out.println("");
		System.out.println("Options:");
		System.out.println("- Roll Dice & Move");
		System.out.println("- Make an Accusation");
		System.out.println("- Display Hand");
		System.out.println("- Make Announcement");
		List<String> options = new ArrayList<String>();
		options.add("roll");
		options.add("accuse");
		options.add("hand");
		options.add("announce");
		while(true){
			System.out.println("Your options are:");
			System.out.print("[");
			for(int i = 0; i < options.size(); i++){
				if(i == options.size() - 1){
					System.out.println(" " + options.get(i) + "]");
				}
				else if(i == 0){
					System.out.print(options.get(i) + " /");
				}else{
				System.out.print(" " + options.get(i) + " /");
				}
			}
			String input = readString("Please type in your choice!").toLowerCase();
			while(!options.contains(input)){
				System.out.println("Invalid move! Your options are:");
				System.out.print("[");
				for(int i = 0; i < options.size(); i++){
					if(i == options.size() - 1){
						System.out.println(" " + options.get(i) + "]");
					}
					else if(i == 0){
						System.out.print(options.get(i) + " /");
					}else{
					System.out.print(" " + options.get(i) + " /");
					}
				}
				input = readString("Please type in a valid choice!");
			}
			if(input.equals("roll")){
			//	playerRoll(p);	//Player still has options therefore loop again
				options.remove("roll");	//Remove roll from valid choices as can only roll once a turn
			}
			if(input.equals("accuse")){
				playerAccuse(p);
				break;	//break from the loop as an accusation is a turn ender
			}
		}
	}

	public static void playerRoll(Player p){
		Random r = new Random();
		int roll = r.nextInt(7);
		if(roll == 0) roll = r.nextInt(7);
		System.out.println("You rolled a " + roll);
		String moveCmd = readString("Please enter " + roll + " movement command(s), followed by enter.").toLowerCase();
		while(!validMoveCmd(moveCmd, roll, p)){
			moveCmd = readString("Invalid move! Try again");
		}
		System.out.println(p.toString() + " successfully moved!");
	}

	public static void playerAccuse(Player p){
		String wep = readString("What do you think is the murder weapon?");
		while(!wepCheck(wep)){
			wep = readString("Invalid weapon! Please enter a valid weapon");
		}
		String room = readString("What room do you think the murder occured in?");
		while(!roomCheck(room)){
			room = readString("Invalid room! Please enter a valid room");
		}
		String person = readString("Finally, who do you think commited the murder?");
		while(!personCheck(person)){
			person = readString("Invalid person! Please enter a valid person");
		}
	}

	public static boolean wepCheck(String wep){
		List<String> validWeps = new ArrayList<String>();
		validWeps.add("candlestick");
		validWeps.add("dagger");
		validWeps.add("lead pipe");
		validWeps.add("revolver");
		validWeps.add("rope");
		validWeps.add("spanner");
		if(validWeps.contains(wep.toLowerCase())) return true;
		return false;

	}

	public static boolean roomCheck(String room){
		List<String> validRooms = new ArrayList<String>();
		validRooms.add("kitchen");
		validRooms.add("dining room");
		validRooms.add("lounge");
		validRooms.add("hall");
		validRooms.add("study");
		validRooms.add("billiard room");
		validRooms.add("conservatory");
		validRooms.add("ball room");
		validRooms.add("library");
		if(validRooms.contains(room.toLowerCase())) return true;
		return false;
	}

	public static boolean personCheck(String person){
		List<String> validPersons = new ArrayList<String>();
		validPersons.add("Miss Scarlett");
		validPersons.add("Colonel Mustard");
		validPersons.add("Mrs. White");
		validPersons.add("The Reverend Green");
		validPersons.add("Mrs. Peacock");
		validPersons.add("Professor Plum");
		if(validPersons.contains(persons.toLowerCase())) return true;
		return false;
	}

	public static boolean validMoveCmd(String cmd,int roll, Player p){
		List<String> validChars = new ArrayList<String>();
		validChars.add("w");
		validChars.add("a");
		validChars.add("s");
		validChars.add("d");
		String[] commands = new String[roll];
		if(cmd.length() != roll) return false;
		for(int i = 0; i < cmd.length(); i++){
			String s = cmd.substring(i, i+1);
			if(!validChars.contains(s)) return false;
			commands[i] = s;
		}
		return p.validMove(commands);
	}

	public void startup() {
		System.out.println("********************************");
		System.out.println("       Welcome to Cluedo");
		System.out.println("********************************");
		System.out.println("");
		System.out.println("Rules of movement:");
		System.out.println("When you roll the dice, you will have a certain number of moves.");
		System.out.println("When prompted by the client, use w, a, s, d for forward, left, back and right respectively");
		System.out.println("with no spaces, followed by enter.");
		setPlayers();
		System.out.println("*********************************");
		System.out.println("");

		Queue<Player> players = game.getPlayers();
		while(players.size() > 0 && !gameIsOver){
			game.getBoard().displayTiles();
			Player currentPlayer = players.poll();
			startPlayerTurn(currentPlayer);

		}

	}
}
