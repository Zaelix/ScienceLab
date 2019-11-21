package FancyRocketship;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

class Ship {
	int x;
	int y;
	int size = 20;
	int flashing = 0;
	int bg;
	double speed = 1;
	int smokeOffset = 0;
	Random gen = new Random();
	Color c;
	boolean isRainbow = false;
	ArrayList<Smoke> smoke = new ArrayList<Smoke>();

	Ship(int x, int y) {
		this.x = x;
		this.y = y;
		c = new Color(gen.nextInt(255),gen.nextInt(255),gen.nextInt(255));
		size = gen.nextInt(50)+15;
	}

	void draw(Graphics g) {
		purgeSmoke();
		drawSmoke(g);
		// stroke(1);
		int red = gen.nextInt(65) + 190;
		if (flashing % 3 == 0) {
			g.setColor(new Color(red, 60, 60));
			g.fillOval(x - (int)(size*1.02), y + (int)(size*1.2), (int)(size*2), (int)(size*2));
		} else {
			g.setColor(new Color(red, 30, 30));
			g.fillOval(x - (int)(size*0.87), y + size, (int)(size*1.7), (int)(size*1.7));
		}
		flashing++;
		Color color = new Color(gen.nextInt(255),gen.nextInt(255),gen.nextInt(255));
		if(isRainbow) {
			c=color;
		}
		g.setColor(c);
		int[] xs = { x - size, x, x + size };
		int[] ys = {(int)( y + size*1.5), y, (int)( y + size*1.5) };
		g.fillPolygon(xs, ys, 3);
		for (int i = 0; i < 4; i++) {
			Smoke s = new Smoke(x - 8, y - (speed) + 50, gen.nextDouble() * 2 - 1, gen.nextDouble() * 3 + 2, 15);
			s.setOffset(smokeOffset++);
			if(isRainbow) {
				c=color;
			}
			s.setColor(c);
			if (smokeOffset == FancyRocketship.offsetMax) {
				smokeOffset = 0;
			}
			smoke.add(s);
		}
		y -= speed;
	}

	void drawSmoke(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		for (int i = 0; i < smoke.size(); i++) {
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, smoke.get(i).alpha));
			smoke.get(i).draw(g2d);
		}
	}

	void purgeSmoke() {
		for (int i = smoke.size() - 1; i >= 0; i--) {
			if (smoke.get(i).alpha <= 0 || smoke.get(i).x > FancyRocketship.WIDTH + 10 || smoke.get(i).x < -10
					|| smoke.get(i).y < -400 || smoke.get(i).y > FancyRocketship.HEIGHT + 10) {
				smoke.remove(i);
			}
		}
	}

	void setSmokeLimit(int bg) {
		this.bg = bg;
	}
}
