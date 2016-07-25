package game;

import java.util.*;


public class Game {
	//The three cards that make the solution
	private Card charSol;
	private Card wepSol;
	private Card roomSol;
	
	protected List<Card> deck = new ArrayList<Card>();	//Full deck of cards
	
	public Game(){
		setDeck();
		setSolution();
		System.out.println(charSol + "    /  " + wepSol + "   /   " + roomSol);
		System.out.println(deck.size());
	}
	
	/**
	 * Populates the deck field with each card in the game
	 */
	public void setDeck(){
		//Add the characters
		 deck.add(new Card("Miss Scarlett", Card.Type.CHARACTER));
		 deck.add(new Card("Colonel Mustard", Card.Type.CHARACTER));
		 deck.add(new Card("Mrs. White", Card.Type.CHARACTER));
		 deck.add(new Card("The Reverend Green", Card.Type.CHARACTER));
		 deck.add(new Card("Mrs. Peacock", Card.Type.CHARACTER));
		 deck.add(new Card("Professor Plum", Card.Type.CHARACTER));
		 //Add the weapons
		 deck.add(new Card("Candlestick", Card.Type.WEAPON));
		 deck.add(new Card("Dagger", Card.Type.WEAPON));
		 deck.add(new Card("Lead Pipe", Card.Type.WEAPON));
		 deck.add(new Card("Revolver", Card.Type.WEAPON));
		 deck.add(new Card("Rope", Card.Type.WEAPON));
		 deck.add(new Card("Spanner", Card.Type.WEAPON));
		 //Add the rooms
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
	 * Shuffles the deck then sets the first instance of each type of card as the solution
	 */
	private void setSolution() {
		Collections.shuffle(deck);
		for(Card c : deck){
			if(charSol != null && wepSol != null && roomSol != null){ //Solutions have been chosen, remove solution cards from deck so they aren't dealt to players
				deck.remove(roomSol);
				deck.remove(charSol);
				deck.remove(wepSol);
				return;
			}
			if(charSol == null && c.getType().equals(Card.Type.CHARACTER)){
				charSol = c;
			}
			if(wepSol == null && c.getType().equals(Card.Type.WEAPON)){
				wepSol = c;
			}
			if(roomSol == null && c.getType().equals(Card.Type.ROOM)){
				roomSol = c;
			}
		}
		
	}


	public void setHands(Queue<Player> players) {
		Collections.shuffle(deck);
		Player firstPlayer = players.peek();
		while(true){
			if(players.peek().equals(firstPlayer) && deck.size() < players.size()){	//Left with remaining cards
				return;
			}
			Player p = players.poll();
			Card c = deck.get(0);
			p.addCard(c);
			deck.remove(c);
			players.offer(p);
		}
		
	}
}
