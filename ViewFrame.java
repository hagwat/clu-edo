package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class ViewFrame extends JFrame implements MouseListener {

	private ViewCanvas canvas;

	public ViewFrame() {
		super("View");

		// JFrame stuff
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		// add canvas
		canvas = new ViewCanvas();
		add(canvas, BorderLayout.CENTER);

		// MouseListeners
		addMouseListener(this);
		canvas.addMouseListener(this);

		// add menu bar
		createMenuBar();

		// visibility
		pack();
		setResizable(true);
		setVisible(true);

	}

	public void setView(String action, Object arg, Controller ctrl){
		if(action.equals("start")){
			canvas.setStartingScreen(ctrl);
			pack();
		}
		else if(action.equals("display board")){
			if(arg!=null){
				System.out.println("this is a board");
			}
		}else if(action.equals("player setup")){
			playerSetupCanvas = new PlayerSetupCanvas();
			this.add(playerSetupCanvas, BorderLayout.CENTER);
			this.validate();
		}
	}


	/**
	 * Called after the "Start" button is pressed.
	 */
	public void playerSetup(){

	}

	public void createMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		JMenu menu, submenu;
	}































	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		System.out.println("hi");
		// new ActionEvent(anchor, y, title, y);

	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	public ViewCanvas getCanvas(){
		return canvas;
	}

}
