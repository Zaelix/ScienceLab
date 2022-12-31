package galaxysim;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Ship extends GameObject {
	Image img;
	double angle = 0;
	double targetAngle;
	public boolean up;
	public boolean down;
	public boolean left;
	public boolean right;
	double speed = 5;

	Ship(double x, double y, double width, double height) {
		super(x, y, width, height);
		img = new ImageIcon("src/galaxysim/ship.png").getImage();
	}

	@Override
	public void draw(Graphics g) {
	Graphics2D g2d = (Graphics2D) g.create((int) 0, (int) 0, (int) GalaxySim.WIDTH,
				(int) GalaxySim.HEIGHT);
		
		drawX = (Camera.mainCam.centerX + ((this.x - Camera.mainCam.px) * Camera.mainCam.zoom)
				- (width / 2 * Camera.mainCam.zoom));
		drawY = (Camera.mainCam.centerY + ((this.y - Camera.mainCam.py) * Camera.mainCam.zoom)
				- (height / 2 * Camera.mainCam.zoom));
		drawWidth = Math.max(width * Camera.mainCam.zoom, 2);
		drawHeight = Math.max(height * Camera.mainCam.zoom, 2);
		//g.drawLine((int)drawX, (int)drawY, (int)Camera.mainCam.centerX, (int)Camera.mainCam.centerY);
		g2d.translate((int)(drawX), (int)(drawY));
		g2d.rotate(Math.toRadians(angle));
		g2d.drawImage(img, (int)(-(drawWidth/2)), (int)(-(drawHeight/2)), (int) drawWidth, (int) drawHeight, null);
		g2d.rotate(-Math.toRadians(angle));

	}
	
	public void customUpdate() {
		xVelocity*=0.97;
		yVelocity*=0.97;
		if(up) {
			yVelocity=-speed/Camera.mainCam.zoom;
			//angle = 0;
		}
		if(down) {
			yVelocity=speed/Camera.mainCam.zoom;
			//angle = 180;
		}
		if(left) {
			xVelocity=-speed/Camera.mainCam.zoom;
			//angle = -90;
		}
		if(right) {
			xVelocity=speed/Camera.mainCam.zoom;
			//angle = 90;
		}
		
		targetAngle = Math.toDegrees((Math.atan(yVelocity/xVelocity))+Math.toRadians(90));
		if(xVelocity < 0) targetAngle=targetAngle+180;
		if(angle>270 && Math.abs(angle-targetAngle) > 180) angle -= 360;
		if(angle<90 && Math.abs(angle-targetAngle) > 180) angle += 360;
		if(angle > targetAngle) angle-=Math.abs(angle-targetAngle)*0.2;
		else if(angle < targetAngle) angle+=Math.abs(angle-targetAngle)*0.2;
		//System.out.println("Angle: " + angle + ", target: " + targetAngle);
	}
}
