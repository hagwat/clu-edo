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

public class PlayerSetupCanvas extends JPanel {

	private JTextField textField;
	private Controller ctrl;
	private int numPlayers;
	private int playerNumber;
	private ArrayList<Integer> chosen;

	public PlayerSetupCanvas(Controller ctrl, int numPlayers, int playerNumber, ArrayList<Integer> chosen) {
		setLayout(null);
		this.ctrl = ctrl;
		this.chosen = chosen;
		this.numPlayers = numPlayers;
		this.playerNumber = playerNumber;

		textField = new JTextField();
		textField.setBounds(84, 53, 114, 19);
		add(textField);
		textField.setColumns(10);

		JTextArea txtrPlayer = new JTextArea();
		txtrPlayer.setFont(new Font("Dialog", Font.PLAIN, 22));
		txtrPlayer.setBackground(UIManager.getColor("Button.background"));
		txtrPlayer.setEditable(false);
		txtrPlayer.setText("Player " + playerNumber + ":");
		txtrPlayer.setBounds(12, 12, 174, 29);
		add(txtrPlayer);

		JTextArea txtrName = new JTextArea();
		txtrName.setFont(new Font("Dialog", Font.PLAIN, 16));
		txtrName.setBackground(UIManager.getColor("Button.background"));
		txtrName.setEditable(false);
		txtrName.setText("Name:");
		txtrName.setBounds(12, 53, 60, 19);
		add(txtrName);

		// Mrs. Peacock button
		JRadioButton peacockRadioButton = new JRadioButton(peacock);
		peacockRadioButton.setBackground(UIManager.getColor("Button.foreground"));
		peacockRadioButton.addActionListener(new SetupActionListener(this, 3, "Miss Peacock"));
		peacockRadioButton.setBounds(188, 114, 80, 100);
		add(peacockRadioButton);

		// The Reverend Green button
		JRadioButton greenRadioButton = new JRadioButton(green);
		greenRadioButton.setBackground(UIManager.getColor("Button.foreground"));
		greenRadioButton.addActionListener(new SetupActionListener(this, 2, "The Reverend Green"));
		greenRadioButton.setBounds(320, 114, 80, 100);
		add(greenRadioButton);

		// Colonel Mustard button
		JRadioButton mustardRadioButton = new JRadioButton(mustard);
		mustardRadioButton.setBackground(UIManager.getColor("Button.foreground"));
		mustardRadioButton.addActionListener(new SetupActionListener(this, 6, "Colonel Mustard"));
		mustardRadioButton.setBounds(450, 114, 80, 100);
		add(mustardRadioButton);

		// Mrs. White button
		JRadioButton whiteRadioButton = new JRadioButton(white);
		whiteRadioButton.setBackground(UIManager.getColor("Button.foreground"));
		whiteRadioButton.addActionListener(new SetupActionListener(this, 1, "Mrs. White"));
		whiteRadioButton.setBounds(53, 237, 80, 100);
		add(whiteRadioButton);

		// Miss Scarlett button
		JRadioButton scarlettRadioButton = new JRadioButton(scarlett);
		scarlettRadioButton.setBackground(UIManager.getColor("Button.foreground"));
		scarlettRadioButton.addActionListener(new SetupActionListener(this, 5, "Miss Scarlett"));
		scarlettRadioButton.setBounds(188, 237, 80, 100);
		add(scarlettRadioButton);

		// Professor Plum button
		JRadioButton plumRadioButton = new JRadioButton(plum);
		plumRadioButton.setBackground(UIManager.getColor("Button.foreground"));
		plumRadioButton.addActionListener(new SetupActionListener(this, 4, "Professor Plum"));
		plumRadioButton.setBounds(53, 114, 80, 100);
		add(plumRadioButton);

		JTextArea txtrCharacterTokenSelection = new JTextArea();
		txtrCharacterTokenSelection.setFont(new Font("Dialog", Font.PLAIN, 16));
		txtrCharacterTokenSelection.setBackground(UIManager.getColor("Button.background"));
		txtrCharacterTokenSelection.setText("Character Token Selection:");
		txtrCharacterTokenSelection.setBounds(188, 84, 224, 37);
		add(txtrCharacterTokenSelection);
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
					System.exit(0);	//SET NEXT CANVAS
					return;
				}
				JOptionPane.showMessageDialog(ctrl.getViewFrame(), "Next player!");
				ctrl.getViewFrame().setCanvas(new PlayerSetupCanvas(ctrl, numPlayers, playerNumber + 1, chosen));
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
		Image newimg = image.getScaledInstance(80, 100, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(newimg);
	}
}
