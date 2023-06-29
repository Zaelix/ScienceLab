package galaxysim;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Planet extends CelestialBody {
	private static int alpha = 150;
	private static Color[] colors = {new Color(200,0,0, alpha),new Color(0,200,0, alpha),new Color(0,0,200, alpha)};
	private static char[] planetClassifications = {'D', 'J', 'H', 'K', 'L', 'M', 'N'};
	
	String classification;
	Planet(double x, double y, double width, double height) {
		super(x, y, width, height);
		color = colors[0];
		degree = GalaxySim.gen.nextInt(360);
		GalaxySim.planets++;
		if(planetImage == null) {
			planetImage = new ImageIcon("src/galaxysim/planet.gif").getImage();
		}
		calculateMassFromRadius(width/200);
	}
	
	public void setParent(CelestialBody parent) {
		super.setParent(parent);
		calculateClassification();
	}
	private void calculateClassification() {
		classification = calculateSizeClassification();
		calculateTemperature();
		classification += " "+calculateHabitabilityIndex();
	}

	private String calculateSizeClassification() {
		String sizeClass;
		if(width <= 12)sizeClass = "Asteroidal";
		else if(width < 15)sizeClass = "Subterran";
		else if(width < 25)sizeClass = "Terran";
		else if(width < 35)sizeClass = "Superterran";
		else if(width < 70) sizeClass = "Subgiant";
		else if(width < 5000)sizeClass = "Giant";
		else sizeClass = "Supergiant";
		return sizeClass;
	}
	
	private String calculateHabitabilityIndex() {
		String habitability = "";
		if(temperature<200) {
			habitability = "Frozen World";
			color = colors[2];
		}
		else if(temperature<300) {
			habitability = "Habitable World";
			color = colors[1];
		}
		else {
			habitability = "Desert World";
			color = colors[0];
		}

		return habitability;
	}
	public String getInfo() {
		return classification + ", " + super.getInfo();
	}

	protected void reassessStatus() {
		calculateClassification();
		checkIfOrbitIsStable();
	}
	
	protected void calculateTemperature() {
		if(parent instanceof Star) {
			double lum = ((Star) parent).luminosity;
			temperature = lum / Math.pow(getDistanceFrom(parent)/200,2);
		}
		else temperature = -1;
	}
	protected void absorb(CelestialBody body) {
		mass += body.mass;
		calculateClassification();
		stealAllSatellitesFrom(body);
	}
	public void customDraw(Graphics g) {
		if(planetImage != null) {
			g.drawImage(planetImage, (int)drawX, (int)drawY, (int)drawWidth, (int)drawHeight, null);
		}
		g.setColor(color);
		g.fillOval((int)drawX, (int)drawY, (int)drawWidth, (int)drawHeight);
	}
}
