package game;

import java.util.*;

import ui.Controller;

import java.io.File;
import java.io.FileNotFoundException;

public class Client {

	private Game game;
	private Controller control;
	private boolean gameIsOver = false;
	private int numOfPlayers;
	private Queue<Player> players = new LinkedList<Player>();

	private List<String> validWeps = new ArrayList<String>();
	private List<String> validRooms = new ArrayList<String>();
	private List<String> validPersons = new ArrayList<String>();

	public Client(Game game) {
		this.game = game;
		setValidWeps();
		setValidRooms();
		setValidPersons();
	}

	// ***************************
	// PLAYER MOVES
	// ***************************


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
	public Object[] playerRefute(String wep, String room, String person) {
		Card wepCard = new Card(wep, Card.Type.WEAPON);
		Card roomCard = new Card(room, Card.Type.ROOM);
		Card personCard = new Card(person, Card.Type.CHARACTER);

		Queue<Player> players = new LinkedList<Player>(game.getPlayers());
		for (Player player : players) { // Does not include current player as
										// they have been polled off and not
										// offered back yet
			if (player.getHand().contains(personCard)) {
				return new Object[]{player.toString(), personCard}; // Card has been refuted, no need to carry on
			}
			if (player.getHand().contains(wepCard)) {
			return new Object[]{player.toString(), wepCard}; // Card has been refuted, no need to carry on
			}
			if (player.getHand().contains(roomCard)) {
				return new Object[]{player.toString(), roomCard}; // Card has been refuted, no need to carry on
			}
		}
		// Went through all players therefore cards must be in the deck
		if (game.getDeck().contains(personCard)) {
			return new Object[]{"Leftover Pile", personCard}; // Card has been refuted, no need to carry on
		}
		if (game.getDeck().contains(wepCard)) {
		return new Object[]{"Leftover Pile", wepCard}; // Card has been refuted, no need to carry on
		}
		if (game.getDeck().contains(roomCard)) {
		return new Object[]{"Leftover Pile", roomCard}; // Card has been refuted, no need to carry on
		}
		return null;	//Has checked all other players hands, deck and solution,
		//must be in accusing player's hand
																				//must be in accusing player's hand
	}

	// ***************************
	// SET UP METHODS
	// ***************************

	/**
	 * Gets the number of players and their names and sets them in the current
	 * Game.
	 */
	public void setPlayers() {
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


	public void setNumPlayers(int i){
		this.numOfPlayers = i;
	}

	public void addPlayer(Player p){
		players.offer(p);
	}

	public List<String> getValidWeps(){
		return validWeps;
	}

	public List<String> getValidRooms(){
		return validRooms;
	}

	public List<String> getValidCharacters(){
		return validPersons;
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

	public void startScreen() {
		control.handle("start");
	}

	public void addController(Controller c){
		this.control = c;
	}

	public Queue<Player> getPlayers(){
		return this.players;
	}
}
