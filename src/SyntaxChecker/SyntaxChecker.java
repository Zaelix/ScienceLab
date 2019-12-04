package SyntaxChecker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SyntaxChecker implements ActionListener {

	JFrame frame = new JFrame();
	JPanel mainPanel = new JPanel();
	JPanel upperPanel = new JPanel();
	JPanel lowerPanel = new JPanel();
	JButton button = new JButton("Click me!");
	
	Dimension panelSize = new Dimension(500, 300);
	
	public static void main(String[] args) {
		new SyntaxChecker().setup();
	}

	public void setup() {
		frame.add(mainPanel);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Hi");
	}
}
