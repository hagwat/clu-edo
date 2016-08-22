package ui;

import java.awt.Dimension;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTextArea;

import game.Player;

import java.awt.Font;

public class GameOverCanvas extends JPanel {

	public GameOverCanvas(Player p){
		setBackground(Color.BLACK);
		setLayout(null);

		JTextArea txtrGameOver = new JTextArea();
		txtrGameOver.setEditable(false);
		txtrGameOver.setBackground(Color.BLACK);
		txtrGameOver.setForeground(Color.WHITE);
		txtrGameOver.setFont(new Font("Dialog", Font.BOLD, 70));
		txtrGameOver.setText("GAME OVER");
		txtrGameOver.setBounds(261, 155, 566, 133);
		add(txtrGameOver);

		JTextArea textArea = new JTextArea();
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.WHITE);
		textArea.setFont(new Font("Dialog", Font.BOLD, 70));
		textArea.setEditable(false);
		textArea.setText(p.toString()+ " WINS!!!");
		textArea.setBounds(233, 300, 528, 133);
		add(textArea);

	}


	@Override
	public Dimension getPreferredSize(){
		return new Dimension(1000, 600);
	}
}
