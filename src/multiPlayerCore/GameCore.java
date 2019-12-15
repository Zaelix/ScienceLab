package multiPlayerCore;

import java.awt.Dimension;

import javax.swing.JFrame;

public class GameCore {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static JFrame frame = new JFrame();
	GamePanel gp = new GamePanel();

	public static void main(String[] args) {
		GameCore gc = new GameCore();
		gc.setup();
	}

	private void setup() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(gp);
		frame.addKeyListener(gp.listener);
		frame.setVisible(true);
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.pack();
	}
	
	public static void pack() {
		System.out.println("Packing!");
		frame.pack();
	}
}
