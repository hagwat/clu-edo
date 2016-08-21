package ui;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;

import game.*;

import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.util.Queue;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import java.awt.Font;

public class PlayerSetupCanvas extends JPanel {

	private JTextField textField;
	private int playerNumber;		//refers to which player we are up to
	private int numPlayers;
	private String name;
	private int characterToken;		//int refers to the character in characters.txt
	private Queue<Player> players;

	public PlayerSetupCanvas(Controller ctrl, int numPlayers, int playerNumber) {
		setLayout(null);
		this.numPlayers = numPlayers;
		this.playerNumber = playerNumber;

		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = textField.getText();
			}
		});
		textField.setBounds(84, 79, 114, 19);
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
		txtrName.setBounds(12, 79, 60, 19);
		add(txtrName);

		JRadioButton peacockRadioButton = new JRadioButton(peacock);
		peacockRadioButton.setBackground(UIManager.getColor("Button.foreground"));
		peacockRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				characterToken = 3;

			}
		});
		peacockRadioButton.setBounds(188, 165, 80, 100);
		add(peacockRadioButton);

		JRadioButton greenRadioButton = new JRadioButton(green);
		greenRadioButton.setBackground(UIManager.getColor("Button.foreground"));
		peacockRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				characterToken = 2;
			}
		});
		greenRadioButton.setBounds(328, 165, 80, 100);
		add(greenRadioButton);

		JRadioButton mustardRadioButton = new JRadioButton(mustard);
		mustardRadioButton.setBackground(UIManager.getColor("Button.foreground"));
		peacockRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				characterToken = 6;
			}
		});
		mustardRadioButton.setBounds(467, 165, 80, 100);
		add(mustardRadioButton);

		JRadioButton whiteRadioButton = new JRadioButton(white);
		whiteRadioButton.setBackground(UIManager.getColor("Button.foreground"));
		peacockRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				characterToken = 1;
			}
		});
		whiteRadioButton.setBounds(53, 293, 80, 100);
		add(whiteRadioButton);

		JRadioButton scarlettRadioButton = new JRadioButton(scarlett);
		scarlettRadioButton.setBackground(UIManager.getColor("Button.foreground"));
		peacockRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				characterToken = 5;
			}
		});
		scarlettRadioButton.setBounds(188, 293, 80, 100);
		add(scarlettRadioButton);

		JRadioButton plumRadioButton = new JRadioButton(plum);
		plumRadioButton.setBackground(UIManager.getColor("Button.foreground"));
		peacockRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				characterToken = 4;
			}
		});
		plumRadioButton.setBounds(53, 165, 80, 100);
		add(plumRadioButton);

		JTextArea txtrCharacterTokenSelection = new JTextArea();
		txtrCharacterTokenSelection.setFont(new Font("Dialog", Font.PLAIN, 16));
		txtrCharacterTokenSelection.setBackground(UIManager.getColor("Button.background"));
		txtrCharacterTokenSelection.setText("Character Token Selection:");
		txtrCharacterTokenSelection.setBounds(188, 120, 224, 37);
		add(txtrCharacterTokenSelection);
	}


	public void setupPlayer(String name, String icon){

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(600, 400);
	}

	//Icons for the character token selection
	Icon peacock = scaleImage("src/peacock.jpg");
	Icon green = scaleImage("src/green.jpg");
	Icon mustard = scaleImage("src/mustard.jpg");
	Icon white = scaleImage("src/white.jpg");
	Icon scarlett = scaleImage("src/scarlett.jpg");
	Icon plum = scaleImage("src/plum.jpg");

	/**
	 * Takes an image filename and scales it to a fixed size 80px width and 100px height image icon.
	 * @param s
	 * @return
	 */
	public ImageIcon scaleImage(String s){
		ImageIcon imageIcon = new ImageIcon(s);
		Image image = imageIcon.getImage();
		Image newimg = image.getScaledInstance(80, 100,  java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(newimg);
	}
}
