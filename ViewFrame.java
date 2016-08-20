package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ViewFrame extends JFrame implements MouseListener {

	private JPanel canvas, previousCanvas;
	private JComponent menuBar;
	private Controller ctrl;
	

	public ViewFrame(Controller ctrl) {
		super("Cluedo");
		this.ctrl = ctrl;
		
		// JFrame stuff
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// centre window on screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((dim.width/2)-(this.getPreferredSize().width/2), (dim.height/2)-(this.getPreferredSize().height/2));
		
		// add menu bar
		menuBar = createMenuBar(ctrl);

		
		// MouseListener
		addMouseListener(this);

		// visibility
		pack();
		setResizable(false);
		setVisible(true);

	}

	public void setView(String action, Object arg, Controller ctrl){
		if(action.equals("start")){
			// add canvas
			canvas = new StartCanvas(ctrl);
			add(canvas, BorderLayout.CENTER);
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


	public JMenuBar createMenuBar(Controller ctrl){
		
		JMenuBar menuBar = new JMenuBar();
		JMenu file, game;
		JMenuItem a, b, c, d;
		
		// two menus
		file = new JMenu("File");
		menuBar.add(file);
		game = new JMenu("Game");
		menuBar.add(game);

		// populate the menus
		a = new JMenuItem("New Game");
		file.add(a);
		b = new JMenuItem("Quit");
		file.add(b);		
		c = new JMenuItem("menu item");
		game.add(c);
		//a submenu
		d = new JMenuItem("menu item");
		c.add(d);
		
		// start a new game
		a.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				setView("start", null, ctrl); //sets the view to the starting screen
			}});
		
		// should open a dialog
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane box = new JOptionPane("bing");	
				
				Object[] options = {"Yes", "No"};
				int n = JOptionPane.showOptionDialog(new JOptionPane(), "Are you sure you want to quit Cluedo?", 
						"unknown text", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				

				//JOptionPane.showMessageDialog(new JOptionPane(), "Are you sure you want to quit Cluedo?");	//basic dialog box
				
			}});

		// finally set the menu
		setJMenuBar(menuBar);		
		return menuBar;
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


	public JPanel getCanvas(){
		return canvas;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(600, 400);
	}
	
	public void changeSize(){
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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
	
	
	
	
	
	
}
