package galaxysim;

import java.awt.Graphics;

public abstract class GameObject {
	static int speedLimit = 150;
	double x;
	double y;
	double width;
	double height;

	double xVelocity;
	double yVelocity;
	
	double drawX;
	double drawY;
	double drawWidth;
	double drawHeight;
	
	GameObject(double x,double y, double width, double height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public double getDistanceFrom(GameObject other) {
		return getDistanceFrom(other.x, other.y);
	}
	public double getDistanceFrom(double x2, double y2) {
		return Math.sqrt(Math.pow(x2-x, 2) + Math.pow(y2-y, 2));
	}
	public static double getDistanceFrom(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
	}
	public abstract void draw(Graphics g);
	
	public void update() {
		customUpdate();
		if(xVelocity>speedLimit) xVelocity = speedLimit;
		if(xVelocity<-speedLimit) xVelocity = -speedLimit;
		if(yVelocity>speedLimit) yVelocity = speedLimit;
		if(yVelocity<-speedLimit) yVelocity = -speedLimit;
		x += xVelocity;
		y += yVelocity;
	}
	
	protected abstract void customUpdate();
}
