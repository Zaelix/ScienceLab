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
		add(createSlider());
	}

	JSlider createSlider() {
		JSlider slider = new JSlider(JSlider.HORIZONTAL,
                1, 100, 1);
		slider.addChangeListener(p);
		
		//Turn on labels at major tick marks.
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setPreferredSize(new Dimension(400, 60));
		return slider;
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, p.img.getWidth(), p.img.getHeight());
		g.drawImage(p.img, 0, 0, null);
	}
	
	
}
