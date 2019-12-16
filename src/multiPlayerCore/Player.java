package multiPlayerCore;

import java.awt.Color;
import java.awt.Graphics;

public class Player {
	int x;
	int y;
	int width;
	int height;
	boolean up;
	boolean down;
	boolean left;
	boolean right;
	double speed = 1;
	String[] commands = {"up", "down", "left", "right"};

	Player(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void update() {
		move();
	}
	
	public void move() {
		if(up) {
			y-=speed;
		}
		if(down) {
			y+=speed;
		}
		if(left) {
			x-=speed;
		}
		if(right) {
			x+=speed;
		}
	}

	public boolean giveCommand(String command) {
		
		return true;
	}
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillOval(x, y, width, height);
	}
}
