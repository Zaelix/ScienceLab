package imageIO;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JSlider;

public class PixelizerPanel extends JPanel {
	Pixelizer p;
	public PixelizerPanel(Pixelizer pixelizer) {
		p = pixelizer;
	}

	
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, p.img.getWidth(), p.img.getHeight());
		g.drawImage(p.img, 0, 50, null);
	}
	
	
}
