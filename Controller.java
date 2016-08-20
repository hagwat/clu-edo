package ui;

import game.*;

public class Controller {

	private Game game;
	private ViewFrame view;
	private TextClient textClient;

	public static void main(String[] args) {
		new Controller();
	}

	public Controller() {
		view = new ViewFrame(this);
	}

	public void startGame(){
		game = new Game();
		textClient = game.getTextClient();
		game.getTextClient().addController(this);
	}



	public void handle(Object source, String action) {

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


	public void setPlayers(int i){
		textClient.setNumPlayers(i);
	}

	public ViewFrame getViewFrame(){
		return this.view;
	}
}
