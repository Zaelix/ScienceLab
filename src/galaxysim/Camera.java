package galaxysim;

import java.awt.Graphics;

public class Camera {
	public static Camera mainCam;
	public Ship currentShip;
	public double zoom = 1;
	public double x;
	public double y;
	public double px;
	public double py;
	public double centerX;
	public double centerY;
	
	public boolean up;
	public boolean down;
	public boolean left;
	public boolean right;
	public boolean zoomIn;
	public boolean zoomOut;
	double speed = 5;
	
	private int[] shipXs = new int[] {GalaxySim.WIDTH/2 - 10, GalaxySim.WIDTH/2, GalaxySim.WIDTH/2 + 10};
	private int[] shipYs = new int[] {GalaxySim.HEIGHT/2 + 10, GalaxySim.HEIGHT/2 - 10, GalaxySim.HEIGHT/2 + 10};
	
	public Camera(double x, double y) {
		this.x = x;
		this.y = y;
		this.centerX = GalaxySim.WIDTH/2;
		this.centerY = GalaxySim.HEIGHT/2;
		if(mainCam == null) mainCam = this;
	}
	public void update() {
		if(currentShip == null) {
			if(up) y-=speed/zoom;
			if(down) y+=speed/zoom;
			if(left) x-=speed/zoom;
			if(right) x+=speed/zoom;
		}
		else {
			x = currentShip.x-centerX-currentShip.width/2;
			y = currentShip.y-centerY-currentShip.height/2;
		}

		if(zoomIn) setZoom(zoom+0.01);
		if(zoomOut) setZoom(zoom-0.01);
		
		px = x + centerX;
		py = y + centerY;
	}
	
	public void setZoom(double zoom) {
		if(zoom < 0.01) zoom = 0.01;
		this.zoom = zoom;
	}
	public void drawPlayer(Graphics g) {
		//g.setColor(Color.BLUE);
		//g.fillPolygon(shipXs, shipYs, 3);
		//ship.draw(g);
	}
}
