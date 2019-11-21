package FancyRocketship;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class FancyRocketship extends JPanel implements ActionListener, KeyListener {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FancyRocketship frs = new FancyRocketship();
		frs.setup();
	}

	JFrame frame = new JFrame();
	public static final int WIDTH = 1600;
	public static final int HEIGHT = 900;
	public static long frameCount = 0;
	Random gen = new Random();
	ArrayList<Ship> ships = new ArrayList<Ship>();
	Timer timer = new Timer(1000 / 60, this);
	int bg = 200;
	public static int offsetMax = 30;
	public static double vxMult = 1;
	public static double vyMult = 1;
	Queue<Long> previousFrames = new LinkedBlockingQueue<Long>();
	long lastFrameTime = 0;
	
	void draw(Graphics g) {
		frameCount++;
		g.setColor(new Color(bg, bg, bg));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		for (int i = 0; i < ships.size(); i++) {
			ships.get(i).draw(g);
			ships.get(i).setSmokeLimit(bg);
		}
		if (frameCount % 300 == 0) {
			addShip();
		}
		purgeShips();
		drawShipDebugStats(g);
		g.setColor(new Color(255, 0, 0));
		g.drawString("fps:"+getFramerate(), 10, 30);
		g.drawString("Ships:" + ships.size(), 10, 55);
	}

	int getFramerate() {
		long diff = System.currentTimeMillis() - lastFrameTime;
		lastFrameTime = System.currentTimeMillis();
		previousFrames.add(diff);
		if(previousFrames.size() > 10) {
			previousFrames.remove();
		}
		double average = 0;
		for(long n : previousFrames) {
			average+=n;
		}
		average = average/previousFrames.size();
		return (int)(1000/average);
	}
	void drawShipDebugStats(Graphics g) {
		g.setColor(Color.BLUE);
		int totalSmoke = 0;
		for (int i = 0; i < ships.size(); i++) {
			g.drawString("Ship " + i + " smoke:" + ships.get(i).smoke.size(), 10, 105 + (25 * i));
			totalSmoke += ships.get(i).smoke.size();
		}
		g.setColor(Color.DARK_GRAY);
		g.drawString("Total smoke:" + totalSmoke, 10, 80);
	}

	void purgeShips() {
		for (int i = ships.size() - 1; i >= 0; i--) {
			if (ships.get(i).y < 0 && ships.get(i).smoke.size() <= 6) {
				ships.remove(i);
			}
		}
	}

	void addShip() {
		Ship ss = new Ship(gen.nextInt(WIDTH), HEIGHT + 10);
		//ss.speed = gen.nextInt(6) + 1;
		if(gen.nextInt(10)<1) {
			ss.isRainbow = true;
		}
		ships.add(ss);
	}

	void setup() {
		frame.add(this);
		frame.setVisible(true);
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(this);
		frame.pack();
		vxMult = 1 - offsetMax * 0.006;
		vyMult = 1 - offsetMax * 0.02;
		addShip();
		lastFrameTime = System.currentTimeMillis();
		timer.start();
	}

	public void paintComponent(Graphics g) {
		draw(g);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		System.out.println(keyCode);
		if(keyCode == 10) {
			Ship.allowColoredSmoke = !Ship.allowColoredSmoke;
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
