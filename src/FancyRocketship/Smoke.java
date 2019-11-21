package FancyRocketship;

import java.awt.Color;
import java.awt.Graphics2D;

class Smoke {
	double x;
	double y;
	double size = 10;
	double c = 50;
	double vx;
	double vy;
	int offset = 0;
	double vxMod = 0;
	float alpha = 1.0f;
	double alphaMod = 0.01;
	Color color;
	boolean hasColor = false;

	Smoke(double x, double y, double vx, double vy, double size) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.size = size;
		this.vxMod = vy * 0.01;
	}
	
	void draw(Graphics2D g) {
		x += vx;
		y += vy;

		if (Math.abs(0 - vx) < 0.02) {
			if (vx >= 0) {
				vx += vxMod;
			} else {
				vx -= vxMod;
			}
		}
		if (FancyRocketship.frameCount % FancyRocketship.offsetMax == offset) {
			vx *= FancyRocketship.vxMult;
			vy *= FancyRocketship.vyMult;
			vxMod = vy * 0.01;
			alphaMod = 0.001 + 0.01*Math.abs(vx);
		}
		if(hasColor) {
			g.setColor(color);
		}
		else {
			g.setColor(new Color((int) c, (int) c, (int) c));
		}
		g.fillOval((int) x, (int) y, (int) size, (int) size);
		size += 0.05;
		alpha -= alphaMod;
		//c += 0.15 + Math.abs(vx);
	}

	void setColor(Color color) {
		if(color == null) {
			this.color = null;
			hasColor = false;
		}
		else {
			this.color = color;
			hasColor = true;
		}
	}
	void setOffset(int offset) {
		this.offset = offset;
	}
}