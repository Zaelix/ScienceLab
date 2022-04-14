package Auto2048;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Robot;

public class ObjectManager2048 {
	Color gameOverColor = new Color(143, 122, 103);
	AI2048 ai = new AI2048();
	public static Cell2048[][] grid = new Cell2048[4][4];
	public static Robot scanbot;
	int cellWidth;
	int cellHeight;

	public ObjectManager2048() {
		cellWidth = Auto2048.WIDTH / 4;
		cellHeight = Auto2048.HEIGHT / 4;
		try {
			scanbot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				grid[x][y] = new Cell2048(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
			}
		}
	}

	public void updateDisplay() {
		int sX = Auto2048.startX + cellWidth / 4;
		int sY = Auto2048.startY + cellHeight / 8;
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				// scanbot.mouseMove(sX+(x*cellWidth),sY+(y*cellHeight));
				Color cellColor = scanbot.getPixelColor(sX + (x * cellWidth), sY + (y * cellHeight));
				grid[x][y].setColor(cellColor);
			}
		}

	}

	public void update() {
		checkForGameOver();
		ai.step();
	}

	public void draw(Graphics g) {
		updateDisplay();
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				grid[x][y].draw(g);
			}
		}
	}

	public void checkForGameOver() {
		Color c = scanbot.getPixelColor(Auto2048.startX + 250, Auto2048.startY + 290);
		if (c.equals(gameOverColor)) AI2048.isActive = false;
	}
}
