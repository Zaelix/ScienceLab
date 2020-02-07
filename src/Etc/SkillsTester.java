package Etc;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SkillsTester {
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	JTextField field = new JTextField(20);
	
	public static void main(String[] args) {
		SkillsTester st = new SkillsTester();
		st.setup();
	}

	private void setup() {
		frame.add(panel);
		panel.add(field);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
	}

}
