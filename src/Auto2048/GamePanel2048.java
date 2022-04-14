package Auto2048;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.MouseInfo;
import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.Timer;


public class GamePanel2048 extends JPanel implements KeyListener, ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ObjectManager2048 manager = new ObjectManager2048();
	Timer stepTimer;
	Timer drawTimer;
	
	public GamePanel2048() {
		int delay = 300;
		drawTimer = new Timer(delay/5, this);
		stepTimer = new Timer(delay, this);
		drawTimer.start();
		stepTimer.start();

	}
	public void paintComponent(Graphics g) {
		manager.draw(g);
	}
	
	public void actionPerformed(ActionEvent e) {
		Point mp = MouseInfo.getPointerInfo().getLocation();
		if(mp.getX() == 0 && mp.getY() == 0) {
			//System.exit(0);
			//timer.stop();
			ObjectManager2048.scanbot.mouseMove(Auto2048.startX,Auto2048.startY);
			AI2048.isActive = !AI2048.isActive;
			System.out.println("AI is Active: " + AI2048.isActive);
		}
		if(e.getSource().equals(stepTimer)) {
			manager.update();
			String enabled = AI2048.isActive ? "RUNNING" : "STOPPED";
			Auto2048.frame.setTitle("Auto2048 " + enabled);
		}
		repaint();
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int keyPressed = e.getKeyCode();
		System.out.println(keyPressed);
		if(keyPressed == 90) {// z
			Point mp = MouseInfo.getPointerInfo().getLocation();
			Auto2048.startX = (int) mp.getX();
			Auto2048.startY = (int) mp.getY();
			System.out.println(mp);
			Auto2048.frame.setLocation((int)mp.getX()+500, (int)mp.getY()-30);
		}
		if(keyPressed == 88) {// x
			Point mp = MouseInfo.getPointerInfo().getLocation();
			Color c = ObjectManager2048.scanbot.getPixelColor((int)mp.getX(), (int)mp.getY());
			System.out.println(c);
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
