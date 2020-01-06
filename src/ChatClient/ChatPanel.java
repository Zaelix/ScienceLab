package ChatClient;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

public class ChatPanel extends JPanel {
	Random gen = new Random();
	public static int theme = 2;
	static int shifter = 0;

	public void paintComponent(Graphics g) {
		if (theme == 0) {
			drawStatic(g);
		}
		if (theme == 1) {
			drawRainbow(g);
		}
		if (theme == 2) {
			drawPattern(g);
		}
	}

	public static void setTheme(int newTheme) {
		theme = newTheme;
	}

	public static void changeTheme() {
		theme++;
		if (theme > 2) {
			theme = 0;
		}
	}

	public void drawStatic(Graphics g) {
		int red = 0;
		int green = 0;
		int blue = 0;
		shifter++;
		if (shifter % 20 == 0) {
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

	public void drawPattern(Graphics g) {
		int red = 0;
		int green = 0;
		int blue = 0;
		shifter++;
		for (int x = 0; x < getWidth(); x += 5) {
			red = (x + shifter) % 255;
			for (int y = 0; y < getHeight(); y += 5) {
				green = (y + shifter) % 255;
				blue = (x + y) % 255;
				g.setColor(new Color(red, green, blue));
				g.fillRect(x, y, 5, 5);
			}
		}
	}

}
