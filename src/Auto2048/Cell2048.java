package Auto2048;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashMap;

public class Cell2048 {
	static HashMap<Color, Integer> colors = new HashMap<Color,Integer>();
	static Font textFont = new Font("Arial", Font.BOLD, 40);
	int x;
	int y;
	int width;
	int height;
	Color color;
	int value;
	static {
		colors.put(new Color(205,193,180), 0);
		colors.put(new Color(238,228,218), 2);
		colors.put(new Color(238,225,201), 4);
		colors.put(new Color(243,178,122), 8);
		colors.put(new Color(246,150,100), 16);
		colors.put(new Color(247,124,95), 32);
		colors.put(new Color(247,95,59), 64);
		colors.put(new Color(237,208,115), 128);
		colors.put(new Color(237,204,98), 256);
		colors.put(new Color(237,201,80), 512);
	}
	public Cell2048(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void setColor(Color c) {
		color = c;
		if(colors.get(c) != null) value = colors.get(c);
		else value = 0;
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
		if(AI2048.isActive) g.setColor(Color.BLACK);
		else g.setColor(Color.CYAN);
		g.drawRect(x, y, width, height);
		if(AI2048.isActive) g.setColor(Color.WHITE);
		else g.setColor(Color.CYAN);
		g.setFont(textFont);
		g.drawString(value+"", x+width/2, y+height/2);
	}
}
