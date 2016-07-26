package game;

public class Card {

	private Hand held;
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
}
