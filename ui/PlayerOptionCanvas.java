package ui;

import javax.swing.JPanel;
import game.*;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Image;

public class PlayerOptionCanvas extends JPanel{
	
	private Controller ctrl;
	private Player player;
	
	public PlayerOptionCanvas(Controller ctrl) {
		this.ctrl = ctrl;
		player = ctrl.getClient().getPlayers().poll();
		
		setBackground(Color.DARK_GRAY);
		setLayout(null);
		
		JButton btnShow = new JButton("Show Cards");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cards = "";
				for(Card c : player.getHand()){
					cards += "- " + c.toString() + "\n";
				}
				JOptionPane.showMessageDialog(ctrl.getViewFrame(), cards, "Cards", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnShow.setBounds(65, 294, 173, 64);
		add(btnShow);
		
		JButton btnSuggest = new JButton("Suggest");
		btnSuggest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//WORK FROM HERE
			}
		});
		btnSuggest.setBounds(65, 369, 173, 64);
		add(btnSuggest);
		
		JButton btnAccuse = new JButton("Accuse");
		btnAccuse.setBounds(65, 445, 173, 64);
		add(btnAccuse);
		
		JButton btnRoll = new JButton("Roll");
		btnRoll.addActionListener(new rollAction());
		btnRoll.setBounds(65, 219, 173, 64);
		add(btnRoll);
		
		JButton btnEnd = new JButton("End");
		btnEnd.setBounds(65, 520, 173, 64);
		add(btnEnd);
	}
	
	class rollAction implements ActionListener{
		
		private int roll;
				
		public rollAction(){
			Random r = new Random();
			roll = r.nextInt(6) + 1;
		}
		
		public ImageIcon scaleImage(String s) {
			ImageIcon imageIcon = new ImageIcon(s);
			Image image = imageIcon.getImage();
			Image newimg = image.getScaledInstance(120, 160, java.awt.Image.SCALE_SMOOTH);
			return new ImageIcon(newimg);
		} 
		
		public void actionPerformed(ActionEvent e){
			if(roll == 1){
				displayRoll(one);				
			}
			if(roll == 2){
				displayRoll(two);				
			}
			if(roll == 3){
				displayRoll(three);				
			}
			if(roll == 4){
				displayRoll(four);				
			}
			if(roll == 5){
				displayRoll(five);				
			}
			if(roll == 6){
				displayRoll(six);				
			}
		}
		
		public void displayRoll(Icon icon){
			JOptionPane.showMessageDialog(ctrl.getViewFrame(), "You rolled a " + roll, "Roll", JOptionPane.INFORMATION_MESSAGE, icon);
		}
		
		Icon one = scaleImage("src/resources/1.jpg");
		Icon two = scaleImage("src/resources/2.jpg");
		Icon three = scaleImage("src/resources/3.jpg");
		Icon four = scaleImage("src/resources/4.jpg");
		Icon five = scaleImage("src/resources/5.jpg");
		Icon six = scaleImage("src/resources/6.jpg");
	}
}
