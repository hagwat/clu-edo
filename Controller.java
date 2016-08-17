package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.Game;
import game.TextClient;

public class Controller {

	private Game game;
	private ViewFrame view;

	public static void main(String[] args) {
		new Controller();
	}

	public Controller() {

		view = new ViewFrame();
		game = new Game();
		game.getTextClient().addController(this);
		game.getTextClient().startScreen();

	}


	
	
	public void handle(Object source, String action){
		if(source instanceof TextClient){
			if(action.equals("start")){
				view.setView("start");
			}
		}
	}

}
