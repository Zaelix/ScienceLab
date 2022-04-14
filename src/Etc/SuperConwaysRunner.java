package Etc;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class SuperConwaysRunner {
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 1000;
	
	JFrame frame = new JFrame();
	SuperConways panel = new SuperConways();
	Timer drawTimer = new Timer(1000/2,panel);
	
	public static void main(String[] args) {
		SuperConwaysRunner dv = new SuperConwaysRunner();
		dv.init();
	}
	
	public void init() {
		frame.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.addKeyListener(panel);
		frame.addMouseListener(panel);
		frame.pack();
		drawTimer.start();
	}

	
}
