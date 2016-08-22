package ui;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import game.Card;
import game.Player;
import game.Room;

/**
 *
 * A custom key listener that maps shortcuts for different buttons implemented in the game
 *
 */
public class CustomKeyListener implements KeyListener {

	private Controller ctrl;
	private Player player;
	private PlayerOptionCanvas canvas;

	public CustomKeyListener(Controller ctrl, Player p, PlayerOptionCanvas c){
		this.ctrl = ctrl;
		this.player = p;
		this.canvas = c;
	}


	@Override
	public void keyPressed(KeyEvent e) {
		//Shortcut key for showing hand
		if(e.getKeyCode() == KeyEvent.VK_H){
			String cards = "";
			for (Card c : player.getHand()) {
				cards += "- " + c.toString() + "\n";
			}
			JOptionPane.showMessageDialog(ctrl.getViewFrame(), cards, "Cards", JOptionPane.INFORMATION_MESSAGE);
		}

		//Shortcut key for making a suggestion
		if(e.getKeyCode() == KeyEvent.VK_S){
			Room room = player.getRoom();

			if (room == null) { 		//Player not in a room
				JOptionPane.showMessageDialog(ctrl.getViewFrame(), "You are not in a room, therefore cannot suggest!");
				return;
			}

			String roomName = room.getName();
			Object[] wepChoices = ctrl.getClient().getValidWeps().toArray();
			Object[] characterChoices = ctrl.getClient().getValidCharacters().toArray();

			int n = -1;

			while (n != JOptionPane.YES_OPTION) {	//Loop until the user confirms their choice
				String wepName = (String) JOptionPane.showInputDialog(null, "Choose the murder weapon...",
						"Weapon Choice", JOptionPane.QUESTION_MESSAGE, null, wepChoices, wepChoices[0]);
				//User selected cancel
				if (wepName == null) {
					return;
				}
				String characterName = (String) JOptionPane.showInputDialog(null, "Choose the murderer...",
						"Murderer Choice", JOptionPane.QUESTION_MESSAGE, null, characterChoices, characterChoices[0]);
				//User selected cancel
				if (characterName == null) {
					return;
				}

				n = JOptionPane.showConfirmDialog(ctrl.getViewFrame(),
						"So you think it was " + characterName + " in the " + roomName + " with the " + wepName + "?",
						"Confirm Suggestion", JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.NO_OPTION) {
					//Loop again so user can re-pick their choices
				} else {
					System.out.println(wepName + roomName + characterName);
					ctrl.getGame().swapWeaponTokens(wepName, room);
					ctrl.getGame().characterToRoom(room, characterName);
					Object[] refuter = ctrl.getClient().playerRefute(wepName, roomName, characterName);
					if(refuter == null){	//Suggested card in own hand
						JOptionPane.showMessageDialog(ctrl.getViewFrame(), "You suggested your own card..");
						canvas.endTurn();
					}
					else if (ctrl.getGame().accusation(wepName, roomName, characterName)) {
						JOptionPane.showMessageDialog(ctrl.getViewFrame(), "Nobody can refute...");
					} else if (refuter[0].equals("Leftover Pile")) {
						JOptionPane.showMessageDialog(ctrl.getViewFrame(),
								"The " + refuter[1] + " is in the leftover pile!");
						canvas.endTurn();
					} else {
						JOptionPane.showMessageDialog(ctrl.getViewFrame(),
								refuter[0] + " refutes and shows " + refuter[1] + "!");
						canvas.endTurn();
					}
				}
			}
		}

		//Shortcut for rolling
		if(e.getKeyCode() == KeyEvent.VK_R){
			Random r = new Random();
			int roll = r.nextInt(6) + 1;
			canvas.setRoll(roll);

			Icon one = scaleImage("src/resources/1.jpg");
			Icon two = scaleImage("src/resources/2.jpg");
			Icon three = scaleImage("src/resources/3.jpg");
			Icon four = scaleImage("src/resources/4.jpg");
			Icon five = scaleImage("src/resources/5.jpg");
			Icon six = scaleImage("src/resources/6.jpg");

			if (roll == 1) {
				canvas.displayRoll(one, 1);
			}
			if (roll == 2) {
				canvas.displayRoll(two, 2);
			}
			if (roll == 3) {
				canvas.displayRoll(three, 3);
			}
			if (roll == 4) {
				canvas.displayRoll(four, 4);
			}
			if (roll == 5) {
				canvas.displayRoll(five, 5);
			}
			if (roll == 6) {
				canvas.displayRoll(six, 6);
			}
		}

		//Shortcut key for making an accusation
		if(e.getKeyCode() == KeyEvent.VK_A){
			Object[] wepChoices = ctrl.getClient().getValidWeps().toArray();
			Object[] characterChoices = ctrl.getClient().getValidCharacters().toArray();
			Object[] roomChoices = ctrl.getClient().getValidRooms().toArray();
			int n = -1;

			while(n != JOptionPane.YES_OPTION){

				//Weapon choice
				String wepName = (String) JOptionPane.showInputDialog(null, "Choose the murder weapon...",
						"Weapon Choice", JOptionPane.QUESTION_MESSAGE, null, wepChoices, wepChoices[0]);
				//User selected cancel
				if (wepName == null) {
					return;
				}

				//Character choice
				String characterName = (String) JOptionPane.showInputDialog(null, "Choose the murderer...",
						"Murderer Choice", JOptionPane.QUESTION_MESSAGE, null, characterChoices, characterChoices[0]);
				//User selected cancel
				if (characterName == null) {
					return;
				}

				//Room choice
				String roomName = (String) JOptionPane.showInputDialog(null, "Choose the murder room...",
						"Room Choice", JOptionPane.QUESTION_MESSAGE, null, roomChoices, roomChoices[0]);
				//User selected cancel
				if (roomName == null) {
					return;
				}

				n = JOptionPane.showConfirmDialog(ctrl.getViewFrame(),
						"So you think it was " + characterName + " in the " + roomName + " with the " + wepName + "?",
						"Confirm Suggestion", JOptionPane.YES_NO_OPTION);

				if(n == JOptionPane.NO_OPTION) {
					//Loop again so user can re-pick their choices
				}else{
					Object[] refuter = ctrl.getClient().playerRefute(wepName, roomName, characterName);
					if(refuter == null){
						JOptionPane.showMessageDialog(ctrl.getViewFrame(), "You suggested your own card...\nYou have been eliminated!");
						canvas.eliminate();
					}
					else if(ctrl.getGame().accusation(wepName, roomName, characterName)){
						JOptionPane.showMessageDialog(ctrl.getViewFrame(), "That is the correct answer!\nCongratulations " + player.toString() + "!!!");

					}
					else if(refuter[0].equals("Leftover Pile")){
						JOptionPane.showMessageDialog(ctrl.getViewFrame(),
								"The " + refuter[1] + " is in the leftover pile!\nYou have been eliminated!");
						canvas.eliminate();
					}
					else {
						JOptionPane.showMessageDialog(ctrl.getViewFrame(),
								refuter[0] + " refutes and shows " + refuter[1] + "!\n You have been eliminated!");
						canvas.eliminate();
					}
				}
			}
		}

		//Shortcut key for showing leftovers
		if(e.getKeyCode() == KeyEvent.VK_L){
			if (ctrl.getGame().getDeck().size() == 0) {
				JOptionPane.showMessageDialog(ctrl.getViewFrame(), "No cards in the leftover pile");
			} else {
				String cards = "";
				for (Card c : ctrl.getGame().getDeck()) {
					cards += "- " + c.toString() + "\n";
				}
				JOptionPane.showMessageDialog(ctrl.getViewFrame(), cards);
			}
		}

		//Shortcut for ending your turn
		if(e.getKeyCode() == KeyEvent.VK_E){
			int r = JOptionPane.showConfirmDialog(ctrl.getViewFrame(), "Are you sure you want to end your turn?",
					"End Turn",
					JOptionPane.YES_NO_OPTION);
			if(r == JOptionPane.YES_OPTION){
				canvas.endTurn();
			}
		}

		//Shortcut for moving up
		if(e.getKeyCode() == KeyEvent.VK_UP){
			if(canvas.getRoll() == 0){
				JOptionPane.showMessageDialog(ctrl.getViewFrame(), "Roll the dice first!");
				return;
			}
			if(canvas.getNumMoves() < canvas.getRoll()){
				if(player.validMove(new String[]{"w"})){
					canvas.setNumMoves(canvas.getNumMoves() + 1);
					ctrl.handle("next move");
				}else{
					JOptionPane.showMessageDialog(ctrl.getViewFrame(), "Can't move there!");
				}
			}else{
				JOptionPane.showMessageDialog(ctrl.getViewFrame(), "No moves left!");
			}
		}

		//Shortcut for moving down
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			if(canvas.getRoll() == 0){
				JOptionPane.showMessageDialog(ctrl.getViewFrame(), "Roll the dice first!");
				return;
			}
			if(canvas.getNumMoves() < canvas.getRoll()){
				if(player.validMove(new String[]{"s"})){
					canvas.setNumMoves(canvas.getNumMoves() + 1);
					ctrl.handle("next move");
				}else{
					JOptionPane.showMessageDialog(ctrl.getViewFrame(), "Can't move there!");
				}
			}else{
				JOptionPane.showMessageDialog(ctrl.getViewFrame(), "No moves left!");
			}
		}

		//Shortcut for moving Left
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			if(canvas.getRoll() == 0){
				JOptionPane.showMessageDialog(ctrl.getViewFrame(), "Roll the dice first!");
				return;
			}
			if(canvas.getNumMoves() < canvas.getRoll()){
				if(player.validMove(new String[]{"a"})){
					canvas.setNumMoves(canvas.getNumMoves() + 1);
					ctrl.handle("next move");
				}else{
					JOptionPane.showMessageDialog(ctrl.getViewFrame(), "Can't move there!");
				}
			}else{
				JOptionPane.showMessageDialog(ctrl.getViewFrame(), "No moves left!");
			}
		}

		//Shortcut for moving RIGHT
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			if(canvas.getRoll() == 0){
				JOptionPane.showMessageDialog(ctrl.getViewFrame(), "Roll the dice first!");
				return;
			}
			if(canvas.getNumMoves() < canvas.getRoll()){
				if(player.validMove(new String[]{"d"})){
					canvas.setNumMoves(canvas.getNumMoves() + 1);
					ctrl.handle("next move");
				}else{
					JOptionPane.showMessageDialog(ctrl.getViewFrame(), "Can't move there!");
				}
			}else{
				JOptionPane.showMessageDialog(ctrl.getViewFrame(), "No moves left!");
			}
		}

	}

	public ImageIcon scaleImage(String s) {
		ImageIcon imageIcon = new ImageIcon(s);
		Image image = imageIcon.getImage();
		Image newimg = image.getScaledInstance(120, 160, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(newimg);
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}


	@Override
	public void keyTyped(KeyEvent e) {
	}
}
