package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import actions.StartAction;

public class StartCanvas extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 401385718012180046L;
	private JComponent button;
	private JButton startButton;

	public StartCanvas(Controller ctrl) {
		super();
		
		setLayout(new BorderLayout());
		button = new JButton("Alternate button for hipster");
		add(button, BorderLayout.EAST);
		
		startButton = new JButton("Start Game");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remove(startButton);
				remove(button);
				ctrl.handle(this, "pressed start button");
			}
															});
		add(startButton, BorderLayout.CENTER);
		startButton.setVisible(true);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(600, 400);
	}

	

	
}






