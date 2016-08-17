package ui;

import game.Game;

public class Controller {

	private Game game;
	private ViewFrame view;

	public static void main(String[] args) {
		new Controller();
	}

	public Controller() {
		view = new ViewFrame();
		view.getCanvas().addController(this);
		view.setView("start", null);
	}

	public void startGame(){
		game = new Game();
		game.getTextClient().addController(this);
	}



	public void handle(Object source, String action) {

		if (action.equals("display board")) {
			view.setView(action, game.getBoard().getTiles());
			return;
		}

		if (action.equals("pressed start button")) {
			startGame();
			view.setView("player setup", null);
			return;
		}
	}
}
