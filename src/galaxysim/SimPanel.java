package galaxysim;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class SimPanel extends JPanel implements KeyListener, ActionListener, MouseListener, MouseWheelListener {
	Camera camera;
	Ship currentShip;
	public ArrayList<Sector> currentSectorGroup = new ArrayList<Sector>();
	public ArrayList<double[]> bgStars = new ArrayList<double[]>();
	public ArrayList<Integer> bgStarsBrightness = new ArrayList<Integer>();

	public SimPanel() {
		camera = new Camera(GalaxySim.WIDTH / 2, GalaxySim.HEIGHT / 2);
		GalaxySim.generateSector(0, 0);
		System.out.println("Generated " + GalaxySim.stars + " stars and " + GalaxySim.planets + " planets.");
		currentShip = new Ship(camera.centerX,camera.centerY, 100,100);
		camera.currentShip = currentShip;
		
		for(int i = 0; i < 100; i++) {
			bgStars.add(new double[]{GalaxySim.gen.nextDouble()*GalaxySim.WIDTH,GalaxySim.gen.nextDouble()*GalaxySim.HEIGHT});
			bgStarsBrightness.add(GalaxySim.gen.nextInt(255));
		}
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
			currentShip.left = true;
		}
		if (keyCode == 68) { // D key
			camera.right = true;
			currentShip.right = true;
		}
		if (keyCode == 87) { // W key
			camera.up = true;
			currentShip.up = true;
		}
		if (keyCode == 83) { // S key
			camera.down = true;
			currentShip.down = true;
		}
		if(keyCode == 37) currentShip.angle--;
		if(keyCode == 39) currentShip.angle++;
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
					+ " planets  in "+ GalaxySim.sectors.size() + " sectors currently loaded.");
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
			currentShip.left = false;
		}
		if (keyCode == 68) { // D key
			camera.right = false;
			currentShip.right = false;
		}
		if (keyCode == 87) { // W key
			camera.up = false;
			currentShip.up = false;
		}
		if (keyCode == 83) { // S key
			camera.down = false;
			currentShip.down = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GalaxySim.WIDTH, GalaxySim.HEIGHT);
		drawBackgroundStarscape(g);
		for (Sector s : currentSectorGroup) {
			s.draw(g);
		}
		currentShip.draw(g);
		camera.drawPlayer(g);
	}

	private void drawBackgroundStarscape(Graphics g) {
		double vx = currentShip.xVelocity/10.0;
		double vy = currentShip.yVelocity/10.0;
		for(int i = 0; i < bgStars.size(); i++) {
			bgStars.get(i)[0] = (bgStars.get(i)[0] - vx);
			bgStars.get(i)[1] = (bgStars.get(i)[1] - vy);
			
			int b = bgStarsBrightness.get(i);
			g.setColor(new Color(b,b,b));
			g.drawRect((int)bgStars.get(i)[0], (int)bgStars.get(i)[1], 1, 1);
			int db = b+GalaxySim.gen.nextInt(6) - 3;
			if(db > 0 && db < 255) {
				b = db;
			}
			else {
				b = GalaxySim.gen.nextInt(255);
				bgStars.get(i)[0] = GalaxySim.gen.nextInt(GalaxySim.WIDTH);
				bgStars.get(i)[1] = GalaxySim.gen.nextInt(GalaxySim.HEIGHT);
			}
			bgStarsBrightness.set(i, b);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		currentShip.update();
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
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
			if(e.getWheelRotation() > 0)	camera.setZoom(camera.zoom += 0.01);
			if(e.getWheelRotation() < 0)	camera.setZoom(camera.zoom -= 0.01);
		}
	}

}
