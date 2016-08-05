package game;

public class Player {
	private Hand hand;
	private int character; // Integer refers to character in Characters.txt
	private String name;
	private CharacterToken token;

	public Player(int characterNum, String playerName, Board board) {
		this.character = characterNum;
		this.name = playerName;
		token = new CharacterToken(playerName, board, characterNum);
	}

	public Hand getHand() {
		return hand;
	}
	public CharacterToken getToken(){
		return token;
	}

	public String toString() {
		return name;
	}

	public boolean validMove(String[] moves) {
		return token.validMove(moves);
	}

}
