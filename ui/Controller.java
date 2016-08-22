package ui;

import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.UIManager;

import game.*;

/**
 * The controller is a bridging class between the UI and the game. It handles
 * actions that go between the two halves of the program.
 */
public class Controller {

	private Game game;
	private ViewFrame view;
	private Client client;

	public static void main(String[] args) {
		new Controller();
	}

	public Controller() {
		// sets look and feel
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					JFrame.setDefaultLookAndFeelDecorated(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
		view = new ViewFrame(this);
	}

	public void startGame(){
		game = new Game();
		client = game.getClient();
		game.getClient().addController(this);
	}


	/**
	 * The controller handles actions by first checking what action it is, then
	 * doing the behaviour for that action.
	 */
	public void handle(String action) {

		if (action.equals("pressed start button")) {
			startGame();
			view.setView("player setup", null, this);
			return;
		}
		if (action.equals("display board")) {
			view.setView(action, new Object[]{
					game.getBoard().getTiles(),
					game.getBoard().getCharacterTokens(),
					game.getWeapons(),
					game.getBoard().getRooms()
					}, this);
			return;
		}
		if(action.equals("next turn")){
			view.setView(action,  new Object[]{
					game.getBoard().getTiles(),
					game.getBoard().getCharacterTokens(),
					game.getWeapons(),
					game.getBoard().getRooms()
					}, this);
			}
		}

	public void setNumPlayers(int i){
		client.setNumPlayers(i);
	}

	public void addPlayer(int i, String name){
		Player p = new Player(i, name, game.getBoard());
		client.addPlayer(p);
	}

	public ViewFrame getViewFrame(){
		return this.view;
	}

	public Client getClient(){
		return this.client;
	}

	public Game getGame(){
		return game;
	}

}
