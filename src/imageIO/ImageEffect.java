package imageIO;

import java.awt.image.BufferedImage;

public abstract class ImageEffect {
	String name;
	abstract BufferedImage apply(BufferedImage img);
}
