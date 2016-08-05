package game;

import java.util.*;

/**
 * Top level class.
 */
public class Game {
	// The three cards that make the solution
	private Card charSol;
	private Card wepSol;
	private Card roomSol;

	private Board board;
	private TextClient client;
	
	private Set<Room> wepRooms = new HashSet<Room>();
	private Queue<Player> players = new LinkedList<Player>();
	private List<Card> deck = new ArrayList<Card>(); // Full deck of cards

	public Game() {
		setDeck();
		setSolution();
		createBoard();
		setWeapons();
		createTextClient();
		client.startup();
	}

	public Game(String msg) {
		setDeck();
		setSolution();
		createBoard();
		System.out.println("test");
		createTextClient();
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

	public void setHands(Queue<Player> players) {
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
		List<String> rooms = new ArrayList<String>();
		List<Weapon> allWeps = new ArrayList<Weapon>();
		//Input all weapon names
		allWeps.add(new Weapon("Candlestick"));
		allWeps.add(new Weapon("Dagger"));
		allWeps.add(new Weapon("Lead Pipe"));
		allWeps.add(new Weapon("Revolver"));
		allWeps.add(new Weapon("Rope"));
		allWeps.add(new Weapon("Spanner"));
		allWeps.add(null);
		allWeps.add(null);
		allWeps.add(null);	//last 3 rooms will not have a weapon
		//Input all rooms
		rooms.add("Library");
		rooms.add("Study");
		rooms.add("Hall");
		rooms.add("Lounge");
		rooms.add("Dining Room");
		rooms.add("Kitchen");
		rooms.add("Ball Room");
		rooms.add("Conservatory");
		rooms.add("Billiard Room");
		//Input all weapons into random rooms
		Collections.shuffle(rooms);
		for(int i = 0; i < allWeps.size(); i++){
			wepRooms.add(new Room(rooms.get(i), allWeps.get(i)));
		}
	}

	public void swapWeaponTokens(String wep, Room room){
		Weapon weaponAccused = new Weapon(wep);
		Room accusedWeaponRoom = null;
		//Find accused weapon and weapon in current room
		for(Room r : wepRooms){
			if(r.equals(room) && r.getWep() != null && r.getWep().equals(weaponAccused)){
				return;	//weapon is already in the room
			}
			if(r.getWep() != null && r.getWep().toString().equals(wep)){
				accusedWeaponRoom = r;
			}
		}
		//Swap the weapons
		Weapon temp = room.getWep();
		room.setWep(weaponAccused);
		accusedWeaponRoom.setWep(temp);
	}

	public void setPlayers(Queue<Player> players) {
		this.players = players;
	}

	public Queue<Player> getPlayers() {
		return this.players;
	}

	public void createBoard() {
		this.board = new Board();
	}

	public void createTextClient() {
		client = new TextClient(this);
	}

	public TextClient getTextClient() {
		return client;
	}

	public Board getBoard() {
		return board;
	}

	public boolean accusation(String wep, String room, String person) {
		if (wepSol.toString().equalsIgnoreCase(wep) && roomSol.toString().equalsIgnoreCase(room)
				&& charSol.toString().equalsIgnoreCase(person)) {
			return true;
		}
		return false;
	}

	public String solutionToString() {
		return charSol + " " + roomSol + " " + wepSol;
	}

	public List<Card> getDeck() {
		return deck;
	}

	public static void main(String[] args) {
		new Game();
	}
}
