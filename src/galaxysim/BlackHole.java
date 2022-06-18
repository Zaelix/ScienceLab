package galaxysim;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class BlackHole extends Star {
	double spirality = 0.01;
	
	BlackHole(double x, double y, double width, double height, double mass) {
		super(x, y, width, height, mass);

	}

	BlackHole(double x, double y, double mass) {
		this(x, y, 50, 50, mass);
	}

	@Override
	protected void reassessStatus() {
		calculateMinMaxSatelliteHeight();
		checkIfOrbitIsStable();
	}

	@Override
	protected void calculateTemperature() {
		temperature = -1;
	}

	@Override
	protected void absorb(CelestialBody body) {
		mass += body.mass;
		if(satellites.contains(body))body.setParent(null);
		stealAllSatellitesFrom(body);
		calculateMinMaxSatelliteHeight();
		forceSatellitesToRecalculateStatus();
		findVictimBodies();
	}
	public void migrate() {
		Sector current = GalaxySim.getCurrentSector(x, y);
		if(!current.name.equals(currentSector)) {
			Sector previous = GalaxySim.getSectorByName(currentSector);
			previous.removeStar(this);
			current.addStar(this);
		}
	}
	
	@Override
	public void customDraw(Graphics g) {
		
		g.setColor(Color.BLACK);
		g.fillOval((int) drawX, (int) drawY, (int) drawWidth, (int) drawHeight);

		g.setColor(GalaxySim.gen.nextBoolean() ? Color.LIGHT_GRAY : Color.DARK_GRAY);
		g.drawArc((int)drawX, (int)drawY, (int)drawWidth, (int)drawHeight, GalaxySim.gen.nextInt(360), GalaxySim.gen.nextInt(150)+10);

		int start = GalaxySim.gen.nextInt(170)+180;
		int end = 180-start-GalaxySim.gen.nextInt(30);
		g.drawArc((int)(drawX-(drawWidth*0.33)), (int)(drawY+(drawHeight*0.4)), (int)(drawWidth+drawWidth*0.66), (int)(drawHeight/4), start, end);
		
//		for(double i = spirality; i < spirality+6*Math.PI; i+=0.01) {
//			double px = Math.cos(i)*i*10*Camera.mainCam.zoom;
//			double py = Math.sin(i)*i*10*Camera.mainCam.zoom;
//			g.drawOval((int)(drawX+px), (int)(drawY+py), 1, 1);
//		}
//		spirality+=0.01;
	}
	
	public void customUpdate() {
		super.customUpdate();
		if (victims.size() > 0)
			attractVictims();
	}

}
