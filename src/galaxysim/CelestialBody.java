package galaxysim;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class CelestialBody extends GameObject {
	protected static Image starImage;
	protected static Image planetImage;
	CelestialBody parent;
	ArrayList<CelestialBody> satellites;
	Color color;
	double mass;
	double orbitalPeriod;
	double orbitalHeight;
	double degree = 0;
	double temperature;
	
	CelestialBody(double x, double y, double width, double height) {
		super(x, y, width, height);
	}
	CelestialBody(double x, double y, double radius) {
		super(x, y, (int)(radius*100), (int)(radius*100));
		calculateMassFromRadius(radius);
	}
	
	protected void calculateMassFromRadius(double radius) {
		if(radius >= 6.6) mass = GalaxySim.gen.nextDouble() * 50 + 16; // O
		else if(radius >= 1.8) mass = GalaxySim.gen.nextDouble() * 13.9 + 2.1; // B
		else if(radius >= 1.4) mass = GalaxySim.gen.nextDouble() * 0.7 + 1.4; // A
		else if(radius >= 1.15) mass = GalaxySim.gen.nextDouble() * 0.36 + 1.04; // F
		else if(radius >= 0.96) mass = GalaxySim.gen.nextDouble() * 0.24 + 0.8; // G
		else if(radius >= 0.7) mass = GalaxySim.gen.nextDouble() * 0.35 + 0.45; // K
		else mass = GalaxySim.gen.nextDouble() * 0.37 + 0.08; // M
	}
	
	
	
	protected void addSatellite(CelestialBody satellite) {
		if(satellites == null) satellites = new ArrayList<CelestialBody>();
		satellites.add(satellite);
		satellite.setParent(this);
	}
	
	protected void setParent(CelestialBody parent) {
		this.parent = parent;
		orbitalHeight = getDistanceFrom(parent);
		orbitalPeriod = Math.max((100-orbitalHeight)/15, 0.1); // Magic number, change later
	}
	protected abstract void calculateTemperature();
	public void update() {
		if(parent != null) orbit();
		if(satellites != null) for(CelestialBody s : satellites) s.update();
	}
	
	public void orbit() {
		x = parent.x+Math.cos(Math.toRadians(degree)) * orbitalHeight;
		y = parent.y+Math.sin(Math.toRadians(degree)) * orbitalHeight;
		degree+=orbitalPeriod;
	}
	
	public void draw(Graphics g) {
		double relativeX = Camera.mainCam.centerX + ((this.x - Camera.mainCam.px)*Camera.mainCam.zoom);
		double relativeY = Camera.mainCam.centerY + ((this.y - Camera.mainCam.py)*Camera.mainCam.zoom);
		double drawX = (relativeX-(width/2*Camera.mainCam.zoom));
		double drawY = (relativeY-(height/2*Camera.mainCam.zoom));
		double drawWidth = Math.max(width*Camera.mainCam.zoom,2);
		double drawHeight = Math.max(height*Camera.mainCam.zoom,2);
		if(this instanceof Star && starImage != null) {
			g.drawImage(starImage, (int)(drawX-(drawWidth/4.45)), (int)(drawY-(drawHeight/4.45)), (int)(drawWidth*1.45), (int)(drawHeight*1.45), null);
		}
		g.setColor(color);
		g.fillOval((int)drawX, (int)drawY, (int)drawWidth, (int)drawHeight);
		if(satellites != null) for(CelestialBody s : satellites) s.draw(g);
	}
	
	public boolean wasClicked(int x, int y) {
		double drawx = Camera.mainCam.centerX + ((this.x - Camera.mainCam.px)*Camera.mainCam.zoom);
		double drawy = Camera.mainCam.centerY + ((this.y - Camera.mainCam.py)*Camera.mainCam.zoom);
		if(getDistanceFrom(drawx, drawy,x,y) < width/2) return true;
		return false;
	}
	
	public String getInfo() {
		return "Radius " + width + ", Mass " + String.format("%.2f", mass) + ", Temperature: " + (int)temperature + "K";
	}
}
