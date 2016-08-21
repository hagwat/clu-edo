package game;

/**
 * Represents a card in the game of Cluedo.
 *
 */
public class Card {

	private String name;
	private Type type;

	public enum Type {
		WEAPON,
		CHARACTER,
		ROOM;
	}

	public Card(String name, Type type){
		this.name = name;
		this.type = type;
	}

	public Type getType() {
		return type;
	}
	
	@Override
	public String toString(){
		return this.name;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Card){
			Card c = (Card)o;
			if(c.name.equalsIgnoreCase(this.name) && c.type == this.type){
				return true;
			}
		}
		return false;
	}
}
