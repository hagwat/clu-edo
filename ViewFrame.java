package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ViewFrame extends JFrame implements MouseListener {

	private JPanel canvas;

	public ViewFrame(Controller ctrl) {
		super("Cluedo");

		// JFrame stuff
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		// add canvas
		canvas = new StartCanvas(ctrl);
		add(canvas, BorderLayout.CENTER);

		// MouseListeners
		addMouseListener(this);
		//canvas.addMouseListener(this);
		
		// add menu bar
		createMenuBar();

		// visibility
		pack();
		setResizable(true);
		setVisible(true);

		//sets the view to the starting screen
		setView("start", null, ctrl);
	}

	public void setView(String action, Object arg, Controller ctrl){
		if(action.equals("start")){
			StartCanvas vc = (StartCanvas)canvas;
			pack();
		}
		else if(action.equals("display board")){
			if(arg!=null){
				System.out.println("this is a board");
			}
		}else if(action.equals("player setup")){
			remove(canvas);
			canvas = new PlayerSetupCanvas();
			this.add(canvas, BorderLayout.CENTER);
			canvas.addMouseListener(this);
			this.validate();
		}
	}




	public void createMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		JMenu menu, submenu;

		menu = new JMenu("A Menu");
		menu.getAccessibleContext().setAccessibleDescription(
		        "Bar");
		menuBar.add(menu);

		setJMenuBar(menuBar);
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

	public JPanel getCanvas(){
		return canvas;
	}

}
