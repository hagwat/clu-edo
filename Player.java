package game;

import java.util.*;

/**
 * Represents a player in the game of Cluedo. A player is the user that is inputting actions.
 *
 */
public class Player {

	private List<Card> hand;	//This player's hand
	private int character; // Integer refers to character in Characters.txt
	private String name;	//This player's name
	private CharacterToken token;	//This players CharacterToken

	public Player(int characterNum, String playerName, Board board) {
		this.character = characterNum;
		this.name = playerName;
		hand = new ArrayList<Card>();
		token = new CharacterToken(playerName, board, characterNum);
		board.getGame().getTokens().add(token);
	}

	public Player(int characterNum, String playerName, Board board, int x, int y) {
		this.character = characterNum;
		this.name = playerName;
		hand = new ArrayList<Card>();
		token = new CharacterToken(playerName, board, characterNum);
		token.setPos(x, y);
	}



	public List<Card> getHand() {
		return hand;
	}

	public String toString() {
		return name;
	}

	/**
	 * Returns a boolean on whether the array of moves passed is valid for this player.
	 * @param moves
	 * @return
	 */
	public boolean validMove(String[] moves) {
		return token.validMove(moves);
	}

	/**
	 * Add Card c to the player's hand
	 * @param c
	 */
	public void addCard(Card c) {
		hand.add(c);
	}

	public Room getRoom() {
		return token.getRoom();
	}

	public CharacterToken getToken() {
		return token;
	}

}
