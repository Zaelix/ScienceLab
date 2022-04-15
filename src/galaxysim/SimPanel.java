package galaxysim;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class SimPanel extends JPanel implements KeyListener, ActionListener, MouseListener {
	Camera camera;
	public ArrayList<Sector> currentSectorGroup = new ArrayList<Sector>();

	public SimPanel() {
		camera = new Camera(GalaxySim.WIDTH / 2, GalaxySim.HEIGHT / 2);
		GalaxySim.generateSector(0, 0);
		System.out.println("Generated " + GalaxySim.stars + " stars and " + GalaxySim.planets + " planets.");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		// System.out.println(keyCode);
		if (keyCode == 45) { // - key
			camera.zoomOut = true;
		}
		if (keyCode == 61) { // + key
			camera.zoomIn = true;
		}
		if (keyCode == 65) { // A key
			camera.left = true;
		}
		if (keyCode == 68) { // D key
			camera.right = true;
		}
		if (keyCode == 87) { // W key
			camera.up = true;
		}
		if (keyCode == 83) { // S key
			camera.down = true;
		}
		if (keyCode == 32) {
			int stars = 0;
			int planets = 0;
			for (Sector sector : currentSectorGroup) {
				for (Star star : sector.stars) {
					if (star.satellites != null) {
						planets += star.satellites.size();
					}
					stars++;
				}
			}
			System.out.println(stars + "/" + GalaxySim.stars + " stars and " + planets + "/" + GalaxySim.planets
					+ " planets currently loaded.");
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == 45) { // - key
			camera.zoomOut = false;
		}
		if (keyCode == 61) { // + key
			camera.zoomIn = false;
		}
		if (keyCode == 65) { // A key
			camera.left = false;
		}
		if (keyCode == 68) { // D key
			camera.right = false;
		}
		if (keyCode == 87) { // W key
			camera.up = false;
		}
		if (keyCode == 83) { // S key
			camera.down = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GalaxySim.WIDTH, GalaxySim.HEIGHT);
		for (Sector s : currentSectorGroup) {
			s.draw(g);
		}
		camera.drawPlayer(g);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		camera.update();

		for (Sector s : currentSectorGroup) {
			s.update();
		}
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX()-8;
		int y = e.getY()-32;
		for (Sector sector : currentSectorGroup) {
			for (Star star : sector.stars) {
				if(star.wasClicked(x, y)) {
					System.out.println(star.getInfo());
				}
				if(star.satellites != null) {
					for(CelestialBody b : star.satellites) {
						if(b.wasClicked(x, y)) {
							System.out.println(b.getInfo());
						}
					}
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
