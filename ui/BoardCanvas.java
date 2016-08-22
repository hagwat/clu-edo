package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import game.CharacterToken;
import game.Room;
import game.Tile;
import game.WeaponToken;
import game.Tile.TileType;

public class BoardCanvas extends JPanel implements MouseListener {

	private Tile[][] tiles;
	private List<CharacterToken> characterTokens;
	private List<WeaponToken> weapons;
	private Room[] rooms; // only needed for the location of weapons.
	private static final int TILE_WIDTH = 25;
	private static final int TILE_HEIGHT = 22;

	@SuppressWarnings("unchecked")
	public BoardCanvas(Object[] arg){
		//if arg is null or not a 2D array of Tiles
		if (arg == null) {
			throw new IllegalArgumentException("No argument to construct board.");
		}
		if (!(arg[0] instanceof Tile[][])) {
			throw new IllegalArgumentException("Invalid argument to display tiles.");
		}
		if (!(arg[1] instanceof List<?>)) {
			throw new IllegalArgumentException("Invalid argument to display player tokens .");
		}
		setPreferredSize(new Dimension(1000, 600));
		tiles = (Tile[][]) arg[0];
		characterTokens = (List<CharacterToken>) arg[1];
		weapons = (List<WeaponToken>) arg[2];
		rooms = (Room[]) arg[3];
	}

	/**
	 * Paints each element of this canvas. Called by a repaint method somewhere else.
	 */
	public void paint(Graphics g) {
		paintTiles(g);
		paintCharacters(g);
		paintWeapons(g);

		g.drawString("A rogue String has infected your assignment!", 250, 200);
	}

	public void paintWeapons(Graphics g) {
		// paint each token
		for (WeaponToken weapon : weapons) {
			paintWeapon(g, weapon);
		}
	}

	/**
	 * Draws a weapon by finding the appropriate image file and scaling it to
	 * fit a 2x2 section of tiles.
	 */
	public void paintWeapon(Graphics g, WeaponToken weapon){

		int xPos = -1;
		int yPos = -1;

		for(int i = 0; i < rooms.length; i++){
			if(rooms[i].getWep() == weapon){
				xPos = rooms[i].getWeaponLoc()[0];
				yPos = rooms[i].getWeaponLoc()[1];
			}
		}
		if(xPos == -1){
			throw new IllegalStateException("Weapon location not found.");
		}
		// instantiate
		Image img = null;

		// a different file for each weapon
		switch (weapon.getName()) {
		case "Candlestick":
			img = scaleImage("src/resources/candlestick.jpg");
			break;
		case "Dagger":
			img = scaleImage("src/resources/dagger.jpg");
			break;
		case "Lead Pipe":
			img = scaleImage("src/resources/leadpipe.jpg");
			break;
		case "Revolver":
			img = scaleImage("src/resources/revolver.jpg");
			break;
		case "Rope":
			img = scaleImage("src/resources/rope.jpg");
			break;
		case "Spanner":
			img = scaleImage("src/resources/spanner.jpg");
			break;
		}
		// draw the image
		g.drawImage(img, xPos*TILE_WIDTH, yPos*TILE_HEIGHT, null);
	}

	/**
	 * Takes an image filename and scales it to twice the width and height of a Tile.
	 */
	public Image scaleImage(String s) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(s));
		} catch (IOException e) {
		}		
		Image scaledImage = img.getScaledInstance(
				(int)TILE_WIDTH*2,
				(int)TILE_HEIGHT*2,
				Image.SCALE_SMOOTH);		
		return scaledImage;
	}

	public void paintCharacters(Graphics g){
		// paint each token
		for(CharacterToken c:characterTokens){
			paintCharacter(g, c);
		}
	}

	public void paintCharacter(Graphics g, CharacterToken c){

		int xPos = c.getLocation()[0];
		int yPos = c.getLocation()[1];

		g.setColor(getTokenColor(c));
		g.fillOval(xPos*TILE_WIDTH +4, yPos*TILE_HEIGHT +3, 17, 16);
		g.setColor(Color.BLACK);
		g.drawOval(xPos*TILE_WIDTH +4, yPos*TILE_HEIGHT +3, 17, 16);
	}

	public void paintTiles(Graphics g){
		// paint each tile
		for(int j = 0; j< tiles[0].length; j++){
			for(int i = 0; i< tiles.length; i++){
				paintTile(g, tiles[i][j], i, j);
			}
		}
	}

	public void paintTile(Graphics g, Tile tile, int xPos, int yPos){

		g.setColor(getTileColor(tile));
		g.fillRect(xPos*TILE_WIDTH, yPos*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
		g.setColor(Color.BLACK);
		g.drawRect(xPos*TILE_WIDTH, yPos*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
	}



	public Color getTileColor(Tile tile) {

		switch (tile.getType()) {
		case INACCESSABLE:
			return new Color(150,75,75);
		case CORRIDOR:
			return Color.YELLOW;
		case ROOM:
			return Color.CYAN;
		case DOOR:
			return new Color(200, 130 , 30);
		case SPAWN:
			return Color.YELLOW;
		case PASSAGE:
			return Color.ORANGE;
		}
		return null;
	}

	public Color getTokenColor(CharacterToken c) {

		switch (c.getCharacterName()) {
		case "Mrs. White":
			return Color.WHITE;
		case "The Reverend Green":
			return Color.GREEN;
		case "Mrs. Peacock":
			return Color.BLUE;
		case "Professor Plum":
			return new Color(200, 0 , 200);
		case "Miss Scarlett":
			return Color.RED;
		case "Colonel Mustard":
			return new Color(170, 100 , 50);
		}
		return null;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		System.out.println(x+ ", "+y);
	}
	
	
	//other MouseListener methods
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub		
	}




}















