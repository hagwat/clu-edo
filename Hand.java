package game;

import java.util.List;

public class Hand {
	private Player player;
	private List<Card> cards;

	public void addCard(Card c) {
		cards.add(c);
	}
}