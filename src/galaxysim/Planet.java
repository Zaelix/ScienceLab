package galaxysim;

import java.awt.Color;
import java.awt.Graphics;

public class Planet extends CelestialBody {
	private static Color[] colors = {new Color(73, 151, 254),new Color(95, 162, 217),new Color(156, 206, 247),
			new Color(255, 255, 250), new Color(255, 242, 212), new Color(237, 220, 135), new Color(230, 170, 96)};
	private static char[] planetClassifications = {'D', 'J', 'H', 'K', 'L', 'M', 'N'};
	
	char classification;
	Planet(double x, double y, double width, double height) {
		super(x, y, width, height);
		color = Color.GREEN;
		degree = GalaxySim.gen.nextInt(360);
		GalaxySim.planets++;
	}
	
	public void setParent(CelestialBody parent) {
		super.setParent(parent);
		calculateClassification();
		
	}
	private void calculateClassification() {
		// TODO Auto-generated method stub
		
	}

	private String calculateSizeClassification() {
		String sizeClass;
		if(width < 0.01)sizeClass = "Asteroidal";
		else if(width < 0.5)sizeClass = "Subterran";
		else if(width < 2)sizeClass = "Terran";
		else if(width < 10)sizeClass = "Superterran";
		else if(width < 50) sizeClass = "Megaterran";
		else if(width < 5000)sizeClass = "Ultraterran";
		else sizeClass = "Omegaterran";
		return sizeClass;
	}
	
	private String calculateHabitabilityIndex() {
		String habitability = "";
		if(mass>5) {
			
		}
		return habitability;
	}
	public String getInfo() {
		return "Habitable Planet, " + super.getInfo();
	}
}
