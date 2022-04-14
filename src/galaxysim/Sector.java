package galaxysim;

import java.awt.Graphics;
import java.util.ArrayList;

public class Sector {
	public static final int WIDTH = 9000;
	public static final int HEIGHT = 9000;
	String name;
	ArrayList<Star> stars = new ArrayList<Star>();
	SectorPosition position;
	public Sector(SectorPosition position) {
		this.position = position;
		this.name = "Sector " + (position.x < 0 ? "N"+-position.x : "P"+position.x) + "-" + (position.y < 0 ? "N"+-position.y : "P"+position.y);
		regenerate();
	}
	public void regenerate() {
		stars.clear();
		int density = Math.min(Math.max(calculateDensityVariation() + GalaxySim.gen.nextInt(40) - 20, 5), 200);
		int failed = 0;
		for (int i = 0; i < density; i++) {
			if(!generateStar()) failed++;
		}
		System.out.println("Generating " + name + " failed on " + failed + "/" + density + " stars.");
	}
	
	public int calculateDensityVariation() {
		int average = 0;
		ArrayList<Sector> neighbors = getNeighboringSectors();
		for(Sector other : neighbors) {
			average += other.stars.size();
		}
		if(average == 0) return GalaxySim.gen.nextInt(195) + 5;
		return average/neighbors.size();
	}
	private boolean generateStar() {
		double x = (position.x*WIDTH)+(GalaxySim.gen.nextDouble() * WIDTH - WIDTH/2);
		double y = (position.y*HEIGHT)+(GalaxySim.gen.nextDouble() * HEIGHT - HEIGHT/2);
		return generateStar(x, y);
	}

	private boolean generateStar(double x, double y) {
		Star star = new Star(x, y);
		int attempts = 0;
		while(!checkIfStarIsViable(star) && attempts < 1000) {
			double newX = (position.x*WIDTH)+(GalaxySim.gen.nextDouble() * WIDTH - WIDTH/2);
			double newY = (position.y*HEIGHT)+(GalaxySim.gen.nextDouble() * HEIGHT - HEIGHT/2);
			star.x = newX;
			star.y = newY;
			attempts++;
		}
		if(attempts < 1000) {
			stars.add(star);
			return true;
		}
		else {
			//System.out.println("WARNING: Failed to place star.");
			GalaxySim.stars--;
			if(star.satellites != null) GalaxySim.planets-=star.satellites.size();
			return false;
		}
	}

	private boolean checkIfStarIsViable(Star star) {
		boolean isClear = true;
		for(Sector s : getSectorGroup()) {
			for (Star other : s.stars) {
				double minAllowedDistance = star.mass * 500 + other.mass * 500;
				if (star.getDistanceFrom(other) < minAllowedDistance)
					isClear = false;
			}
		}

		return isClear;
	}
	public ArrayList<Sector> getSectorGroup(){
		ArrayList<Sector> sectorGroup = getNeighboringSectors();
		sectorGroup.add(this);
		return sectorGroup;
	}
	public ArrayList<Sector> getNeighboringSectors(){
		ArrayList<Sector> neighbors = new ArrayList<Sector>();
		SectorPosition pos = new SectorPosition(position.x-1, position.y-1);
		if(GalaxySim.sectors.containsKey(pos.toString())) neighbors.add(GalaxySim.sectors.get(pos.toString()));
		pos.setPosition(position.x, position.y-1);
		if(GalaxySim.sectors.containsKey(pos.toString())) neighbors.add(GalaxySim.sectors.get(pos.toString()));
		pos.setPosition(position.x+1, position.y-1);
		if(GalaxySim.sectors.containsKey(pos.toString())) neighbors.add(GalaxySim.sectors.get(pos.toString()));
		pos.setPosition(position.x-1, position.y);
		if(GalaxySim.sectors.containsKey(pos.toString())) neighbors.add(GalaxySim.sectors.get(pos.toString()));
		pos.setPosition(position.x+1, position.y);
		if(GalaxySim.sectors.containsKey(pos.toString())) neighbors.add(GalaxySim.sectors.get(pos.toString()));
		pos.setPosition(position.x-1, position.y+1);
		if(GalaxySim.sectors.containsKey(pos.toString())) neighbors.add(GalaxySim.sectors.get(pos.toString()));
		pos.setPosition(position.x, position.y+1);
		if(GalaxySim.sectors.containsKey(pos.toString())) neighbors.add(GalaxySim.sectors.get(pos.toString()));
		pos.setPosition(position.x+1, position.y+1);
		if(GalaxySim.sectors.containsKey(pos.toString())) neighbors.add(GalaxySim.sectors.get(pos.toString()));
		return neighbors;
	}
	
	public void generateNeighbors() {
		SectorPosition pos = new SectorPosition(position.x-1, position.y-1);
		if(!GalaxySim.sectors.containsKey(pos.toString())) GalaxySim.generateSector(pos.x, pos.y);
		pos.setPosition(position.x, position.y-1);
		if(!GalaxySim.sectors.containsKey(pos.toString())) GalaxySim.generateSector(pos.x, pos.y);
		pos.setPosition(position.x+1, position.y-1);
		if(!GalaxySim.sectors.containsKey(pos.toString())) GalaxySim.generateSector(pos.x, pos.y);
		pos.setPosition(position.x-1, position.y);
		if(!GalaxySim.sectors.containsKey(pos.toString())) GalaxySim.generateSector(pos.x, pos.y);
		pos.setPosition(position.x+1, position.y);
		if(!GalaxySim.sectors.containsKey(pos.toString())) GalaxySim.generateSector(pos.x, pos.y);
		pos.setPosition(position.x-1, position.y+1);
		if(!GalaxySim.sectors.containsKey(pos.toString())) GalaxySim.generateSector(pos.x, pos.y);
		pos.setPosition(position.x, position.y+1);
		if(!GalaxySim.sectors.containsKey(pos.toString())) GalaxySim.generateSector(pos.x, pos.y);
		pos.setPosition(position.x+1, position.y+1);
		if(!GalaxySim.sectors.containsKey(pos.toString())) GalaxySim.generateSector(pos.x, pos.y);
	}
	public void draw(Graphics g) {
		for (CelestialBody s : stars) {
			s.draw(g);
		}
	}
	
	public void update() {
		for (CelestialBody s : stars) {
			s.update();
		}
	}
}
