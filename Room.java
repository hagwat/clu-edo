package game;

public class Room implements Locatable{

	private String name;
	
	public Room(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}
	
}
