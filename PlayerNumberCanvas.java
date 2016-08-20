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
import javax.swing.UIManager;

public class PlayerNumberCanvas extends JPanel {

	private Controller ctrl;
	private JPanel previousCanvas;

	public PlayerNumberCanvas(Controller ctrl, JPanel previous) {
		setLayout(null);
		this.ctrl = ctrl;
		this.previousCanvas = previous;

		JButton button3 = new JButton("3");
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPlayers(3);
			}
		});
		button3.setBounds(51, 152, 68, 25);
		add(button3);

		JButton button4 = new JButton("4");
		button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPlayers(4);
			}
		});
		button4.setBounds(187, 152, 68, 25);
		add(button4);

		JButton button5 = new JButton("5");
		button5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPlayers(5);
			}
		});
		button5.setBounds(329, 152, 68, 25);
		add(button5);

		JButton button6 = new JButton("6");
		button6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPlayers(6);
			}
		});
		button6.setBounds(465, 152, 68, 25);
		add(button6);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.getViewFrame().setCanvas(previousCanvas);
			}
		});
		btnBack.setBounds(249, 315, 117, 25);
		add(btnBack);

		JTextArea txtrHowManyPlayers = new JTextArea();
		txtrHowManyPlayers.setBackground(UIManager.getColor("Button.background"));
		txtrHowManyPlayers.setFont(new Font("Dialog", Font.PLAIN, 22));
		txtrHowManyPlayers.setText("How Many Players? (3 - 6)");
		txtrHowManyPlayers.setBounds(160, 44, 297, 33);
		add(txtrHowManyPlayers);
	}


	public void setPlayers(int i){
		ctrl.setPlayers(i);
		ctrl.getViewFrame().setCanvas(new PlayerSetupCanvas(ctrl, i, 1));
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(600, 400);
	}

}
