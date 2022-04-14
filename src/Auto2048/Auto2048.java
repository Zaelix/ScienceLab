package Auto2048;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Auto2048 {
	public static int startX = 0;
	public static int startY = 0;
	public static int WIDTH = 500;
	public static int HEIGHT = 500;
	public static JFrame frame;
	GamePanel2048 panel;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Auto2048 a = new Auto2048();
		a.setup();
	}
	void setup(){
		//Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame = new JFrame();
		panel = new GamePanel2048(); 
		frame.addKeyListener(panel);
		frame.setTitle("Auto2048");
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));

		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//frame.setUndecorated(true);
		frame.setVisible(true);

		frame.pack();
	}
}
