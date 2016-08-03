package game;

public class Player {
private Hand hand;
private int character;	//Integer refers to character in Characters.txt
private String name;

  public Player(int character, String name){
	  this.character = character;
	  this.name = name;
  }

  public Hand getHand(){
	  return hand;
  }

  public String toString(){
	  return name;
  }
}
