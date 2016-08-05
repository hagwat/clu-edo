package game;

import java.util.*;

public class Player {
private List<Card> hand;
private int character;	//Integer refers to character in Characters.txt
private String name;
private CharacterToken token;

  public Player(int characterNum, String playerName, Board board){
	  this.character = characterNum;
	  this.name = playerName;
	  hand = new ArrayList<Card>();
	  token = new CharacterToken(playerName, board, characterNum);
  }
/*
   public void promptMove(){


	  Tile current = board.getTile(xPos, yPos);
		if(!current.getType().equals(Tile.TileType.ROOM)){
			promptNormalMove();
		}else{
			promptRoomMove();
		}

	}
	public void promptNormalMove(){

	}

	public void promptRoomMove(){

	}
	public void nextTurn(){
		Player current = this.players.poll();
		current.move();
		current.suggest();
		nextTurn();
	}
*/


  public List<Card> getHand(){
	  return hand;
  }

  public String toString(){
	  return name;
  }

  public boolean validMove(String[] moves){
		return token.validMove(moves);
  }
  
  public void addCard(Card c){
	  hand.add(c);
  }


public Room getRoom() {
	return token.getRoom();
}

}
