package game;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class TextClient {

	private Game game;
	private boolean gameIsOver = false;

	public TextClient(Game game) {
		this.game = game;

	}

	public static String readString(String msg) {
		System.out.println(msg);
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		return s;
	}

	public static int readInt(String msg) {
		System.out.println("");
		System.out.println(msg);
		try {
			Scanner sc = new Scanner(System.in);
			int i = sc.nextInt();
			return i;

		} catch (InputMismatchException e) {
			System.out.println("Must be a number!");
			return readInt(msg);
		}

	}

	public void setPlayers() {
		List<String> names = getPlayerNames();
		List<Integer> tokens = getPlayerTokens(names);
		Queue<Player> players = new LinkedList<Player>();
		if (names.size() != tokens.size()) {
			throw new IllegalStateException("Number of players not equal to number of tokens.");
		}
		for (int i = 0; i < names.size(); i++) {
			players.offer(new Player(tokens.get(i), names.get(i), game.getBoard()));
		}
		game.setPlayers(players);
		game.setHands(players);
	}

	public static List<String> getPlayerNames() {
		int players = readInt("How many players? (Must be between 3-6)");
		while (players > 6 || players < 1) {//change back
			System.out.println("Must be between 3 and 6!");
			players = readInt("How many players? (Must be between 3-6)");
		}
		List<String> playersNames = new ArrayList<String>();
		for (int i = 1; i <= players; i++) {
			playersNames.add(readString("Player " + i + " name?"));
		}
		return playersNames;
	}

	public static List<Integer> getPlayerTokens(List<String> names) {
		try {
			Scanner sc = new Scanner(new File("src/game/characters.txt"));
			System.out.println("****************CHARACTERS*****************");
			System.out.println("");
			while (sc.hasNextLine()) {
				System.out.println(sc.nextLine());
			}
			System.out.println("");
			System.out.println("*******************************************");
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		List<Integer> tokens = new ArrayList<Integer>();

		for (String s : names) {
			int number = readInt(s + ", please choose a number that refers to your character choice!");
			while (tokens.contains(number)) {
				number = readInt("Token already chosen! Try again");
			}
			tokens.add(number);
		}
		return tokens;
	}

	public boolean startPlayerTurn(Player p) {
		System.out.println("");
		System.out.println("********** " + p.toString() + "'s Turn **********");
		System.out.println("");
		playerRoll(p);
		System.out.println("Options:");
		System.out.println("- Make an Accusation --- [accuse]");
		System.out.println("- Display Hand --- [hand]");
		System.out.println("- Make Suggestion --- [suggest]");
		System.out.println("- Show Leftover Pile --- [leftovers]");
		System.out.println("- End Your Turn --- [end]");
		List<String> options = new ArrayList<String>();
		options.add("accuse");
		options.add("hand");
		options.add("leftovers");
		options.add("end");
		if(p.getRoom() != null){
			options.add("suggest");
		}
		while (true) {
			System.out.println("Your options are:");
			System.out.print("[");
			for (int i = 0; i < options.size(); i++) {
				if (i == options.size() - 1) {
					System.out.println(" " + options.get(i) + "]");
				} else if (i == 0) {
					System.out.print(options.get(i) + " /");
				} else {
					System.out.print(" " + options.get(i) + " /");
				}
			}
			String input = readString("Please type in your choice!").toLowerCase();
			while (!options.contains(input)) {
				System.out.println("Invalid move! Your options are:");
				System.out.print("[");
				for (int i = 0; i < options.size(); i++) {
					if (i == options.size() - 1) {
						System.out.println(" " + options.get(i) + "]");
					} else if (i == 0) {
						System.out.print(options.get(i) + " /");
					} else {
						System.out.print(" " + options.get(i) + " /");
					}
				}
				input = readString("Please type in a valid choice!");
			}
			if (input.equals("accuse")) {
				return playerAccuse(p); // break from the loop as an accusation
										// is a turn ender

			}
			if (input.equals("hand")) {
				showPlayerHand(p);
			}
			if (input.equals("suggest")) { // && p.getRoom() != null ========>
											// FOR TESTING // PUT BACK IN IF
											// CONDITION
				return playerSuggest(p);
			} else if (input.equals("suggest") && p.getRoom() == null) {
				System.out.println("");
				System.out.println("Cannot suggest, you aren't in a room!");
				System.out.println("");
			}
			if (input.equals("leftovers")) {
				showLeftovers();
			}
			if (input.equals("end")) {
				return false;
			}
		}
	}

	public boolean playerSuggest(Player p) {
		String room = p.getRoom().getName(); 
		System.out.println("You are currently in the " + room);
		System.out.println("");
		String wep = readString("What is the murder weapon?");
		while (!wepCheck(wep)) {
			wep = readString("Invalid weapon! Please enter a valid weapon");
		}
		String person = readString("Finally, who commited the murder?");
		while (!personCheck(person)) {
			person = readString("Invalid person! Please enter a valid person");
		}
		String finalise = readString("So you think it was " + person + " with the " + wep + // ADD
																							// IN
																							// CANCEL???
				" in the " + room + "(type YES to finalise your choice or NO to re-enter)");
		while (true) {
			if (finalise.equalsIgnoreCase("yes")) {
				if (game.accusation(wep, room, person)) {
					game.swapWeaponTokens(wep, p.getRoom());
					System.out.println("");
					System.out.println("*******************************");
					System.out.println("Congratulations " + p.toString() + "! You have solved the murder!");
					System.out.println("Game over, " + p.toString() + " is the winner!");
					System.out.println("*******************************");
					gameIsOver = true;
					return false;
				} else {
					game.swapWeaponTokens(wep, p.getRoom());
					System.out.println("");
					System.out.println("Sorry " + p.toString() + " that is incorrect!");
					playerRefute(wep, room, person, p);
					return false;
				}
			} else if (finalise.equalsIgnoreCase("no")) {
				return playerSuggest(p);
			} else {
				finalise = readString("Invalid input; please type in YES or NO!");
			}
		}
	}

	public void showLeftovers() {
		System.out.println("");
		if (game.getDeck().size() == 0) {
			System.out.println("Leftovers pile is empty!!");
			System.out.println("");
		} else {
			for (Card c : game.getDeck()) {
				System.out.println("- " + c);
				System.out.println("");
			}
		}
	}

	public void showPlayerHand(Player p) {
		readString("Press ENTER when you would like your hand to display (keep your hand private)");
		for (Card c : p.getHand()) {
			System.out.println("- " + c);
			System.out.println("");
		}
		readString("Press ENTER when you have finished looking!!!");
		for (int i = 0; i < 30; i++) {
			System.out.println(""); // attempt to fill console with blank space
									// to avoid cheating
		}
	}

	public void playerRoll(Player p) {
		Random r = new Random();
		int roll = r.nextInt(6) + 1;
		int [] coords = p.getToken().getLocation(); 
		System.out.print(p.toString() + " is located at [" + coords[0] + " ," + coords[1] + "]");
		if(game.getBoard().getTile(coords[0], coords[1]).getRoom()!=null){
			Room currRoom = game.getBoard().getTile(coords[0], coords[1]).getRoom();
			System.out.print(" The "+currRoom.getName());
			if(currRoom.getWep()!=null){
				System.out.println(" contains the "+currRoom.getWep().getName()+".");
			}else{
				System.out.println("is empty.");
			}
		}else{
			System.out.println();
		}

		System.out.println("You rolled a " + roll+".");

		Room room = p.getToken().getRoom();
		if (room != null) {// if player is in a room
			Map<String, int[]> exits = room.getExits();
			for (String s : exits.keySet()) {
				System.out.print(s);
				System.out.print(", ");
			}
			System.out.println();
			String moveCmd = readString("Please input an exit.");
			while (!roomMoveCmd(moveCmd, exits, p)) {
				moveCmd = readString("Invalid move! Try again");
			}
			System.out.println(p.toString() + " successfully moved!");
			if(p.getToken().getRoom()!=null){
				return;
			}
			roll=roll-1;
		}

		String moveCmd = readString("Please enter " + roll + " movement command(s), followed by enter.").toLowerCase();
		while (!normalMoveCmd(moveCmd, roll, p)) {
			moveCmd = readString("Invalid move! Try again");
		}
		System.out.println(p.toString() + " successfully moved!");
	}

	public boolean roomMoveCmd(String moveCmd, Map<String, int[]> exits, Player p) {
		// exits.containsKey();
		if(exits.containsKey(moveCmd)){
			p.getToken().roomMove(exits.get(moveCmd));
			return true;
		}
		return false;
	}

	public boolean normalMoveCmd(String cmd, int roll, Player p) {
		List<String> validChars = new ArrayList<String>();
		validChars.add("w");
		validChars.add("a");
		validChars.add("s");
		validChars.add("d");
		String[] commands = new String[cmd.length()];
		if (cmd.length() > roll)
			return false;
		for (int i = 0; i < cmd.length(); i++) {
			String s = cmd.substring(i, i + 1);
			if (!validChars.contains(s))
				return false;
			commands[i] = s;
		}
		return p.validMove(commands);
	}

	public boolean playerAccuse(Player p) {
		System.out.println(game.solutionToString());
		String wep = readString("What do you think is the murder weapon?");
		while (!wepCheck(wep)) {
			wep = readString("Invalid weapon! Please enter a valid weapon");
		}
		String room = readString("What room do you think the murder occured in?");
		while (!roomCheck(room)) {
			room = readString("Invalid room! Please enter a valid room");
		}
		String person = readString("Finally, who do you think commited the murder?");
		while (!personCheck(person)) {
			person = readString("Invalid person! Please enter a valid person");
		}
		if (game.accusation(wep, room, person)) {
			System.out.println("");
			System.out.println("*******************************");
			System.out.println("Congratulations " + p.toString() + "! You have solved the murder!");
			System.out.println("Game over, " + p.toString() + " is the winner!");
			System.out.println("*******************************");
			gameIsOver = true;
			return false;
		} else {
			playerRefute(wep, room, person, p);
			return true;
		}
	}

	public boolean wepCheck(String wep) {
		for (WeaponToken weapon : game.getWeapons()) {
			if (weapon.getName().equals(wep)) {
				return true;
			}
		}
		return false;
	}

	public static boolean roomCheck(String room) {
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
		if (validRooms.contains(room.toLowerCase()))
			return true;
		return false;
	}

	public static boolean personCheck(String person) {
		List<String> validPersons = new ArrayList<String>();
		validPersons.add("miss scarlett");
		validPersons.add("colonel mustard");
		validPersons.add("mrs. white");
		validPersons.add("the reverend green");
		validPersons.add("mrs. peacock");
		validPersons.add("professor plum");
		if (validPersons.contains(person.toLowerCase()))
			return true;
		return false;
	}

	public void playerRefute(String wep, String room, String person, Player p) {
		Card wepCard = new Card(wep, Card.Type.WEAPON);
		Card roomCard = new Card(room, Card.Type.ROOM);
		Card personCard = new Card(person, Card.Type.CHARACTER);

		Queue<Player> players = new LinkedList<Player>(game.getPlayers());
		System.out.println("");
		for (Player player : players) { // Does not include current player as
										// they have been polled off and not
										// offered back yet
			if (player.getHand().contains(personCard)) {
				System.out.println(player.toString() + " refutes and shows " + person + "!");
				return; // Card has been refuted, no need to carry on
			}
			if (player.getHand().contains(wepCard)) {
				System.out.println(player.toString() + " refutes and shows " + wep + "!");
				return; // Card has been refuted, no need to carry on
			}
			if (player.getHand().contains(roomCard)) {
				System.out.println(player.toString() + " refutes and shows " + room + "!");
				return; // Card has been refuted, no need to carry on
			}
		}
		// Went through all players therefore cards must be in the deck
		if (game.getDeck().contains(personCard)) {
			System.out.println(personCard + " is in the leftover pile.");
			return; // Card has been refuted, no need to carry on
		}
		if (game.getDeck().contains(wepCard)) {
			System.out.println(wepCard + " is in the leftover pile.");
			return; // Card has been refuted, no need to carry on
		}
		if (game.getDeck().contains(roomCard)) {
			System.out.println(roomCard + " is in the leftover pile.");
			return; // Card has been refuted, no need to carry on
		}
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
		System.out.println();
		System.out.println("Text client:");
		System.out.println("The text client drives the game and will ask you for inputs during your turn.");
		System.out.println("The inputs are not case-sensitive, but they do require correct spelling.");
		System.out.println();
		System.out.println("Board display:");
		System.out.println("The board display is text based. It does not update - however the client regularly reminds you of");
		System.out.println("your location with co-ordinates.");
		System.out.println();
		System.out.println("Board key:");
		System.out.println("- n = Inaccessible");
		System.out.println("- c = Corridor");
		System.out.println("- k = Kitchen");
		System.out.println("- b = Ball Room");
		System.out.println("- o = Conservatory");
		System.out.println("- i = Billiard Room");
		System.out.println("- l = Library");
		System.out.println("- s = Study");
		System.out.println("- h = Hall");
		System.out.println("- u = Lounge");
		System.out.println("- d = Dining Room");
		System.out.println("- D = door");
		System.out.println("- T# = Teleporter");
		System.out.println("- S# = Starting point");
		System.out.println();
		System.out.println("Let's begin...");
		setPlayers();

		Queue<Player> players = game.getPlayers();
		while (players.size() > 0 && !gameIsOver) {
			System.out.println("");
			System.out.println("***************GAME BOARD******************");
			System.out.println("");
			game.getBoard().displayTiles();
			Player currentPlayer = players.poll();
			boolean eliminated = startPlayerTurn(currentPlayer);
			if (eliminated) {
				System.out.println("");
				System.out.println(currentPlayer.toString() + " has been eliminated!!!");
			} else {
				players.offer(currentPlayer);
			}
			System.out.println("");
			System.out.println("End of " + currentPlayer.toString() + "'s turn. " + "Starting "
					+ players.peek().toString() + "'s turn!");
			System.out.println("");
			readString(players.peek().toString() + "! Press ENTER when you are ready for your turn!");
		}

	}
}
