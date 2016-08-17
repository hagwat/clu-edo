package game;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class TextClient {

	private Game game;
	private boolean gameIsOver = false;

	private List<String> validWeps = new ArrayList<String>();
	private List<String> validRooms = new ArrayList<String>();
	private List<String> validPersons = new ArrayList<String>();

	public TextClient(Game game) {
		this.game = game;
		setValidWeps();
		setValidRooms();
		setValidPersons();
	}

	// ***************************
	// PLAYER MOVES
	// ***************************

	/**
	 * Current player can make an accusation and either guess the solution or
	 * get eliminated
	 *
	 * @param p
	 * @return
	 */
	public boolean playerAccuse(Player p) {
		System.out.println("What do you think is the murder weapon?");
		displayWeaponOptions();
		String wep = readString("");
		while (!wepCheck(wep)) {
			wep = readString("Invalid weapon! Please enter a valid weapon");
		}
		System.out.println("What room do you think the murder occured in?");
		displayRoomOptions();
		String room = readString("");
		while (!roomCheck(room)) {
			room = readString("Invalid room! Please enter a valid room");
		}
		System.out.println("Finally, who do you think commited the murder?");
		displayPersonOptions();
		String person = readString("");
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

	/**
	 * When a player suggests or accuses the game goes in a clockwise direction
	 * to check if any players can refute. This method checks each player in a
	 * clock wise direction to see if they have any of the accused cards.
	 *
	 * @param wep
	 * @param room
	 * @param person
	 * @param p
	 */
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
		System.out.println("You accused a card that was in your own hand! ");	//Has checked all other players hands, deck and solution,
																				//must be in accusing player's hand
	}

	/**
	 * Rolls one dice and makes the user enter a move command. Then checks to
	 * see whether the command is valid.
	 *
	 * @param p
	 */
	public void playerRoll(Player p) {
		Random r = new Random();
		int roll = r.nextInt(6) + r.nextInt(6) + 2;
		int[] coords = p.getToken().getLocation();
		System.out.print(p.toString() + " is located at [" + coords[0] + " ," + coords[1] + "]");
		if (game.getBoard().getTile(coords[0], coords[1]).getRoom() != null) {
			Room currRoom = game.getBoard().getTile(coords[0], coords[1]).getRoom();
			System.out.print(" The " + currRoom.getName());
			if (currRoom.getWep() != null) {
				System.out.println(" contains the " + currRoom.getWep().getName() + ".");
			} else {
				System.out.println("is empty.");
			}
		} else {
			System.out.println();
		}

		System.out.println("You rolled a " + roll + ".");

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
			if (p.getToken().getRoom() != null) {
				return;
			}
			roll = roll - 1;
		}

		String moveCmd = readString("Please enter up to " + roll + " movement command(s) (w, a, s, d), followed by enter.").toLowerCase();
		while (!normalMoveCmd(moveCmd, roll, p)) {
			moveCmd = readString("Invalid move! Try again");
		}
		System.out.println(p.toString() + " successfully moved!");
	}

	/**
	 * Lets the player make a suggestion as to what the solution is. The
	 * suggested weapon and person are then moved into that room. They must be
	 * in a room to suggest.
	 *
	 * @param p
	 * @return
	 */
	public boolean playerSuggest(Player p) {
		String room = p.getRoom().getName();
		System.out.println("You are currently in the " + room);
		System.out.println("");
		System.out.println("What is the murder weapon?");
		displayWeaponOptions();
		String wep = readString("");
		while (!wepCheck(wep)) {
			wep = readString("Invalid weapon! Please enter a valid weapon");
		}
		System.out.println("Finally, who commited the murder?");
		displayPersonOptions();
		String person = readString("");
		while (!personCheck(person)) {
			person = readString("Invalid person! Please enter a valid person");
		}
		String finalise = readString("So you think it was " + person + " with the " + wep +
				" in the " + room + "(type YES to finalise your choice or NO to re-enter)");


		while (true) {
			if (finalise.equalsIgnoreCase("yes")) {
				if (game.accusation(wep, room, person)) {
					game.swapWeaponTokens(wep, p.getRoom());
					game.characterToRoom(p.getRoom(), person);
					System.out.println();
					System.out.println("Nobody can refute...");
					System.out.println();
					return false;
				} else {
					game.swapWeaponTokens(wep, p.getRoom());
					game.characterToRoom(p.getRoom(), person);
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

	// **********************
	// DISPLAY METHODS
	// **********************

	/**
	 * Prints each valid Person option to the console
	 */
	public void displayPersonOptions() {
		System.out.print("[");
		for (int i = 0; i < validPersons.size(); i++) {
			if (i < validPersons.size() - 1) {
				System.out.print(validPersons.get(i) + " / ");
			} else {
				System.out.print(validPersons.get(i) + "]");
			}
		}
	}

	/**
	 * Prints each valid Room option to the console
	 */
	public void displayRoomOptions() {
		System.out.print("[");
		for (int i = 0; i < validRooms.size(); i++) {
			if (i < validRooms.size() - 1) {
				System.out.print(validRooms.get(i) + " / ");
			} else {
				System.out.print(validRooms.get(i) + "]");
			}
		}
	}

	/**
	 * Prints each valid Weapon option to the console
	 */
	public void displayWeaponOptions() {
		System.out.print("[");
		for (int i = 0; i < validWeps.size(); i++) {
			if (i < validWeps.size() - 1) {
				System.out.print(validWeps.get(i) + " / ");
			} else {
				System.out.print(validWeps.get(i) + "]");
			}
		}
	}

	/**
	 * Prints the cards in the leftover pile to the console. N.B There is no
	 * leftovers if there is 3 or 6 players.
	 */
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

	/**
	 * Prints the cards in the player's hand to the console.
	 *
	 * @param p
	 */
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

	// ***************************
	// SET UP METHODS
	// ***************************

	/**
	 * Gets the number of players and their names and sets them in the current
	 * Game.
	 */
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
		game.setHands();
	}

	public void setValidPersons() {
		validPersons.add("Miss Scarlett");
		validPersons.add("Colonel Mustard");
		validPersons.add("Mrs. White");
		validPersons.add("The Reverend Green");
		validPersons.add("Mrs. Peacock");
		validPersons.add("Professor Plum");
	}

	/**
	 * Initialises the field with all valid Rooms. This is a helper method for
	 * displaying options.
	 */
	public void setValidRooms() {
		validRooms.add("Kitchen");
		validRooms.add("Dining Room");
		validRooms.add("Lounge");
		validRooms.add("Hall");
		validRooms.add("Study");
		validRooms.add("Billiard Room");
		validRooms.add("Conservatory");
		validRooms.add("Ball Room");
		validRooms.add("Library");
	}

	/**
	 * Initialises the field with all valid Weapons. This is a helper method for
	 * displaying options.
	 */
	public void setValidWeps() {
		validWeps.add("Candlestick");
		validWeps.add("Dagger");
		validWeps.add("Lead Pipe");
		validWeps.add("Revolver");
		validWeps.add("Rope");
		validWeps.add("Spanner");
	}

	/**
	 * Gets the name of each player
	 *
	 * @return
	 */
	public static List<String> getPlayerNames() {
		int players = readInt("How many players? (Must be between 3-6)");
		while (players > 6 || players < 3) {
			System.out.println("Must be between 3 and 6!");
			players = readInt("How many players? (Must be between 3-6)");
		}
		List<String> playersNames = new ArrayList<String>();
		for (int i = 1; i <= players; i++) {
			playersNames.add(readString("Player " + i + " name?"));
		}
		return playersNames;
	}

	/**
	 * Gets the characters of each player using the integer they inputed and the
	 * file with all characters and corresponding integers.
	 *
	 * @param names
	 * @return
	 */
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
			while (tokens.contains(number) || !checkTokenNum(number)) {
				if (tokens.contains(number)) {
					number = readInt("Token already chosen! Try again");
				}else if(!checkTokenNum(number)){
					number = readInt("Invalid selection! Try again");
				}
			}
			tokens.add(number);
		}

		return tokens;
	}

	// ************************
	// CHECK METHODS
	// ************************

	/**
	 * Check that the chosen character number is valid.
	 */
	public static boolean checkTokenNum (int num){
		return (num<=6&&num>=1);
	}

	/**
	 * Check that the given move is valid.
	 *
	 * @param cmd
	 * @param roll
	 * @param p
	 * @return
	 */
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

	/**
	 * Checks to see if the move is valid when the starting position is in a
	 * room
	 *
	 * @param moveCmd
	 * @param exits
	 * @param p
	 * @return
	 */
	public boolean roomMoveCmd(String moveCmd, Map<String, int[]> exits, Player p) {
		if (exits.containsKey(moveCmd)) {
			p.getToken().roomMove(exits.get(moveCmd));
			return true;
		}
		return false;
	}

	/**
	 * Checks the string is a valid person
	 *
	 * @param person
	 * @return
	 */
	public boolean personCheck(String person) {
		List<String> personsLowerCase = new ArrayList<String>();
		for (String s : validPersons)
			personsLowerCase.add(s.toLowerCase());
		if (personsLowerCase.contains(person.toLowerCase()))
			return true;
		return false;
	}

	/**
	 * Check the string is valid room
	 *
	 * @param room
	 * @return
	 */
	public boolean roomCheck(String room) {
		List<String> roomsLowerCase = new ArrayList<String>();
		for (String s : validRooms)
			roomsLowerCase.add(s.toLowerCase());
		if (roomsLowerCase.contains(room.toLowerCase()))
			return true;
		return false;

	}

	/**
	 * Check the string is a valid Weapon
	 *
	 * @param wep
	 * @return
	 */
	public boolean wepCheck(String wep) {
		List<String> wepsLowerCase = new ArrayList<String>();
		for (String s : validWeps)
			wepsLowerCase.add(s.toLowerCase());
		if (wepsLowerCase.contains(wep.toLowerCase()))
			return true;
		return false;

	}

	// ******************
	// HELPER METHODS
	// ******************

	/**
	 * Returns a string from the user input
	 *
	 * @param msg
	 * @return
	 */
	public static String readString(String msg) {
		System.out.println(msg);
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		return s;
	}

	/**
	 * Returns an int from the user input
	 *
	 * @param msg
	 * @return
	 */
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

	// *********************
	// STARTUP METHODS
	// *********************

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
		if (p.getRoom() != null) {
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
			if (input.equals("suggest") && p.getRoom() != null){
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
/**
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
		game.setSpareTokens();
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
				if (players.size() < 1) {
					System.out.println();
					System.out.println("***********GAME OVER************");
					return;
				}
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
	*/

	public void startScreen(){

	}
}
