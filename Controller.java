package ui;

import javax.swing.JFrame;
import javax.swing.UIManager;

import game.*;

public class Controller {

	private Game game;
	private ViewFrame view;
	private TextClient textClient;

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
		textClient = game.getTextClient();
	}

	public void startGame(){
		game = new Game();
		game.getTextClient().addController(this);
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
	
	public ViewFrame getViewFrame(){
		return this.view;
	}

}
