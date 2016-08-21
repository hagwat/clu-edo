package ui;

import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.UIManager;

import game.*;

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



	public void handle(String action) {

		if (action.equals("display board")) {
			view.setView(action, game.getBoard().getTiles(), this);
			return;
		}

		if (action.equals("pressed start button")) {
			startGame();
			view.setView("player setup", null, this);
			return;
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

}
