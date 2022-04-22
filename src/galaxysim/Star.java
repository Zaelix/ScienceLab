package galaxysim;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Star extends CelestialBody{
	private static int alpha = 150;
	private static Color[] colors = {new Color(73, 151, 254,alpha),new Color(95, 162, 217,alpha),new Color(156, 206, 247,alpha),
			new Color(255, 255, 250,alpha), new Color(255, 242, 212,alpha), new Color(237, 220, 135,alpha), new Color(230, 170, 96,alpha)};
	private static char[] starClassifications = {'O', 'B', 'A', 'F', 'G', 'K', 'M'};
	double luminosity;
	char classification;
	boolean hasCombined = false;
	Star(double x, double y, double radius) {
		super(x, y, radius);
		generateClassFromRadius(radius);
		calculateTemperature();
		calculateLuminosity();
		generatePlanets();
		if(starImage == null) {
			//starImage = ImageIO.read(new File("src/galaxysim/star_rotate.jpg"));
			starImage = new ImageIcon("src/galaxysim/star_alpha.gif").getImage();
		}
		GalaxySim.stars++;
	}
	Star(double x, double y, int starClass){
		this(x,y,calculateRadiusFromClass(starClass));
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
	
	public static double calculateRadiusFromClass(int starClass) {
		double radius;
		if(starClass == 0) radius = GalaxySim.gen.nextDouble() * 10 + 6.6;
		else if(starClass == 1) radius = GalaxySim.gen.nextDouble() * 4.8 + 1.8;
		else if(starClass == 2) radius = GalaxySim.gen.nextDouble() * 0.4 + 1.4;
		else if(starClass == 3) radius = GalaxySim.gen.nextDouble() * 0.25 + 1.15;
		else if(starClass == 4) radius = GalaxySim.gen.nextDouble() * 0.19 + 0.96;
		else if(starClass == 5) radius = GalaxySim.gen.nextDouble() * 0.26 + 0.7;
		else radius = GalaxySim.gen.nextDouble() * 0.7 + 0.3;
		return radius;
	}
	
	public double calculateRadiusFromMass() {
		double radius;
		if(mass >= 16) radius = GalaxySim.gen.nextDouble() * 10 + 6.6;
		else if(mass >= 2.1) radius = GalaxySim.gen.nextDouble() * 4.8 + 1.8;
		else if(mass >= 1.4) radius = GalaxySim.gen.nextDouble() * 0.4 + 1.4;
		else if(mass >= 1.04) radius = GalaxySim.gen.nextDouble() * 0.25 + 1.15;
		else if(mass >= 0.8) radius = GalaxySim.gen.nextDouble() * 0.19 + 0.96;
		else if(mass >= 0.45) radius = GalaxySim.gen.nextDouble() * 0.26 + 0.7;
		else radius = GalaxySim.gen.nextDouble() * 0.7 + 0.3;
		this.width = radius*200;
		this.height = radius*200;
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
			double dx = GalaxySim.gen.nextDouble() * (mass*400) + (width);
			Planet p = new Planet(x+dx,y, width/scaleMod, height/scaleMod);
			addSatellite(p);
		}
		//System.out.println(count + " planets created.");
	}
	
	public String getInfo() {
		return "Class "+classification + ", "+super.getInfo() + ", Luminosity " + String.format("%.1f", luminosity);
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
	
	protected void calculateLuminosity() {
		double surfaceArea = 4 * Math.PI * Math.pow(width,2);
		luminosity = (temperature / surfaceArea) * (2 * Math.PI * Math.pow(width,2));
	}
	
	protected void combineWith(Star other) {
		Star survivor;
		Star trash;
		if(other.mass > this.mass) {
			survivor = other;
			trash = this;
		}
		else {
			survivor = this;
			trash = other;
		}
		survivor.mass += trash.mass;
		double r = survivor.calculateRadiusFromMass();
		survivor.calculateTemperature();
		survivor.calculateLuminosity();
		survivor.generateClassFromRadius(r);
		Sector s = GalaxySim.getSectorByName(trash.currentSector);
		if(s != null) s.removeStar(trash);
		else System.out.println(trash.currentSector + " is an invalid Sector.");
		GalaxySim.stars--;
		if(trash.satellites != null)	GalaxySim.planets -= trash.satellites.size();
		
		hasCombined = true;
		other.hasCombined = true;
	}
	public void draw(Graphics g) {
		super.draw(g);
		if(starImage != null) {
			g.drawImage(starImage, (int)(drawX-(drawWidth/4.45)), (int)(drawY-(drawHeight/4.45)), (int)(drawWidth*1.45), (int)(drawHeight*1.45), null);
		}
		g.setColor(color);
		g.fillOval((int)drawX, (int)drawY, (int)drawWidth, (int)drawHeight);
//		if(hasCombined) {
//			g.setColor(Color.CYAN);
//			g.drawOval((int)(drawX-(int)(drawWidth/2)), (int)(drawY-(int)(drawHeight/2)), (int)(drawWidth*2), (int)(drawHeight*2));
//		}
	}
}
