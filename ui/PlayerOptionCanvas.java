package ui;

import javax.swing.JPanel;
import game.*;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.JTextArea;
import java.awt.Font;

public class PlayerOptionCanvas extends JPanel {

	private Controller ctrl;
	private Player player;

	public PlayerOptionCanvas(Controller ctrl) {
		this.ctrl = ctrl;
		player = ctrl.getClient().getPlayers().poll();

		setBackground(Color.DARK_GRAY);
		setLayout(null);

		JButton btnShow = new JButton("Show Cards");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cards = "";
				for (Card c : player.getHand()) {
					cards += "- " + c.toString() + "\n";
				}
				JOptionPane.showMessageDialog(ctrl.getViewFrame(), cards, "Cards", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnShow.setBounds(103, 180, 173, 42);
		add(btnShow);

		JButton btnSuggest = new JButton("Suggest");
		btnSuggest.addActionListener(new suggestAction());
		btnSuggest.setBounds(103, 336, 173, 42);
		add(btnSuggest);

		JButton btnAccuse = new JButton("Accuse");
		btnAccuse.addActionListener(new accuseAction());
		btnAccuse.setBounds(103, 412, 173, 42);
		add(btnAccuse);

		JButton btnRoll = new JButton("Roll");
		btnRoll.addActionListener(new rollAction());
		btnRoll.setBounds(103, 99, 173, 42);
		add(btnRoll);

		JButton btnEnd = new JButton("End");
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
		btnEnd.setBounds(103, 486, 173, 42);
		add(btnEnd);

		JButton btnLeftoverButton = new JButton("Show Leftovers");
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
		btnLeftoverButton.setBounds(103, 259, 173, 42);
		add(btnLeftoverButton);

		JTextArea txtrPlayerName = new JTextArea();
		txtrPlayerName.setForeground(Color.WHITE);
		txtrPlayerName.setFont(new Font("Dialog", Font.PLAIN, 26));
		txtrPlayerName.setBackground(Color.DARK_GRAY);
		txtrPlayerName.setEditable(false);
		txtrPlayerName.setText(player.toString() + "'s Turn!");
		txtrPlayerName.setBounds(72, 12, 262, 32);
		add(txtrPlayerName);
	}

	/**
	 *
	 * Action Listener for when the roll button is pressed.
	 *
	 */
	class rollAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Random r = new Random();
			int roll = r.nextInt(6) + 1;

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

		public void displayRoll(Icon icon, int i) {
			JOptionPane.showMessageDialog(ctrl.getViewFrame(),
					"You rolled a " + i + "!\nClick a valid place on the board to move there", "Roll",
					JOptionPane.INFORMATION_MESSAGE, icon);
		}

		public ImageIcon scaleImage(String s) {
			ImageIcon imageIcon = new ImageIcon(s);
			Image image = imageIcon.getImage();
			Image newimg = image.getScaledInstance(120, 160, java.awt.Image.SCALE_SMOOTH);
			return new ImageIcon(newimg);
		}

		Icon one = scaleImage("src/resources/1.jpg");
		Icon two = scaleImage("src/resources/2.jpg");
		Icon three = scaleImage("src/resources/3.jpg");
		Icon four = scaleImage("src/resources/4.jpg");
		Icon five = scaleImage("src/resources/5.jpg");
		Icon six = scaleImage("src/resources/6.jpg");
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

			String roomName = "Study";
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
					Object[] refuter = ctrl.getClient().playerRefute(wepName, roomName, characterName);
					if(refuter == null){	//Suggested card in own hand
						JOptionPane.showMessageDialog(ctrl.getViewFrame(), "You suggested your own card..");
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
}
