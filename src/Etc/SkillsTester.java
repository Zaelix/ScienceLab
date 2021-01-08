package Etc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SkillsTester implements ActionListener {
	
	
	public static void main(String[] args) {
		SkillsTester st = new SkillsTester();
		st.setup();
		

	}

	private void setup() {
		System.out.println(calculateTip(100));
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	double calculateTip(double bill){
		double tip = bill*0.15;
		return tip;
	}
	
	

}
