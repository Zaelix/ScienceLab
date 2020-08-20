package Smileys;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SmileyRunner {
	public static void main(String[] args) {
		// 1. Make a new JFrame and set it to be visible
		JFrame frame = new JFrame();
		frame.setVisible(true);
		// Set default close operation
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 2. Make a new JPanel
		JPanel panel = new JPanel();
		// 3. Add your panel to your frame
		frame.add(panel);
		// 7. Set the Smiley class's color variable to Smiley.YELLOW
		// Run it again; are your Smileys all red now?
		//Smiley.setColor(Smiley.BLUE);
		Smiley.color = Smiley.GREEN;
		// 4. Make three Smiley objects and add them to your panel
		Smiley s1 = new Smiley();
		panel.add(s1);
		Smiley s2 = new Smiley();
		panel.add(s2);
		Smiley s3 = new Smiley();
		panel.add(s3);
		// 5. Pack your frame
		frame.pack();
		// 6. Run the program and make sure you see three yellow Smileys!
	}
}
