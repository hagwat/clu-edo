package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

public class ViewFrame extends JFrame implements MouseListener {

	private ViewCanvas canvas;

	public ViewFrame() {
		super("View");
		canvas = new ViewCanvas();
		setLayout(new BorderLayout());
		add(canvas, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setResizable(true);
		setVisible(true);
		addMouseListener(this);
		canvas.addMouseListener(this);
	}

	public void setView(String action, Object arg){
		if(action.equals("start")){
			canvas.setStartingScreen();
			pack();
		}
		else if(action.equals("display board")){
			if(arg!=null){
				System.out.println("this is a board");
			}
		}else if(action.equals("player setup")){

		}
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