package Etc;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class SineVisualizer {
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 1000;
	
	JFrame frame = new JFrame();
	SinePanel visualizer = new SinePanel();
	Timer drawTimer = new Timer(1000/120,visualizer);
	
	public static void main(String[] args) {
		SineVisualizer dv = new SineVisualizer();
		dv.init();
	}
	
	public void init() {
		frame.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(visualizer);
		frame.addKeyListener(visualizer);
		frame.pack();
		drawTimer.start();
	}

	
}
