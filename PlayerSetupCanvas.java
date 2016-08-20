package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class PlayerSetupCanvas extends JPanel {

	private JComponent button;

	public PlayerSetupCanvas(){
		super();
		setLayout(new BorderLayout());
		button = new JRadioButton("random");
		add(button, BorderLayout.CENTER);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(600, 400);
	}

}
