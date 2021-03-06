package game;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Keeps track of all the information of the current game of Cluedo. For example, players, solution, deck etc.
 */
public class Game {
	// The three cards that make the solution
	private Card charSol;
	private Card wepSol;
	private Card roomSol;

	private List<WeaponToken> allWeps;

	private Board board;
	private Client client;

	private Queue<Player> players = new LinkedList<Player>();
	private List<Card> deck = new ArrayList<Card>(); // Full deck of cards

	public Game() {
		setDeck();
		setSolution();
		createBoard();
		setWeapons();
		createClient();
	}



	/**
	 * Populates the deck field with each card in the game
	 */
	public void setDeck() {
		// Add the characters
		deck.add(new Card("Miss Scarlett", Card.Type.CHARACTER));
		deck.add(new Card("Colonel Mustard", Card.Type.CHARACTER));
		deck.add(new Card("Mrs. White", Card.Type.CHARACTER));
		deck.add(new Card("The Reverend Green", Card.Type.CHARACTER));
		deck.add(new Card("Mrs. Peacock", Card.Type.CHARACTER));
		deck.add(new Card("Professor Plum", Card.Type.CHARACTER));
		// Add the weapons
		deck.add(new Card("Candlestick", Card.Type.WEAPON));
		deck.add(new Card("Dagger", Card.Type.WEAPON));
		deck.add(new Card("Lead Pipe", Card.Type.WEAPON));
		deck.add(new Card("Revolver", Card.Type.WEAPON));
		deck.add(new Card("Rope", Card.Type.WEAPON));
		deck.add(new Card("Spanner", Card.Type.WEAPON));
		// Add the rooms
		deck.add(new Card("Kitchen", Card.Type.ROOM));
		deck.add(new Card("Ball Room", Card.Type.ROOM));
		deck.add(new Card("Conservatory", Card.Type.ROOM));
		deck.add(new Card("Dining Room", Card.Type.ROOM));
		deck.add(new Card("Billiard Room", Card.Type.ROOM));
		deck.add(new Card("Library", Card.Type.ROOM));
		deck.add(new Card("Lounge", Card.Type.ROOM));
		deck.add(new Card("Hall", Card.Type.ROOM));
		deck.add(new Card("Study", Card.Type.ROOM));
	}

	/**
	 * Shuffles the deck then sets the first instance of each type of card as
	 * the solution
	 */
	private void setSolution() {
		Collections.shuffle(deck);
		for (Card c : deck) {
			if (charSol != null && wepSol != null && roomSol != null) {
				// Solutions have been chosen, remove solution cards from deck
				// so
				// they aren't dealt to players
				deck.remove(roomSol);
				deck.remove(charSol);
				deck.remove(wepSol);
				return;
			}
			if (charSol == null && c.getType().equals(Card.Type.CHARACTER)) {
				charSol = c;
			}
			if (wepSol == null && c.getType().equals(Card.Type.WEAPON)) {
				wepSol = c;
			}
			if (roomSol == null && c.getType().equals(Card.Type.ROOM)) {
				roomSol = c;
			}
		}

	}

	/**
	 * Takes the remaining cards in the deck, shuffles them then deals them evenly to the players, leaving
	 * the remainder (if any) in the leftovers pile.
	 */
	public void setHands() {
		Collections.shuffle(deck);
		Player firstPlayer = players.peek();
		while (deck.size() > 0) {
			if (players.peek().equals(firstPlayer) && deck.size() < players.size()) {
				// Left with remaining cards
				return;
			}
			Player p = players.poll();
			Card c = deck.get(0);
			p.addCard(c);
			deck.remove(c);
			players.offer(p);
		}

	}

	public void setWeapons(){
		Room[] rooms = board.getRooms();
		List<Room> roomList = new ArrayList<Room>();

		for(int i = 0; i< rooms.length; i++){
			roomList.add(rooms[i]);
		}

		allWeps = new ArrayList<WeaponToken>();
		//Input all weapon names
		allWeps.add(new WeaponToken("Candlestick"));
		allWeps.add(new WeaponToken("Dagger"));
		allWeps.add(new WeaponToken("Lead Pipe"));
		allWeps.add(new WeaponToken("Revolver"));
		allWeps.add(new WeaponToken("Rope"));
		allWeps.add(new WeaponToken("Spanner"));
		//last 3 rooms will not have a weapon
		//Input all weapons into random rooms
		Collections.shuffle(roomList);
		for(int i = 0; i<allWeps.size();i++){
			roomList.get(i).setWep(allWeps.get(i));
			}
	}

	public void setSpareTokens(){
		for(int i = 1; i<=6; i++){
			boolean found = false;
			for(CharacterToken t: board.getCharacterTokens()){
				if(t.getId()==i){
					found = true;
				}
			}
			if(found == false){
			 board.getCharacterTokens().add(new CharacterToken(board, i));
			}
		}
		for(CharacterToken t: board.getCharacterTokens()){
		}

	}

	/**
	 * When a player makes a suggestion, the suggested weapon and person are moved into the current room (weapons
	 * swapped if there is already one in there). This method carries this action out.
	 * @param wep
	 * @param room
	 */
	public void swapWeaponTokens(String wep, Room room) {
		if (room.getWep() == null) {// if room has no weapon
			Room[] rooms = board.getRooms();
			for (int i = 0; i < rooms.length; i++) {// for each room
				if (rooms[i].getWep() != null) {
					if (rooms[i].getWep().getName().equalsIgnoreCase(wep)) {
						// if that room has the needed weapon
						room.setWep(rooms[i].getWep());
						rooms[i].setWep(null);
						return;
					}
				}
			}
		} else if (room.getWep().getName().equalsIgnoreCase(wep)) {
			return;
		} else {

			Room[] rooms = board.getRooms();
			for (int i = 0; i < rooms.length; i++) {
				if (room.getWep() != null) {
					if (rooms[i].getWep().getName().equalsIgnoreCase(wep)) {
						WeaponToken wrongWep = room.getWep();
						room.setWep(rooms[i].getWep());
						rooms[i].setWep(wrongWep);
						return;
					}

				}
			}
		}
	}

	public void characterToRoom(Room here, String tokenName) {
		CharacterToken person = null;
		tokenName = tokenName.toLowerCase();
		for (CharacterToken t : board.getCharacterTokens()) {
			if (t.getCharacterName().equalsIgnoreCase(tokenName)) {
				person = t;
			}
		}
		if(person == null){
		}
		person.roomMove(board.getRoomLocation(here.getName()));
	}

	/**
	 * Uses the parameters to determine whether this accusation matches the solution.
	 * @param wep
	 * @param room
	 * @param person
	 * @return
	 */
	public boolean accusation(String wep, String room, String person) {
		if (wepSol.toString().equalsIgnoreCase(wep) && roomSol.toString().equalsIgnoreCase(room)
				&& charSol.toString().equalsIgnoreCase(person)) {
			return true;
		}
		return false;
	}

/**
	 * Creates a new instance of Board
	 */
	public void createBoard() {
		this.board = new Board();
	}

	/**
	 * Creates a new instance of Client
	 */
	public void createClient() {
		client = new Client(this);
	}

	//*********************
	//SETTERS & GETTERS
	//*********************

	public void setPlayers(Queue<Player> players) {
		this.players = players;

		//make sure we have all the tokens
		setSpareTokens();
	}

	public List<WeaponToken> getWeapons(){
		return this.allWeps;
	}

	public Queue<Player> getPlayers() {
		return this.players;
	}

	public Client getClient() {
		return client;
	}

	public Board getBoard() {
		return board;
	}

	public List<Card> getDeck() {
		return deck;
	}

	public static void main(String[] args) {
		new Game();
	}
}
