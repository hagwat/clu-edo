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

		// sets border layout
		setLayout(new BorderLayout());
	
		// what to do on close
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				Object[] options = { "Yes", "No" };
				int r = JOptionPane.showOptionDialog(new JOptionPane(), "Are you sure you want to quit Cluedo?", "",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if (r == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});

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
		previousCanvas = canvas;
		remove(canvas);
		canvas = new BoardCanvas(arg, ctrl);
		this.add(canvas, BorderLayout.CENTER);
		canvas.addMouseListener(this);
		pack();
		validate();
		repaint();
		
		}else if(action.equals("player setup")){
			previousCanvas = canvas;
			remove(canvas);
			canvas = new PlayerNumberCanvas(ctrl, previousCanvas);
			this.add(canvas, BorderLayout.CENTER);
			canvas.addMouseListener(this);
			validate();
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

	// do you really want to exit?
				b.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Object[] options = {"Yes", "No"};
						int r = JOptionPane.showOptionDialog(new JOptionPane()	, "Are you sure you want to quit Cluedo?", "",
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
						if (r == JOptionPane.YES_OPTION) {
							System.exit(0);
						}
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

		if(j instanceof PlayerNumberCanvas){
			newCanvas = (PlayerNumberCanvas)j;
		}

		//Remove old canvas and repaint new canvas
		contain.removeAll();
		canvas = newCanvas;
		contain.add(canvas);
		contain.validate();
		contain.repaint();
	}


	public JPanel getCanvas(){
		return canvas;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1000, 600);
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
