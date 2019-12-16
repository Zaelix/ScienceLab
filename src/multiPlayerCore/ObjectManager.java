package multiPlayerCore;

import java.awt.Color;
import java.awt.Graphics;

public class ObjectManager {
	GamePanel gp;
	boolean isAuthoritative;
	Player player;
	ObjectManager(GamePanel gp, boolean isAuthoritative) {
		this.gp = gp;
		this.isAuthoritative = isAuthoritative;
		player = new Player(100, 100, 50, 50);
	}

	public void update() {
		player.update();
	}

	public void draw(Graphics g) {
		player.draw(g);
	}
	
}
