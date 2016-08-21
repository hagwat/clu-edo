package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SetupActionListener implements ActionListener {

	private PlayerSetupCanvas canvas;
	private int characterToken;
	private String playerName;

	public SetupActionListener(PlayerSetupCanvas j, int i, String name){
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
