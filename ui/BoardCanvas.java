package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import game.Card;
import game.Player;
import game.Tile;
import game.Tile.TileType;

public class BoardCanvas extends JPanel {
	
	private Controller ctrl;
	Tile[][] tiles;
	private static final int TILE_WIDTH = 30;
	private static final int TILE_HEIGHT = 20;
	
	public BoardCanvas(Object arg, Controller ctrl){
		this.ctrl = ctrl;
		
		//if arg is null or not a 2D array of Tiles
		if (arg == null) {
			throw new IllegalArgumentException("No argument to construct board.");
		}
		if (!(arg instanceof Tile[][])) {
			throw new IllegalArgumentException("Invalid argument to construct board.");
		}
		JPanel playerOptions = new PlayerOptionCanvas(ctrl);
		ctrl.getViewFrame().add(this, BorderLayout.WEST);
		ctrl.getViewFrame().add(playerOptions, BorderLayout.EAST);
		setPreferredSize(new Dimension(1000, 600));
		tiles = (Tile[][])arg;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public void paint(Graphics g) {
		paintTiles(g);
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
		System.out.println(xPos + " " + yPos);
		System.out.println((xPos+1)*TILE_WIDTH +" "+(yPos+1)*TILE_HEIGHT);
		System.out.println();
	
		g.setColor(getTileColor(tile));		
		g.fillRect(xPos*TILE_WIDTH, yPos*TILE_HEIGHT, (xPos+1)*TILE_WIDTH, (yPos+1)*TILE_HEIGHT);

		g.setColor(Color.BLACK);		
		g.drawRect(xPos*TILE_WIDTH, yPos*TILE_HEIGHT, (xPos+1)*TILE_WIDTH, (yPos+1)*TILE_HEIGHT);
	
	}
	

	
	public Color getTileColor(Tile tile) {
		switch (tile.getType()) {
		case INACCESSABLE:
			return Color.MAGENTA;
		case CORRIDOR:
			return Color.YELLOW;
		case ROOM:
			return Color.CYAN;
		case DOOR:
			return Color.YELLOW;
		case SPAWN:
			return Color.YELLOW;
		}
		return null;
	}
	
	
}















