package multiPlayerCore;

import java.awt.Color;
import java.awt.Graphics;

public class Player {
	public static int nextNum = 1;
	public int playerNum;
	int x;
	int y;
	int width;
	int height;
	boolean up;
	boolean down;
	boolean left;
	boolean right;
	double speed = 1;

	Player(int x, int y, int width, int height) {
		playerNum = nextNum;
		nextNum++;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void update() {
		move();
	}

	public void move() {
		if (up) {
			y -= speed;
		}
		if (down) {
			y += speed;
		}
		if (left) {
			x -= speed;
		}
		if (right) {
			x += speed;
		}
	}

	public boolean giveCommand(String command) {
		if (command.equals("up")) {
			if (up == true) {
				left = false;
				right = false;
			}
			up = true;
			down = false;
			return true;
		} else if (command.equals("down")) {
			if (down == true) {
				left = false;
				right = false;
			}
			up = false;
			down = true;
			return true;
		} else if (command.equals("left")) {
			if (left == true) {
				up = false;
				down = false;
			}
			left = true;
			right = false;
			return true;
		} else if (command.equals("right")) {
			if (right == true) {
				up = false;
				down = false;
			}
			left = false;
			right = true;
			return true;
		} else if (command.equals("stop")) {
			up = false;
			down = false;
			left = false;
			right = false;
			return true;
		} else {
			return false;
		}
	}

	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillOval(x, y, width, height);
		g.setColor(Color.BLACK);
		g.drawOval(x, y, width, height);
		g.drawString(playerNum+"", x+width/2-3, y+height/2+3);
	}
}
