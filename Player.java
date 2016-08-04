package game;

public class Player {
private Hand hand;
private int character;	//Integer refers to character in Characters.txt
private String name;
private CharacterToken token;

  public Player(int characterNum, String playerName, Board board){
	  this.character = characterNum;
	  this.name = playerName;
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


  public Hand getHand(){
	  return hand;
  }

  public String toString(){
	  return name;
  }
}
