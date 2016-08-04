package game;

public class CharacterToken implements Locatable {

	private int characterId;
	private int playerId;
	private int xPos;
	private int yPos;
	private Board board;

	public CharacterToken(int playerId, Board board, int xPos, int yPos) {
		this.playerId = playerId;
		this.board = board;
		this.xPos = xPos;
		this.yPos = yPos;
		this.characterId = board.getTile(xPos, yPos).getCharacterNumber();

	}

	@Override
	public int[] getLocation() {
		int[] xy = new int[] { xPos, yPos };
		return xy;
	}

	public void nextTurn(){
		Player current = this.players.poll();
		current.move();
		current.suggest();
		nextTurn();
	}
	



}