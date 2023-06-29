package clickers;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Idleon extends JPanel implements ActionListener {
	JFrame f = new JFrame();
	JFrame display = new JFrame();
	JPanel p = new JPanel();
	JButton find = new JButton("Find");
	JButton start = new JButton("Start");
	JButton stop = new JButton("Stop");
	Timer t;
	Robot m;

	Dimension size;
	BufferedImage screen;

	Rectangle board;
	Rectangle indSpace;

	Color failBarColor = new Color(229, 96, 99);
	Color failBarBottomColor = new Color(125, 20, 44);
	Color passBarColor = new Color(123, 205, 38);
	Color passBarBottomColor = new Color(38, 138, 19);
	Color bonusBarColor = new Color(252, 255, 122);
	Color bonusBarBottomColor = new Color(231, 168, 39);

	Color avgIndicatorColor = new Color(182, 233, 57);

	boolean hasFoundGameBoard = false;
	boolean isRunning = false;
	boolean hasBars = false;
	int boardStartX = 0;
	int boardStartY = 0;
	int boardEndX = 0;
	int boardEndY = 0;
	int boardWidth = 0;

	int passStartX = 0;
	int passEndX = 0;

	int bonusStartX = 0;
	int bonusEndX = 0;

	int indX = 0;
	int predX = 0;
	double indVel = 0;
	long lastIndTime = 0;
	private long timeSinceLastIndCheck;
	private Long[] checkTimes = new Long[40];
	private short checkIndex = 0;
	private int avgVelocity = 0;

	public static void main(String[] args) {
		Idleon i = new Idleon();
		i.start();
	}

	private void start() {
		try {
			m = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		find.addActionListener(this);
		start.addActionListener(this);
		stop.addActionListener(this);
		p.add(find);
		p.add(start);
		p.add(stop);
		f.add(p);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		display.setPreferredSize(new Dimension(300, 80));
		this.setPreferredSize(new Dimension(300, 80));
		display.add(this);
		display.setVisible(true);
		f.pack();
		display.pack();
		t = new Timer(1000 / 120, this);
		findBoard();
		findBars();
		t.start();
	}

	private void findBoard() {
		hasFoundGameBoard = false;
		size = Toolkit.getDefaultToolkit().getScreenSize();
		screen = m.createScreenCapture(new Rectangle(size.width, size.height));
		int count = 0;
		int sx = 0;
		int sy = 0;
		int h = screen.getHeight();
		int w = screen.getWidth();
		check: for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				Color c = new Color(screen.getRGB(x, y));
				// Color c = m.getPixelColor(x, y);
				if (areSimilarColors(c, failBarColor, 8) && count == 0) {
					sx = x;
					sy = y;
					count++;
				} else if (areSimilarColors(c, failBarColor, 8)) {
					count++;
				} else {
					count = 0;
				}
				if (count > 30 && !hasFoundGameBoard) {
					boardStartX = sx;
					boardStartY = sy;
					hasFoundGameBoard = true;
					System.out.println("Found board start at " + boardStartX + "," + boardStartY);
					break check;
				}
			}
		}
		count = 0;
		sx = 0;
		sy = 0;
		end:for (int y = h - 1; y >= boardStartY; y--) {
			for (int x = w - 1; x >= boardStartX; x--) {
				Color c = new Color(screen.getRGB(x, y));
				if (areSimilarColors(c, failBarBottomColor, 8) && count == 0) {
					sx = x;
					sy = y;
					count++;
				}else if (areSimilarColors(c, failBarBottomColor, 8)) {
					count++;
				} else {
					count = 0;
				}
				if (count > 30) {
					boardEndX = sx;
					boardEndY = sy;
					display.setPreferredSize(new Dimension(boardEndX - boardStartX, 100));
					display.pack();
					System.out.println("Found board end at " + boardEndX + "," + boardEndY);
					break end;
				}
			}
		}
		//System.out.println("{"+boardStartX+","+boardStartY+","+boardEndX+","+boardEndY+"}");
		int bHeight = boardEndY-boardStartY;
		int bWidth = boardEndX-boardStartX;
		board = new Rectangle(boardStartX, boardStartY, bWidth, bHeight);
		indSpace = new Rectangle(boardStartX, boardStartY-bHeight, bWidth, bHeight);
		
//		tlF.setBounds(boardStartX-120, boardStartY, 0, 0);
//		trF.setBounds(boardEndX, boardStartY, 0, 0);
//		blF.setBounds(boardStartX-120, boardEndY, 0, 0);
//		brF.setBounds(boardEndX, boardEndY, 0, 0);
		//display.setBounds(area.x, area.y+50, area.width, 100);
	}

	private void findBars() {
		if(!hasFoundGameBoard) return;
		hasBars = false;
		screen = m.createScreenCapture(board);
		int pX = 0;
		int bX = 0;
		for (int x = 0; x < board.width; x++) {
			Color c = new Color(screen.getRGB(x, 5));
			if (areSimilarColors(c, passBarColor, 8) && pX == 0) {
				pX = x;
			}
			if (areSimilarColors(c, bonusBarColor, 8) && bX == 0) {
				bX = x;
			}
		}

		int pEX = 0;
		int bEX = 0;
		for (int x = boardEndX - boardStartX - 1; x >= 0; x--) {
			Color c = new Color(screen.getRGB(x, 0));
			if (areSimilarColors(c, passBarColor, 8) && pEX == 0) {
				pEX = x;
			}
			if (areSimilarColors(c, bonusBarColor, 8) && bEX == 0) {
				bEX = x;
			}
		}
		passStartX = pX;
		passEndX = pEX;
		bonusStartX = bX;
		bonusEndX = bEX;
		if (pX == 0 && pEX == 0 && bX == 0 && bEX == 0) {
			hasFoundGameBoard = false;
		}
		else {
			hasBars = true;
		}
		// System.out.println("Pass: " + passStartX + "->" + passEndX);
		// System.out.println("Bonus: " + bonusStartX + "->" + bonusEndX);
	}

	private void findIndicator() {
		screen = m.createScreenCapture(indSpace);
		int count = 0;
		int sx = -1;
		int prev = indX;
		check: for (int y = board.height-1; y >= 0; y--) {
			for (int x = board.width-1; x >= 0; x--) {
				Color c = new Color(screen.getRGB(x, y));
				if (areSimilarColors(c, avgIndicatorColor, 25)) {
					sx = x;
					count++;
				} else if (count > 5) {
					count = 0;
					long currentIndTime = System.currentTimeMillis();
					markTime(currentIndTime);
					//indVel = (indX-prev)/(timeSinceLastIndCheck);
					indX = (int) (sx);
					indVel = (indX-prev);
					break check;
				}
			}
		}
	}
	
	private void markTime(long time) {
		timeSinceLastIndCheck = time-lastIndTime;
		checkTimes[checkIndex] = timeSinceLastIndCheck;
		checkIndex = (short) (checkIndex+1 >= checkTimes.length ? 0 : checkIndex+1);
		lastIndTime = time;
		//lastIndTime = checkIndex == 0 ? checkTimes[checkTimes.length-1] : checkTimes[checkIndex-1];
	}
	
	private int getAvgFrameTime() {
		int sum = 0;
		for(long time : checkTimes) {
			sum += time;
		}
		return sum / checkTimes.length;
	}

	private boolean areSimilarColors(Color a, Color b, int dist) {
		int dr = Math.abs(a.getRed() - b.getRed());
		int dg = Math.abs(a.getGreen() - b.getGreen());
		int db = Math.abs(a.getBlue() - b.getBlue());
		if (dr < dist && dg < dist && db < dist)
			return true;
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == t) {
			update();
		} else if (e.getSource() == find) {
			findBoard();
		} else if (e.getSource() == start) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			isRunning = true;
		} else if (e.getSource() == stop) {
			isRunning = false;
		}
		repaint();
	}

	private void update() {
		if (!hasFoundGameBoard) {
			findBoard();
			findBars();
		}
		else {
			findIndicator();
			//findBars();
			waitForTarget();
		}
	}

	private void waitForTarget() {
		int width;
		if (hasFoundGameBoard && isRunning) {
			width = (bonusEndX - bonusStartX)/3;
			long currentIndTime = System.currentTimeMillis();
			long timeDelta = lastIndTime-currentIndTime;
			int avgFT = getAvgFrameTime();
			double ftRatio = 1;
			if(timeDelta != 0) ftRatio =  avgFT / timeDelta;
			
			int x = (int) (indX+(indVel / ftRatio));
			predX = x;
			if(bonusStartX == 0 && bonusEndX == 0) {
				width = (passEndX - passStartX)/3;
				if (x > passStartX+width && x < passEndX-width) {
					click("Pass",passStartX,passEndX,x);
				}
			}
			else if (x > bonusStartX+width && x < bonusEndX-width) {
				click("Bonus",bonusStartX,bonusEndX,x);
			}
		}
	}
	
	private void click(String target, int x, int y, int ix) {
		if(hasBars) {
			
			m.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			m.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			System.out.println("Clicking for " + target + " at " + x + ","+ y + ", indicator seen at " + indX + " but is predicted at " + ix + ", velocity is " + indVel);
		}
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		findBars();
	}

	private void drawMiniGameBar(Graphics g) {
		if (hasFoundGameBoard) {
			g.setColor(Color.RED);
			g.fillRect(0, 0, boardEndX - boardStartX, 80);

			g.setColor(passBarColor);
			g.fillRect(passStartX, 0, passEndX - passStartX, 80);

			g.setColor(bonusBarColor);
			g.fillRect(bonusStartX, 0, bonusEndX - bonusStartX, 80);

			g.setColor(Color.CYAN);
			g.fillRect(indX, 0, 4, 80);
			
			g.setColor(Color.PINK);
			g.fillRect((int) (predX), 0, 4, 80);
		} else {
			g.setColor(Color.CYAN);
			g.fillRect(0, 0, 300, 80);
		}
	}

	public void paintComponent(Graphics g) {
		drawMiniGameBar(g);
	}
}
