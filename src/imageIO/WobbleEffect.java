package imageIO;

import java.awt.image.BufferedImage;

public class WobbleEffect extends ImageEffect{
	int counter = 5;
	@Override
	BufferedImage apply(BufferedImage original) {
		BufferedImage img = original.getSubimage(0, 0, original.getWidth(), original.getHeight());
		
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				int rgb = original.getRGB(x, y);
				
				img.setRGB(x, (int) (Math.max(0,y + Math.sin(x)*20)%(original.getHeight())), rgb);
			}
		}
		counter++;
		counter %= 30;
		System.out.println(counter);
		return img;
	}

}
