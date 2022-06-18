package galaxysim;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class GalaxySim implements ActionListener {
	public static HashMap<String, Sector> sectors = new HashMap<String, Sector>();

	public static final int WIDTH = 1600;
	public static final int HEIGHT = 900;
	public static Random gen = new Random(-6461756828592062191L);
	public static int stars;
	public static int planets;
	public static String currentSectorName = "";
	public static Sector currentSector;

	JFrame frame = new JFrame();
	Timer timer;
	Timer currentSectorTimer;
	Timer mergeSearchTimer;
	SimPanel panel = new SimPanel();

	public static void main(String[] args) {
		GalaxySim sim = new GalaxySim();
		sim.setup();
	}

	void setup() {
		frame.add(panel);
		frame.setVisible(true);
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT + 39));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(panel);
		frame.addMouseListener(panel);
		frame.pack();

		long seed = gen.nextLong();
		System.out.println("Seed: " + seed);
		gen = new Random(seed);

		timer = new Timer(1000 / 60, panel);
		currentSectorTimer = new Timer(1000 / 3, this);
		mergeSearchTimer = new Timer(1000, this);
		timer.start();
		currentSectorTimer.start();
		mergeSearchTimer.start();
	}

	public static void generateSector(int x, int y) {
		SectorPosition pos = new SectorPosition(x, y);
		Sector s = new Sector(pos);
		sectors.put(pos.toString(), s);
		s.regenerate();
	}

	public static Sector getCurrentSector(double x, double y) {
		for (Sector s : sectors.values()) {
			if (x > s.position.x * Sector.WIDTH - Sector.WIDTH / 2
					&& x < (s.position.x) * Sector.WIDTH + Sector.WIDTH / 2
					&& y > s.position.y * Sector.HEIGHT - Sector.HEIGHT / 2
					&& y < (s.position.y) * Sector.HEIGHT + Sector.HEIGHT / 2) {
				return s;
			}
		}
		return null;
	}

	public static Sector getSectorByName(String sectorName) {
		for (Sector s : sectors.values()) {
			if (s.name.equals(sectorName)) {
				return s;
			}
		}
		return null;
	}

	public static Sector getSectorByPosition(int x, int y) {
		return sectors.get("[" + x + "," + y + "]");
	}

	public static Sector getRandomSector() {
		List<Sector> valuesList = new ArrayList<Sector>(sectors.values());
		return valuesList.get(new Random().nextInt(valuesList.size()));
	}

	public static Sector getRandomSectorInSectorGroup() {
		List<Sector> group = currentSector.getSectorGroup();
		return group.get(gen.nextInt(group.size()));
	}

	public static Sector getRandomSectorInCurrentSectorNeighbors() {
		List<Sector> group = currentSector.getNeighboringSectors();
		return group.get(gen.nextInt(group.size()));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == currentSectorTimer) {
			currentSector = getCurrentSector(Camera.mainCam.x, Camera.mainCam.y);
			if (currentSector != null) {
				if (!currentSector.name.equals(currentSectorName)) {
					currentSector.generateNeighbors();
					panel.currentSectorGroup = currentSector.getSectorGroup(3);
				}
				currentSectorName = currentSector.name;
			} else
				currentSectorName = "Unknown";
			frame.setTitle("GalaxySim: Currently in " + currentSectorName);
		} else if (e.getSource() == mergeSearchTimer) {
			Sector randomSector = getRandomSector();
			if (randomSector != currentSector) {
				for (Star star : randomSector.stars) {
					if (star.hasCombined) {
						star.findVictimBodies();
					}
				}
			}
			Star selectedStar = currentSector.getRandomStar();
			if (selectedStar.hasCombined) {
				selectedStar.findVictimBodies();
			}
//			for (Star star : currentSector.stars) {
//				if (star.hasCombined) {
//					star.findVictimBodies();
//				}
//			}
			Sector neighbor = getRandomSectorInCurrentSectorNeighbors();
			if (neighbor != randomSector) {
				for (Star star : neighbor.stars) {
					if (star.hasCombined) {
						star.findVictimBodies();
					}
				}
			}
		}
	}
}
