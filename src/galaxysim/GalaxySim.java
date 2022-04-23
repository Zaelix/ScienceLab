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
	public static Random gen = new Random();
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
		return sectors.get("["+x+","+y+"]");
	}
	
	public static Sector getRandomSector() {
		List<Sector> valuesList = new ArrayList<Sector>(sectors.values());
		return valuesList.get(new Random().nextInt(valuesList.size()));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == currentSectorTimer) {
			currentSector = getCurrentSector(Camera.mainCam.x, Camera.mainCam.y);
			if (currentSector != null) {
				if (!currentSector.name.equals(currentSectorName)) {
					currentSector.generateNeighbors();
					panel.currentSectorGroup = currentSector.getSectorGroup();
				}
				currentSectorName = currentSector.name;
			} else
				currentSectorName = "Unknown";
			frame.setTitle("GalaxySim: Currently in " + currentSectorName);
		}
		else if(e.getSource() == mergeSearchTimer) {
			Sector sector = getRandomSector();
			for(Star star : sector.stars) {
				if(star.hasCombined) {
					star.findVictimBodies();
				}
			}
		}
	}
}
