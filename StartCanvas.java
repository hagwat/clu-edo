package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;


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

		//Big start button
		startButton = new JButton("Start Game");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remove(startButton);
				ctrl.handle("pressed start button");
			}});															
		add(startButton, BorderLayout.CENTER);
		startButton.setVisible(true);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}






