package galaxysim;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
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
	public static String currentSector = "";

	JFrame frame = new JFrame();
	Timer timer;
	Timer currentSectorTimer;
	SimPanel panel = new SimPanel();
	
	public static void main(String[] args) {
		GalaxySim sim = new GalaxySim();
		sim.setup();
	}
	void setup() {
		frame.add(panel);
		frame.setVisible(true);
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT+39));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(panel);
		frame.addMouseListener(panel);
		frame.pack();
		timer = new Timer(1000 / 60, panel);
		currentSectorTimer = new Timer(1000/2, this);
		timer.start();
		currentSectorTimer.start();
	}
	public static void generateSector(int x, int y) {
		SectorPosition pos = new SectorPosition(x,y);
		Sector s = new Sector(pos);
		sectors.put(pos.toString(), s);
	}
	
	public static Sector getCurrentSector(double x, double y) {
		for(Sector s : sectors.values()) {
			if(x>s.position.x*Sector.WIDTH - Sector.WIDTH/2 && x<(s.position.x)*Sector.WIDTH + Sector.WIDTH/2 && y>s.position.y*Sector.HEIGHT - Sector.HEIGHT/2 && y<(s.position.y)*Sector.HEIGHT + Sector.HEIGHT/2) {
				return s;
			}
		}
		return null;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Sector current = getCurrentSector(Camera.mainCam.x, Camera.mainCam.y);
		if(current != null)	{
			if(!current.name.equals(currentSector)) {
				current.generateNeighbors();
				panel.currentSectorGroup = current.getSectorGroup();
			}
			currentSector = current.name;
		}
		else currentSector = "Unknown";
		frame.setTitle("GalaxySim: Currently in " + currentSector);
	}
}
