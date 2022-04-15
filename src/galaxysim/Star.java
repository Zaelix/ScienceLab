package galaxysim;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Star extends CelestialBody{
	private static int alpha = 150;
	private static Color[] colors = {new Color(73, 151, 254,alpha),new Color(95, 162, 217,alpha),new Color(156, 206, 247,alpha),
			new Color(255, 255, 250,alpha), new Color(255, 242, 212,alpha), new Color(237, 220, 135,alpha), new Color(230, 170, 96,alpha)};
	private static char[] starClassifications = {'O', 'B', 'A', 'F', 'G', 'K', 'M'};
	
	char classification;
	Star(double x, double y, double radius) {
		super(x, y, radius);
		generateClassFromRadius(radius);
		calculateTemperature();
		generatePlanets();
		if(starImage == null) {
			//starImage = ImageIO.read(new File("src/galaxysim/star_rotate.jpg"));
			starImage = new ImageIcon("src/galaxysim/star.gif").getImage();
		}
		GalaxySim.stars++;
	}
	Star(double x, double y, int starClass){
		this(x,y,generateRadiusFromClass(starClass));
	}
	Star(double x, double y){
		this(x,y, getRandomClass());
	}
	protected void generateClassFromRadius(double radius) {
		int starClass;
		if(radius >= 6.6) starClass = 0; // O
		else if(radius >= 1.8) starClass = 1; // B
		else if(radius >= 1.4) starClass = 2;// A
		else if(radius >= 1.15) starClass = 3; // F
		else if(radius >= 0.96) starClass = 4;// G
		else if(radius >= 0.7) starClass = 5;// K
		else starClass = 6;// M
		color = colors[starClass];
		classification = starClassifications[starClass];
		//System.out.println("Class " + classification + ": " + color.getRed() + "," + color.getGreen() + "," + color.getBlue());
	}
	
	public static double generateRadiusFromClass(int starClass) {
		double radius;
		if(starClass == 0) radius = GalaxySim.gen.nextDouble() * 10 + 6.6;
		else if(starClass == 1) radius = GalaxySim.gen.nextDouble() * 4.8 + 1.8;
		else if(starClass == 2) radius = GalaxySim.gen.nextDouble() * 0.4 + 1.4;
		else if(starClass == 3) radius = GalaxySim.gen.nextDouble() * 0.25 + 1.15;
		else if(starClass == 4) radius = GalaxySim.gen.nextDouble() * 0.19 + 0.96;
		else if(starClass == 5) radius = GalaxySim.gen.nextDouble() * 0.26 + 0.7;
		else radius = GalaxySim.gen.nextDouble() * 0.7;
		return radius;
	}
	public static int getRandomClass() {
		double chance = GalaxySim.gen.nextDouble() * 99.88003;
		int starClass = -1;
		if(chance < 76.45) starClass = 6;
		else if(chance < 88.55) starClass = 5;
		else if(chance < 96.15) starClass = 4;
		else if(chance < 99.15) starClass = 3;
		else if(chance < 99.75) starClass = 2;
		else if(chance < 99.88) starClass = 1;
		else starClass = 0;
		return starClass;
	}
	
	private void generatePlanets() {
		int count = GalaxySim.gen.nextInt((int) (mass*8)+2);
		for(int i = 0; i < count; i++) {
			int scaleMod = GalaxySim.gen.nextInt(3) + 4;
			double dx = GalaxySim.gen.nextDouble() * (mass*300) + (mass*100);
			double dy = GalaxySim.gen.nextDouble() * (mass*300) + (mass*100);
			Planet p = new Planet(x+dx,y+dy, width/scaleMod, height/scaleMod);
			addSatellite(p);
		}
		//System.out.println(count + " planets created.");
	}
	
	public String getInfo() {
		return "Class "+classification + ", "+super.getInfo();
	}
	@Override
	protected void calculateTemperature() {
		if(classification == starClassifications[0]) temperature = GalaxySim.gen.nextDouble() * 30000 + 30000;
		else if(classification == starClassifications[1]) temperature = GalaxySim.gen.nextDouble() * 20000 + 10000;
		else if(classification == starClassifications[2]) temperature = GalaxySim.gen.nextDouble() * 2500 + 7500;
		else if(classification == starClassifications[3]) temperature = GalaxySim.gen.nextDouble() * 1500 + 6000;
		else if(classification == starClassifications[4]) temperature = GalaxySim.gen.nextDouble() * 800 + 5200;
		else if(classification == starClassifications[5]) temperature = GalaxySim.gen.nextDouble() * 1500 + 3700;
		else temperature = GalaxySim.gen.nextDouble() * 1300 + 2400;
	}
}
