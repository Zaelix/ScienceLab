package ChatClient;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

public class ChatPanel extends JPanel {
	private Random gen = new Random();
	private int theme = 2;
	private int shifter = 0;

	ChatPanel(int shifter, int theme) {
		this.setShifter(shifter);
		this.setTheme(theme);
	}

	public void paintComponent(Graphics g) {
		if (getTheme() == 0) {
			drawPlain(g);
		}
		if (getTheme() == 1) {
			drawStatic(g);
		}
		if (getTheme() == 2) {
			drawRainbow(g);
		}
		if (getTheme() == 3) {
			drawPattern(g);
		}
	}


	public void changeTheme() {
		setTheme(getTheme() + 1);
		if (getTheme() > 3) {
			setTheme(0);
		}
	}

	public void drawPlain(Graphics g) {
		Color c = g.getColor();
		c = new Color(c.getRed()+(gen.nextInt(3)-1),c.getRed()+(gen.nextInt(3)-1),c.getRed()+(gen.nextInt(3)-1));
		g.setColor(c);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	public void drawStatic(Graphics g) {
		int red = 0;
		int green = 0;
		int blue = 0;
		setShifter(getShifter() + 1);
		if (getShifter() % 3 == 0) {
			for (int x = 0; x < getWidth(); x += 5) {
				for (int y = 0; y < getHeight(); y += 5) {
					red = gen.nextInt(255);
					green = gen.nextInt(255);
					blue = gen.nextInt(255);
					g.setColor(new Color(red, green, blue));
					g.fillRect(x, y, 5, 5);
				}
			}
		}
	}

	public void drawRainbow(Graphics g) {
		int red = 0;
		int green = 0;
		int blue = 0;
		setShifter(getShifter() + 1);
		if (getShifter() % 3 == 0) {
			for (int x = 0; x < getWidth(); x += 5) {
				red = gen.nextInt(255);
				green = gen.nextInt(255);
				blue = gen.nextInt(255);
				for (int y = 0; y < getHeight(); y += 5) {
					g.setColor(new Color(red, green, blue));
					g.fillRect(x, y, 5, 5);
				}
			}
		}
	}

	public void drawPattern(Graphics g) {
		int red = 0;
		int green = 0;
		int blue = 0;
		setShifter(getShifter() + 1);
		for (int x = 0; x < getWidth(); x += 5) {
			red = (x + getShifter()) % 255;
			for (int y = 0; y < getHeight(); y += 5) {
				green = (y + getShifter()) % 255;
				blue = (x + y) % 255;
				g.setColor(new Color(red, green, blue));
				g.fillRect(x, y, 5, 5);
			}
		}
	}

	public int getShifter() {
		return shifter;
	}

	public void setShifter(int shifter) {
		this.shifter = shifter;
	}

	public int getTheme() {
		return theme;
	}

	public void setTheme(int theme) {
		this.theme = theme;
	}
}
