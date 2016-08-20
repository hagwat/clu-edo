package ui;

import java.awt.BorderLayout;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JSplitPane;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.SwingConstants;
import java.awt.Font;

public class PlayerSetupCanvas extends JPanel {
	private JTextField txtHowManyPlayers;
	public PlayerSetupCanvas() {
		setLayout(null);

		JButton btnNewButton = new JButton("3");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(51, 152, 68, 25);
		add(btnNewButton);

		JButton button = new JButton("4");
		button.setBounds(187, 152, 68, 25);
		add(button);

		JButton button_1 = new JButton("5");
		button_1.setBounds(329, 152, 68, 25);
		add(button_1);

		JButton button_2 = new JButton("6");
		button_2.setBounds(465, 152, 68, 25);
		add(button_2);

		JButton btnContinue = new JButton("Continue");
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnContinue.setBounds(356, 325, 117, 25);
		add(btnContinue);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBack.setBounds(149, 325, 117, 25);
		add(btnBack);

		txtHowManyPlayers = new JTextField();
		txtHowManyPlayers.setFont(new Font("Lucida Bright", Font.PLAIN, 24));
		txtHowManyPlayers.setHorizontalAlignment(SwingConstants.CENTER);
		txtHowManyPlayers.setText("How Many Players? (3 - 6)");
		txtHowManyPlayers.setBounds(132, 29, 331, 56);
		add(txtHowManyPlayers);
		txtHowManyPlayers.setColumns(10);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(600, 400);
	}

}
