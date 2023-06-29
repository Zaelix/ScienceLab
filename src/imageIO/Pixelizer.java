package imageIO;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Pixelizer implements ChangeListener, ActionListener {
	Timer timer;
	JFrame frame;
	BufferedImage img;
	BufferedImage src;
	PixelizerPanel panel;
	Random gen = new Random();
	
	ImageEffect wobble;

	public static void main(String[] args) {
		Pixelizer p = new Pixelizer();
		p.load();
	}

	void load() {
		timer = new Timer(1000 / 30, this);
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		panel = new PixelizerPanel(this);
		wobble = new WobbleEffect();
		try {
			src = ImageIO.read(new File("src/imageIO/face.jpg"));
			frame.setPreferredSize(new Dimension(src.getWidth(), src.getHeight() + 90));
			img = pixelize(1);
			System.out.println("Loaded image file successfully! Width: " + src.getWidth() + ", Height: " + src.getHeight());
		} catch (IOException e) {
			e.printStackTrace();
		}
		panel.add(createSlider());
		frame.add(panel);
		timer.start();
		frame.pack();
	}
	
	JSlider createSlider() {
		JSlider slider = new JSlider(JSlider.HORIZONTAL,
                1, 100, 1);
		slider.addChangeListener(this);
		
		//Turn on labels at major tick marks.
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setPreferredSize(new Dimension(src.getWidth()-20, 40));
		return slider;
	}

	BufferedImage pixelize(int p) {
		BufferedImage img = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);
		System.out.println("P:" + p);
		int rgb = 0;
		for (int x = 0; x < src.getWidth(); x += p) {
			for (int y = 0; y < src.getHeight(); y += p) {
				rgb = src.getRGB(x, y);
				img = fillNewPixel(x, y, p, rgb, img);
			}
		}
		//colorize(img);
		return img;
	}

	BufferedImage fillNewPixel(int px, int py, int p, int rgb, BufferedImage img) {
		for (int x = px; x < px + p; x++) {
			for (int y = py; y < py + p; y++) {
				if (x < img.getWidth() && y < img.getHeight()) {
					img.setRGB(x, y, rgb);
				}
			}
		}
		return img;
	}

	void colorize(BufferedImage img) {
		
		int cr = gen.nextInt(10) >> 16;
		int cg = gen.nextInt(10) >> 8;
		int cb = gen.nextInt(10);

		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				if (x < img.getWidth() && y < img.getHeight()) {
					int rgb = img.getRGB(x, y);
					int red = (rgb & 0xff0000) >> 16;
					int green = (rgb & 0xff00) >> 8;
					int blue = rgb & 0xff;
					
					//int nrgb = calculateRGB(red, cr, green, cg, blue, cb);
					int nrgb = shiftRGB(rgb, 3);
					
					img.setRGB(x, y, nrgb);
				}
			}
		}
	}
	int shiftRGB(int rgb, int count) {
		int r = (rgb & 0xff0000) >> 16;
		int g = (rgb & 0xff00) >> 8;
		int b = rgb & 0xff;
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(r);
		list.add(g);
		list.add(b);
		for(int i = 0; i < count; i++) {
			int x = list.remove(0);
			list.add(x);
		}
		
		return 255<<24 | list.get(0)<<16 | list.get(1)<<8 | list.get(2);
	}
	int calculateRGB(int r1, int r2, int g1, int g2, int b1, int b2) {
		System.out.println();
		int r = Math.max(r1+r2, 255);
		int g = Math.max(g1+g2, 255);
		int b = Math.max(b1+b2, 255);
		int rgb = 255<<24 | r<<16 | g<<8 | b;
		
		return rgb;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		img = pixelize(source.getValue());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		img = wobble.apply(src);
		panel.repaint();
	}
}
