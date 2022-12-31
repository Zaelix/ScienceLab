package galaxysim;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Star extends CelestialBody {
	private static int alpha = 150;
	private static Color[] colors = { new Color(73, 151, 254, alpha), new Color(95, 162, 217, alpha),
			new Color(156, 206, 247, alpha), new Color(255, 255, 250, alpha), new Color(255, 242, 212, alpha),
			new Color(237, 220, 135, alpha), new Color(230, 170, 96, alpha) };
	private static char[] starClassifications = { 'O', 'B', 'A', 'F', 'G', 'K', 'M' };
	double luminosity;
	char classification;
	boolean hasCombined = false;

	ArrayList<Star> victims = new ArrayList<Star>();

	Star(double x, double y, double width, double height, double mass){
		super(x,y,width,height);
		this.mass = mass;
		calculateMinMaxSatelliteHeight();
	}
	
	Star(double x, double y, double mass) {
		super(x, y, calculateRadiusFromMass(mass), false);
		setRadius(calculateRadiusFromMass(mass));
		generateClassFromRadius(calculateRadiusFromMass(mass));
		this.mass = mass;
		calculateTemperature();
		calculateLuminosity();
		calculateMinMaxSatelliteHeight();
		generatePlanets();
		if (starImage == null) {
			// starImage = ImageIO.read(new File("src/galaxysim/star_rotate.jpg"));
			starImage = new ImageIcon("src/galaxysim/star_alpha.gif").getImage();
		}
		GalaxySim.stars++;
	}

	Star(double x, double y, int starClass) {
		this(x, y, calculateMassFromClass(starClass));
	}

	Star(double x, double y) {
		this(x, y, getRandomClass());
	}

	protected void generateClassFromRadius(double radius) {
		int starClass;
		if (radius >= 6.6)
			starClass = 0; // O
		else if (radius >= 1.8)
			starClass = 1; // B
		else if (radius >= 1.4)
			starClass = 2;// A
		else if (radius >= 1.15)
			starClass = 3; // F
		else if (radius >= 0.96)
			starClass = 4;// G
		else if (radius >= 0.7)
			starClass = 5;// K
		else
			starClass = 6;// M
		color = colors[starClass];
		classification = starClassifications[starClass];
		// System.out.println("Class " + classification + ": " + color.getRed() + "," +
		// color.getGreen() + "," + color.getBlue());
	}

	public static double calculateRadiusFromClass(int starClass) {
		double radius;
		if (starClass == 0)
			radius = GalaxySim.gen.nextDouble() * 10 + 6.6;
		else if (starClass == 1)
			radius = GalaxySim.gen.nextDouble() * 4.8 + 1.8;
		else if (starClass == 2)
			radius = GalaxySim.gen.nextDouble() * 0.4 + 1.4;
		else if (starClass == 3)
			radius = GalaxySim.gen.nextDouble() * 0.25 + 1.15;
		else if (starClass == 4)
			radius = GalaxySim.gen.nextDouble() * 0.19 + 0.96;
		else if (starClass == 5)
			radius = GalaxySim.gen.nextDouble() * 0.26 + 0.7;
		else
			radius = GalaxySim.gen.nextDouble() * 0.7 + 0.3;
		return radius;
	}

	public static double calculateMassFromClass(int starClass) {
		double mass;
		if (starClass == 0)
			mass = GalaxySim.gen.nextDouble() * 50 + 16; // O
		else if (starClass == 1)
			mass = GalaxySim.gen.nextDouble() * 13.9 + 2.1; // B
		else if (starClass == 2)
			mass = GalaxySim.gen.nextDouble() * 0.7 + 1.4; // A
		else if (starClass == 3)
			mass = GalaxySim.gen.nextDouble() * 0.36 + 1.04; // F
		else if (starClass == 4)
			mass = GalaxySim.gen.nextDouble() * 0.24 + 0.8; // G
		else if (starClass == 5)
			mass = GalaxySim.gen.nextDouble() * 0.35 + 0.45; // K
		else
			mass = GalaxySim.gen.nextDouble() * 0.37 + 0.08; // M
		return mass;
	}

	public static double calculateRadiusFromMass(double mass) {
		//return 1.08475 * Math.pow(mass, 0.64902) + 0.0414486;
		return 1.0348 * Math.pow(mass, 0.663168) + 0.0941639;
	}

	public static int getRandomClass() {
		double chance = GalaxySim.gen.nextDouble() * 99.88003;
		int starClass = -1;
		if (chance < 76.45)
			starClass = 6;
		else if (chance < 88.55)
			starClass = 5;
		else if (chance < 96.15)
			starClass = 4;
		else if (chance < 99.15)
			starClass = 3;
		else if (chance < 99.75)
			starClass = 2;
		else if (chance < 99.88)
			starClass = 1;
		else
			starClass = 0;
		return starClass;
	}

	private void generatePlanets() {
		int count = GalaxySim.gen.nextInt((int) (mass * 8) + 2);
		for (int i = 0; i < count; i++) {
			int scaleMod = GalaxySim.gen.nextInt(3) + 4;
			double dx = GalaxySim.gen.nextDouble() * maxSatelliteHeight + minSatelliteHeight;
			Planet p = new Planet(x + dx, y, width / scaleMod, height / scaleMod);
			addSatellite(p);
		}
		// System.out.println(count + " planets created.");
	}
	
	public String getInfo() {
		return "Class " + classification + ", " + super.getInfo() + ", Luminosity " + String.format("%.1f", luminosity);
	}

	protected void reassessStatus() {
		generateClassFromRadius(calculateRadiusFromMass(mass));
		calculateTemperature();
		calculateLuminosity();
		calculateMinMaxSatelliteHeight();
		checkIfOrbitIsStable();
	}
	@Override
	protected void calculateTemperature() {
		temperature = 8060.83*Math.pow(mass, 0.496634) - 1929.9;
	}

	protected void calculateLuminosity() {
		double surfaceArea = 4 * Math.PI * Math.pow(width/2, 2);
		luminosity = (temperature / surfaceArea) * (2 * Math.PI * Math.pow(width/2, 2));
	}

	protected void combineWith(Star other) {
		Star survivor;
		Star trash;
		if (other.mass > this.mass) {
			survivor = other;
			trash = this;
		} else {
			survivor = this;
			trash = other;
		}
		//System.out.println("Combining stars with masses " + survivor.mass + " and " + trash.mass);
		survivor.absorb(trash);
		Sector s = GalaxySim.getSectorByName(trash.currentSector);
		if (s != null)
			s.removeStar(trash);
		else
			System.out.println(trash.currentSector + " is an invalid Sector.");
		GalaxySim.stars--;

		hasCombined = true;
		other.hasCombined = true;
	}
	
	protected void absorb(CelestialBody body) {
		mass += body.mass;
		double r = Star.calculateRadiusFromMass(mass);
		setRadius(r);
		generateClassFromRadius(r);
		calculateTemperature();
		calculateLuminosity();
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
			currentSector = current.name;
		}
	}
	
	public BlackHole convertToBlackHole() {
		BlackHole bh = new BlackHole(x,y,mass);
		return bh;
	}
	
	public void customDraw(Graphics g) {
		if (starImage != null) {
			g.drawImage(starImage, (int) (drawX - (drawWidth / 4.45)), (int) (drawY - (drawHeight / 4.45)),
					(int) (drawWidth * 1.45), (int) (drawHeight * 1.45), null);
		}
		g.setColor(color);
		g.fillOval((int) drawX, (int) drawY, (int) drawWidth, (int) drawHeight);
		if (hasCombined) {
			g.setColor(Color.CYAN);
			//g.drawOval((int) (drawX - (int) (drawWidth / 2)), (int) (drawY - (int) (drawHeight / 2)),
			//		(int) (drawWidth * 2), (int) (drawHeight * 2));
		}
	}

	public void findVictimBodies() {
		for (Sector sector : GalaxySim.getSectorByName(currentSector).getSectorGroup()) {
			for (Star star : sector.stars) {
				if (star.mass < this.mass && star.getDistanceFrom(this) < maxSatelliteHeight+minSatelliteHeight && !victims.contains(star)) {
					victims.add(star);
				}
			}
		}
	}

	public void attractVictims() {
		for (int i = victims.size() - 1; i >= 0; i--) {
			Star victim = victims.get(i);
			double dx = x - victim.x;
			double dy = y - victim.y;
			double dist = victim.getDistanceFrom(this);
			double vmassp = victim.mass / (mass + victim.mass);
			double d2 = Math.pow(dist,2);
			victim.xVelocity += ((mass*dx) / d2)*(1-vmassp);
			victim.yVelocity += ((mass*dy) / d2)*(1-vmassp);
			xVelocity += ((victim.mass*-dx) / d2)*(vmassp);
			yVelocity += ((victim.mass*-dy) / d2)*(vmassp);
			
			if (dist < width / 3) {
				combineWith(victim);
				victims.remove(i);
			}
		}
	}

	public void customUpdate() {
		super.customUpdate();
		if (victims.size() > 0)
			attractVictims();
		migrate();
		
	}
}
