package ui;

import javax.swing.JPanel;
import game.*;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import java.awt.Font;

/**
 *
 * Canvas that displays the player's options. For example, suggesting, accusing, rolling and moving.
 *
 */
public class PlayerOptionCanvas extends JPanel {

	private Controller ctrl;
	private Player player;
	private int roll;		//Number of moves the player has
	private int numMoves = 0;	//Number of moves player has taken so far

	//Dice roll icons
	private Icon one = scaleImage("src/resources/1.jpg");
	private Icon two = scaleImage("src/resources/2.jpg");
	private Icon three = scaleImage("src/resources/3.jpg");
	private Icon four = scaleImage("src/resources/4.jpg");
	private Icon five = scaleImage("src/resources/5.jpg");
	private Icon six = scaleImage("src/resources/6.jpg");

	public PlayerOptionCanvas(Controller ctrl) {
		this.ctrl = ctrl;
		player = ctrl.getClient().getPlayers().poll();

		setBackground(Color.DARK_GRAY);
		setLayout(null);

		//Key Listener
		addKeyListener(new CustomKeyListener(ctrl, player, this));
		setFocusable(true);

		JButton btnShow = new JButton("Show Cards");
		btnShow.setFocusable(false);
		btnShow.setToolTipText("Show your current hand. Shortcut - h");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cards = "";
				for (Card c : player.getHand()) {
					cards += "- " + c.toString() + "\n";
				}
				JOptionPane.showMessageDialog(ctrl.getViewFrame(), cards, "Cards", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnShow.setBounds(216, 100, 118, 42);
		add(btnShow);

		//Suggest button
		JButton btnSuggest = new JButton("Suggest");
		btnSuggest.setFocusable(false);
		btnSuggest.setToolTipText("Make a suggestion using the room you are currently in. Shortcut - s");
		btnSuggest.addActionListener(new suggestAction());
		btnSuggest.setBounds(216, 163, 118, 42);
		add(btnSuggest);

		//Accuse button
		JButton btnAccuse = new JButton("Accuse");
		btnAccuse.setFocusable(false);
		btnAccuse.setToolTipText("Make an accusation. Shortcut - a");
		btnAccuse.addActionListener(new accuseAction());
		btnAccuse.setBounds(147, 224, 118, 42);
		add(btnAccuse);

		//Roll button
		JButton btnRoll = new JButton("Roll");
		btnRoll.setFocusable(false);
		btnRoll.setToolTipText("Roll a single dice. Shortcut - r");
		btnRoll.addActionListener(new rollAction());
		btnRoll.setBounds(72, 100, 112, 42);
		add(btnRoll);

		//End button
		JButton btnEnd = new JButton("End");
		btnEnd.setFocusable(false);
		btnEnd.setToolTipText("End your turn. Shortcut - e");
		btnEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int r = JOptionPane.showConfirmDialog(ctrl.getViewFrame(), "Are you sure you want to end your turn?",
						"End Turn",
						JOptionPane.YES_NO_OPTION);
				if(r == JOptionPane.YES_OPTION){
					endTurn();
				}
			}
		});
		btnEnd.setBounds(72, 284, 268, 42);
		add(btnEnd);

		//Leftovers Button
		JButton btnLeftoverButton = new JButton("Leftovers");
		btnLeftoverButton.setFocusable(false);
		btnLeftoverButton.setToolTipText("Show the leftover card pile. Shortcut - l");
		btnLeftoverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		});
		btnLeftoverButton.setBounds(72, 163, 118, 42);
		add(btnLeftoverButton);

		//Title text area
		JTextArea txtrPlayerName = new JTextArea();
		txtrPlayerName.setForeground(Color.WHITE);
		txtrPlayerName.setFont(new Font("Dialog", Font.PLAIN, 26));
		txtrPlayerName.setBackground(Color.DARK_GRAY);
		txtrPlayerName.setEditable(false);
		txtrPlayerName.setText(player.toString() + "'s Turn!\n"+player.getToken().getCharacterName());
		txtrPlayerName.setBounds(72, 12, 270, 62);
		add(txtrPlayerName);

		//Movement button - up
		JButton btnUp = new JButton("UP");
		btnUp.setFocusable(false);
		btnUp.setToolTipText("Move your character up after you have rolled. Shortcut - Up arrow key");
		btnUp.setBounds(167, 383, 79, 65);
		btnUp.addActionListener(new moveAction("w"));
		add(btnUp);

		//Movement button - down
		JButton btnDown = new JButton("DOWN");
		btnDown.setFocusable(false);
		btnDown.setToolTipText("Move your character down after you have rolled. Shortcut - Down arrow key");
		btnDown.setBounds(167, 460, 79, 65);
		btnDown.addActionListener(new moveAction("s"));
		add(btnDown);

		//Movement button - left
		JButton btnLeft = new JButton("LEFT");
		btnLeft.setFocusable(false);
		btnLeft.setToolTipText("Move your character left after you have rolled. Shortcut - Left arrow key");
		btnLeft.setBounds(76, 460, 79, 65);
		btnLeft.addActionListener(new moveAction("a"));
		add(btnLeft);

		//Movement button - right
		JButton btnRight = new JButton("RIGHT");
		btnRight.setFocusable(false);
		btnRight.setToolTipText("Move your character right after you have rolled. Shortcut - Right arrow key");
		btnRight.setBounds(258, 460, 79, 65);
		btnRight.addActionListener(new moveAction("d"));
		add(btnRight);

	}


	/**
	 *
	 * Action Listener for when the roll button is pressed.
	 *
	 */
	class rollAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Random r = new Random();
			roll = r.nextInt(6) + 1;

			if (roll == 1) {
				displayRoll(one, 1);
			}
			if (roll == 2) {
				displayRoll(two, 2);
			}
			if (roll == 3) {
				displayRoll(three, 3);
			}
			if (roll == 4) {
				displayRoll(four, 4);
			}
			if (roll == 5) {
				displayRoll(five, 5);
			}
			if (roll == 6) {
				displayRoll(six, 6);
			}
		}
	}

		public void displayRoll(Icon icon, int i) {
			JOptionPane.showMessageDialog(ctrl.getViewFrame(),
					"You rolled a " + i + "!\nUse the dirrection buttons to move there!", "Roll",
					JOptionPane.INFORMATION_MESSAGE, icon);

		}

		public ImageIcon scaleImage(String s) {
			ImageIcon imageIcon = new ImageIcon(s);
			Image image = imageIcon.getImage();
			Image newimg = image.getScaledInstance(120, 160, java.awt.Image.SCALE_SMOOTH);
			return new ImageIcon(newimg);
		}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(397, 600);
	}

	public void endTurn(){
		ctrl.getClient().getPlayers().offer(player);
		JOptionPane.showMessageDialog(ctrl.getViewFrame(), "End of " + player.toString() + "'s turn \n" +
										ctrl.getClient().getPlayers().peek().toString() + "'s Turn!");
		ctrl.handle("next turn");
	}

	public void eliminate(){
		JOptionPane.showMessageDialog(ctrl.getViewFrame(), player.toString() + " has been eliminated!\n" +
				ctrl.getClient().getPlayers().peek().toString() + "'s Turn!");
		if(ctrl.getClient().getPlayers().size() == 1){
			ctrl.getViewFrame().setCanvas(new GameOverCanvas(player));
		}else{
		ctrl.handle("next turn");
		}
	}

	public Player getPlayer(){
		return player;
	}
	class suggestAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
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
					ctrl.getGame().swapWeaponTokens(wepName, room);
					ctrl.getGame().characterToRoom(room, characterName);
					Object[] refuter = ctrl.getClient().playerRefute(wepName, roomName, characterName);
					if(refuter == null){	//Suggested card in own hand
						JOptionPane.showMessageDialog(ctrl.getViewFrame(), "You suggested your own card..");
						endTurn();
					}
					else if (ctrl.getGame().accusation(wepName, roomName, characterName)) {
						JOptionPane.showMessageDialog(ctrl.getViewFrame(), "Nobody can refute...");
					} else if (refuter[0].equals("Leftover Pile")) {
						JOptionPane.showMessageDialog(ctrl.getViewFrame(),
								"The " + refuter[1] + " is in the leftover pile!");
						endTurn();
					} else {
						JOptionPane.showMessageDialog(ctrl.getViewFrame(),
								refuter[0] + " refutes and shows " + refuter[1] + "!");
						endTurn();
					}
				}
			}
		}
	}


	class accuseAction implements ActionListener{

		public void actionPerformed(ActionEvent e){
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
						eliminate();
					}
					else if(ctrl.getGame().accusation(wepName, roomName, characterName)){
						JOptionPane.showMessageDialog(ctrl.getViewFrame(), "That is the correct answer!\nCongratulations " + player.toString() + "!!!");

					}
					else if(refuter[0].equals("Leftover Pile")){
						JOptionPane.showMessageDialog(ctrl.getViewFrame(),
								"The " + refuter[1] + " is in the leftover pile!\nYou have been eliminated!");
						eliminate();
					}
					else {
						JOptionPane.showMessageDialog(ctrl.getViewFrame(),
								refuter[0] + " refutes and shows " + refuter[1] + "!\n You have been eliminated!");
						eliminate();
					}
				}
			}
		}
	}


	class moveAction implements ActionListener{

		private String[] move;

		public moveAction(String s){
			move = new String[]{s};
		}

		public void actionPerformed(ActionEvent e){
			if(roll == 0){
				JOptionPane.showMessageDialog(ctrl.getViewFrame(), "Roll the dice first!");
				return;
			}
			if(numMoves < roll){
				if(player.validMove(move)){
					numMoves++;
					ctrl.handle("next move");
				}else{
					JOptionPane.showMessageDialog(ctrl.getViewFrame(), "Can't move there!");
				}
			}else{
				JOptionPane.showMessageDialog(ctrl.getViewFrame(), "No moves left!");
			}
		}
	}

	//**************************
	//GETTERS AND SETTERS
	//**************************
	public void setRoll(int r){
		this.roll = r;
	}

	public int getRoll(){
		return this.roll;
	}

	public int getNumMoves(){
		return this.numMoves;
	}

	public void setNumMoves(int i){
		this.numMoves = i;
	}
}
