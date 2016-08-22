package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * The main frame that displays different canvases depending on the state of the game.
 *
 */
public class ViewFrame extends JFrame implements MouseListener {

	private JPanel canvas, previousCanvas, optionCanvas;
	private JComponent menuBar;
	private Controller ctrl;

/**
 * The top level container for the UI.
 */
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

	public JMenuBar createMenuBar(Controller ctrl){

		JMenuBar menuBar = new JMenuBar();
		JMenu file, game;
		JMenuItem a, b, c, d;

		// two menus
		file = new JMenu("File");
		menuBar.add(file);
		game = new JMenu("Help");
		menuBar.add(game);

		// populate the menus
		a = new JMenuItem("New Game");
		file.add(a);
		b = new JMenuItem("Quit");
		file.add(b);
		c = new JMenuItem("Key bindings");
		game.add(c);

		// start a new game
		a.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setView("start", null, ctrl); //sets the view to the starting screen
			}});

		// do you really want to exit?
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] options = { "Yes", "No" };
				int r = JOptionPane.showOptionDialog(new JOptionPane(), "Are you sure you want to quit Cluedo?", "",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if (r == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});

		//Key bindings help
		c.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(new JOptionPane(), "Key Bindings:\n"
											+ "h - Show Hand\n"
											+ "l - Show Leftovers"
											+ "s - Make Suggestion\n"
											+ "Arrow Keys - Move in that direction\n"
											+ "a - make accusation\n"
											+ "e - end turn\n");

			}
		});

		// finally set the menu
		setJMenuBar(menuBar);
		return menuBar;
	}

	/**
	 * The next two methods deal with changing to the next view on the UI.
	 */
	public void setView(String action, Object[] arg, Controller ctrl) {

		if (action.equals("start")) {
			// add canvas
			canvas = new StartCanvas(ctrl);
			add(canvas, BorderLayout.CENTER);
			StartCanvas vc = (StartCanvas) canvas;
			pack();
		}
		else if (action.equals("player setup")) {
			previousCanvas = canvas;
			remove(canvas);
			canvas = new PlayerNumberCanvas(ctrl, previousCanvas);
			this.add(canvas, BorderLayout.CENTER);
			canvas.addMouseListener(this);
			validate();
		}
		else if (action.equals("display board")) {
			previousCanvas = canvas;
			optionCanvas = new PlayerOptionCanvas(ctrl);
			remove(canvas);
			canvas = new BoardCanvas(arg);
			this.add(canvas, BorderLayout.CENTER);
			this.add(optionCanvas, BorderLayout.EAST);
			pack();
			validate();
			repaint();
		}
		else if(action.equals("next turn")){
			remove(optionCanvas);
			optionCanvas = new PlayerOptionCanvas(ctrl);
			this.add(optionCanvas, BorderLayout.EAST);
			pack();
			validate();
			repaint();
		}
		else if(action.equals("next move")){
			remove(canvas);
			canvas = new BoardCanvas(arg);
			this.add(canvas, BorderLayout.CENTER);
			pack();
			validate();
			repaint();
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
