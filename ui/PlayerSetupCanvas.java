package ui;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import game.*;

import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Queue;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import java.awt.Font;
import javax.swing.JButton;

/**
 * 
 * A setup canvas that asks the players their name and which character token
 * they wish to use.
 *
 */
public class PlayerSetupCanvas extends JPanel {

	private JTextField textField;
	private Controller ctrl;
	private int numPlayers;
	private int playerNumber;
	private ArrayList<Integer> chosen;
	private JPanel previous;

	public PlayerSetupCanvas(Controller ctrl, JPanel previous, int numPlayers, int playerNumber,
			ArrayList<Integer> chosen) {
		setLayout(null);
		this.ctrl = ctrl;
		this.chosen = chosen;
		this.numPlayers = numPlayers;
		this.playerNumber = playerNumber;
		this.previous = previous;

		textField = new JTextField();
		textField.setFont(new Font("Dialog", Font.PLAIN, 18));
		textField.setBounds(147, 66, 154, 36);
		add(textField);
		textField.setColumns(10);

		JTextArea txtrPlayer = new JTextArea();
		txtrPlayer.setFont(new Font("Dialog", Font.PLAIN, 22));
		txtrPlayer.setBackground(UIManager.getColor("Button.background"));
		txtrPlayer.setEditable(false);
		txtrPlayer.setText("Player " + playerNumber + ":");
		txtrPlayer.setBounds(426, 12, 174, 29);
		add(txtrPlayer);

		JTextArea txtrName = new JTextArea();
		txtrName.setFont(new Font("Dialog", Font.BOLD, 24));
		txtrName.setBackground(UIManager.getColor("Button.background"));
		txtrName.setEditable(false);
		txtrName.setText("Name:");
		txtrName.setBounds(51, 67, 114, 45);
		add(txtrName);

		// Mrs. Peacock button
		JRadioButton peacockRadioButton = new JRadioButton(peacock);
		peacockRadioButton.setBackground(UIManager.getColor("Button.foreground"));
		peacockRadioButton.addActionListener(new SetupActionListener(this, 3, "Miss Peacock"));
		peacockRadioButton.setBounds(552, 181, 120, 160);
		add(peacockRadioButton);

		// The Reverend Green button
		JRadioButton greenRadioButton = new JRadioButton(green);
		greenRadioButton.setBackground(UIManager.getColor("Button.foreground"));
		greenRadioButton.addActionListener(new SetupActionListener(this, 2, "The Reverend Green"));
		greenRadioButton.setBounds(329, 375, 120, 160);
		add(greenRadioButton);

		// Colonel Mustard button
		JRadioButton mustardRadioButton = new JRadioButton(mustard);
		mustardRadioButton.setBackground(UIManager.getColor("Button.foreground"));
		mustardRadioButton.addActionListener(new SetupActionListener(this, 6, "Colonel Mustard"));
		mustardRadioButton.setBounds(776, 181, 120, 160);
		add(mustardRadioButton);

		// Mrs. White button
		JRadioButton whiteRadioButton = new JRadioButton(white);
		whiteRadioButton.setBackground(UIManager.getColor("Button.foreground"));
		whiteRadioButton.addActionListener(new SetupActionListener(this, 1, "Mrs. White"));
		whiteRadioButton.setBounds(123, 375, 120, 160);
		add(whiteRadioButton);

		// Miss Scarlett button
		JRadioButton scarlettRadioButton = new JRadioButton(scarlett);
		scarlettRadioButton.setBackground(UIManager.getColor("Button.foreground"));
		scarlettRadioButton.addActionListener(new SetupActionListener(this, 5, "Miss Scarlett"));
		scarlettRadioButton.setBounds(329, 181, 120, 160);
		add(scarlettRadioButton);

		// Professor Plum button
		JRadioButton plumRadioButton = new JRadioButton(plum);
		plumRadioButton.setBackground(UIManager.getColor("Button.foreground"));
		plumRadioButton.addActionListener(new SetupActionListener(this, 4, "Professor Plum"));
		plumRadioButton.setBounds(123, 181, 120, 160);
		add(plumRadioButton);

		JTextArea txtrCharacterTokenSelection = new JTextArea();
		txtrCharacterTokenSelection.setFont(new Font("Dialog", Font.BOLD, 24));
		txtrCharacterTokenSelection.setBackground(UIManager.getColor("Button.background"));
		txtrCharacterTokenSelection.setText("Character Token Selection:");
		txtrCharacterTokenSelection.setBounds(294, 126, 396, 37);
		add(txtrCharacterTokenSelection);

		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.getClient().getPlayers().clear();
				ctrl.getViewFrame().setCanvas(previous);
			}
		});
		btnNewButton.setBounds(653, 460, 230, 75);
		add(btnNewButton);

	}

	public void buttonAction(int i, String name) {
		int r = JOptionPane.showConfirmDialog(new JOptionPane(), "Are you sure you want to select " + name + "?", null,
				JOptionPane.YES_NO_OPTION);
		if (r == JOptionPane.YES_OPTION) {
			for (int j : chosen) {
				if (j == i) {
					JOptionPane.showMessageDialog(ctrl.getViewFrame(), "Character already taken!");
					return;
				}
			}

			if (!textField.getText().equals("")) {
				ctrl.addPlayer(i, textField.getText());
				chosen.add(i);
				if (playerNumber >= numPlayers) {
					ctrl.getClient().setPlayers();
					ctrl.handle("display board");
					return;
				}
				JOptionPane.showMessageDialog(ctrl.getViewFrame(), "Next player!");
				ctrl.getViewFrame()
						.setCanvas(new PlayerSetupCanvas(ctrl, previous, numPlayers, playerNumber + 1, chosen));

			} else {
				JOptionPane.showMessageDialog(ctrl.getViewFrame(), "Name cannot be left blank!");
			}
		}

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(600, 400);
	}

	// Icons for the character token selection
	Icon peacock = scaleImage("src/resources/peacock.jpg");
	Icon green = scaleImage("src/resources/green.jpg");
	Icon mustard = scaleImage("src/resources/mustard.jpg");
	Icon white = scaleImage("src/resources/white.jpg");
	Icon scarlett = scaleImage("src/resources/scarlett.jpg");
	Icon plum = scaleImage("src/resources/plum.jpg");

	/**
	 * Takes an image filename and scales it to a fixed size 80px width and
	 * 100px height image icon.
	 *
	 * @param s
	 * @return
	 */
	public ImageIcon scaleImage(String s) {
		ImageIcon imageIcon = new ImageIcon(s);
		Image image = imageIcon.getImage();
		Image newimg = image.getScaledInstance(120, 160, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(newimg);
	}

	class SetupActionListener implements ActionListener {

		private PlayerSetupCanvas canvas;
		private int characterToken;
		private String playerName;

		public SetupActionListener(PlayerSetupCanvas j, int i, String name) {
			super();
			this.characterToken = i;
			this.playerName = name;
			this.canvas = j;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			canvas.buttonAction(characterToken, playerName);
		}

	}

}
