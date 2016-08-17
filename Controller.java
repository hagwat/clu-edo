package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.Game;

public class Controller implements ActionListener {

	private Game game;
	private ViewFrame view;

	public static void main(String[] args) {
		new Controller();
	}

	public Controller() {

		view = new ViewFrame();
		game = new Game();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("gotitt");
	}

	public void getAction() {

	}

}
