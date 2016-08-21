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
import java.util.ArrayList;
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
		button3.setBounds(69, 229, 154, 54);
		add(button3);

		JButton button4 = new JButton("4");
		button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPlayers(4);
			}
		});
		button4.setBounds(304, 229, 154, 54);
		add(button4);

		JButton button5 = new JButton("5");
		button5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPlayers(5);
			}
		});
		button5.setBounds(537, 229, 154, 54);
		add(button5);

		JButton button6 = new JButton("6");
		button6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPlayers(6);
			}
		});
		button6.setBounds(760, 229, 154, 54);
		add(button6);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.getViewFrame().setCanvas(previousCanvas);
			}
		});
		btnBack.setBounds(370, 423, 243, 70);
		add(btnBack);

		JTextArea txtrHowManyPlayers = new JTextArea();
		txtrHowManyPlayers.setBackground(UIManager.getColor("Button.background"));
		txtrHowManyPlayers.setFont(new Font("Dialog", Font.BOLD, 30));
		txtrHowManyPlayers.setText("How Many Players? (3 - 6)");
		txtrHowManyPlayers.setBounds(260, 70, 679, 70);
		add(txtrHowManyPlayers);
	}


	public void setPlayers(int i){
		ctrl.setNumPlayers(i);
		ctrl.getViewFrame().setCanvas(new PlayerSetupCanvas(ctrl, this, i, 1, new ArrayList<Integer>()));
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(600, 400);
	}

}
