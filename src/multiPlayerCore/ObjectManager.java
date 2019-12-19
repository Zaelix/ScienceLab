package multiPlayerCore;

import java.awt.Color;
import java.awt.Graphics;

public class ObjectManager {
	GamePanel gp;
	boolean isAuthoritative;
	Player player;
	Player player2;
	ObjectManager(GamePanel gp, boolean isAuthoritative) {
		this.gp = gp;
		this.isAuthoritative = isAuthoritative;
		player = new Player(100, 100, 50, 50);
		player2 = new Player(180, 100, 50, 50);
	}

	public void update() {
		player.update();
		player2.update();
	}

	public void draw(Graphics g) {
		player.draw(g);
		player2.draw(g);
	}
	
}
