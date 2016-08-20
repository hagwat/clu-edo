package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ViewFrame extends JFrame implements MouseListener {

	private JPanel canvas;
	private JPanel previousCanvas;
	private Controller ctrl;

	public ViewFrame(Controller ctrl) {
		super("Cluedo");
		this.ctrl = ctrl;

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
			previousCanvas = canvas;
			remove(canvas);
			canvas = new PlayerNumberCanvas(ctrl, previousCanvas);
			this.add(canvas, BorderLayout.CENTER);
			canvas.addMouseListener(this);
			this.validate();
		}
	}


	public void setCanvas(JPanel j){
		JPanel newCanvas = null;
		Container contain = this.getContentPane();
		previousCanvas = canvas;

		if(j instanceof StartCanvas){
			newCanvas = new StartCanvas(ctrl);
		}

		if(j instanceof PlayerSetupCanvas){
			newCanvas = (PlayerSetupCanvas)j;
		}

		//Remove old canvas and repaint new canvas
		contain.removeAll();
		canvas = newCanvas;
		contain.add(canvas);
		contain.validate();
		contain.repaint();
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
