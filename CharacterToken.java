package game;

public class CharacterToken implements Locatable {

	private int characterId;
	private int playerId;
	private int xPos;
	private int yPos;

	public CharacterToken(int playerId, Tile spawn, int xPos, int yPos) {
		this.playerId = playerId;
		this.characterId = spawn.getCharacterNumber();
		
	}

	@Override
	public int[] getLocation() {
		int[] xy = new int[] { xPos, yPos };
		return xy;
	}

}
