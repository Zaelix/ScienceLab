package SyntaxChecker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SyntaxChecker implements ActionListener {

	JFrame frame = new JFrame();
	JPanel mainPanel = new JPanel();
	JPanel upperPanel = new JPanel();
	JPanel lowerPanel = new JPanel();
	JTextField inputField = new JTextField();
	JButton button = new JButton("Click me!");
	
	Dimension panelSize = new Dimension(500, 300);
	
	public static void main(String[] args) {
		new SyntaxChecker().setup();
	}

	public void setup() {
		button = createButtonWithImage("power_button.png");
		frame.add(mainPanel);
		
		// input text field stuff
		inputField.setPreferredSize(new Dimension(100, 30));
		mainPanel.add(inputField);
		// end input text field stuff
		
		mainPanel.add(upperPanel);
		mainPanel.add(button);
		mainPanel.add(lowerPanel);
		upperPanel.setBackground(Color.GREEN);
		lowerPanel.setBackground(Color.RED);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel.setPreferredSize(new Dimension(500, 650));
		upperPanel.setPreferredSize(panelSize);
		lowerPanel.setPreferredSize(panelSize);
		button.addActionListener(this);
		frame.pack();
	}

	public JButton createButtonWithImage(String fileName) {
		 JButton button = new JButton();
		  try {
		    Image img = ImageIO.read(getClass().getResource(fileName));
		    button.setIcon(new ImageIcon(img));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }
		  return button;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Hi");
		String input = inputField.getText();
		System.out.println(input);
	}
}
