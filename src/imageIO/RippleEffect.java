package imageIO;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RippleEffect extends JPanel implements ActionListener, ChangeListener {

	JFrame frame = new JFrame();
	Timer timer;
	BufferedImage inputImage;
	BufferedImage outputImage;


	double radius = 20;
	double amplitude = 10;
	double frequency = 10.0;

	double ampSpeed = 2;

	JSlider radiusSlider;
	JSlider ampSlider;
	JSlider freqSlider;
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, outputImage.getWidth(), outputImage.getHeight());
		draw(g);
		amplitude-=ampSpeed;
		if(amplitude > 15 || amplitude < 5) ampSpeed *= -1;
	}

	public void draw(Graphics g) {
		// Set the center point of the ripple effect
		int centerX = outputImage.getWidth() / 2;
		int centerY = outputImage.getHeight() / 2;

		// Set the parameters of the ripple effect

		// Iterate over all the pixels in the output image
		for (int y = 0; y < outputImage.getHeight(); y++) {
			for (int x = 0; x < outputImage.getWidth(); x++) {
				// Calculate the distance from the center point
				double distance = Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));

				// Calculate the angle of the point relative to the center point
				double angle = Math.atan2(y - centerY, x - centerX);

				// Calculate the displacement of the point due to the ripple effect
				double displacement = amplitude * Math.sin(distance / radius * frequency - Math.PI / 2);

				// Calculate the new position of the point
				double newX = x + displacement * Math.cos(angle);
				double newY = y + displacement * Math.sin(angle);

				int rgb = inputImage.getRGB(x, y);
				if (newX < outputImage.getWidth() && newY < outputImage.getHeight() && newX >= 0 && newY >= 0) {
					// Draw the pixel from the input image onto the output image
					outputImage.setRGB((int) newX, (int) newY, rgb);
				}
			}
		}
		g.drawImage(outputImage, 0, 50, null);
	}

	public static void main(String[] args) {
		RippleEffect r = new RippleEffect();
		r.start();
	}
	
	public void start() {
		frame.setVisible(true);
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Load the input image
		try {
			inputImage = ImageIO.read(new File("src/imageIO/spongebob.jpg"));
			outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(),
					BufferedImage.TYPE_INT_ARGB);

			// Create a new BufferedImage for the output image
			//frame.setPreferredSize(new Dimension(500, 500));
			frame.setPreferredSize(new Dimension(inputImage.getWidth()+20, inputImage.getHeight()+50));
			setPreferredSize(new Dimension(inputImage.getWidth(), inputImage.getHeight()));
			radiusSlider = createSlider("Radius",1,50,30,1,10);
			ampSlider = createSlider("Amplitude",1,30,1,1,10);
			freqSlider = createSlider("Frequency",1,15,10,1,5);
			add(radiusSlider);
			add(ampSlider);
			add(freqSlider);
			frame.pack();
			timer = new Timer(1000 / 10, this);
			timer.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}
	
	JSlider createSlider(String name, int min, int max, int value, int minorTick, int majorTick) {
		JSlider slider = new JSlider(JSlider.HORIZONTAL,
				min, max, value);
		slider.addChangeListener(this);
		
		//Turn on labels at major tick marks.
		slider.setMajorTickSpacing(majorTick);
		slider.setMinorTickSpacing(minorTick);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setPreferredSize(new Dimension(inputImage.getWidth()/4, 40));
		slider.setName(name);
		slider.setToolTipText(name);
		return slider;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		if(source == radiusSlider) {
			radius = radiusSlider.getValue();
		}
		if(source == ampSlider) {
			amplitude = ampSlider.getValue();
		}
		if(source == freqSlider) {
			frequency = freqSlider.getValue();
		}
	}
}