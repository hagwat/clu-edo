package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class ViewCanvas extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 401385718012180046L;
	private JComponent button;
	private JComponent startButton;

	public ViewCanvas() {
		super();
		setLayout(new BorderLayout());
		button = new JButton("Alternate button for hipstr");
		add(button, BorderLayout.EAST);

		startButton = new JButton("Start Game");
		add(startButton, BorderLayout.CENTER);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(600, 400);
	}

	public void setStartingScreen() {
		
		System.out.println("start");
		startButton = new JButton("Start Game");
		add(startButton, BorderLayout.CENTER);
	}
}
