package multiPlayerCore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.Timer;

public class GameListener implements ActionListener, KeyListener {
	Timer gameTimer;
	GamePanel gp;

	GameListener(GamePanel gp) {
		this.gp = gp;
		start();
	}
	
	public void start() {
		gameTimer = new Timer(1000 / 30, this);
		gameTimer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() instanceof JButton) {
			JButton b = (JButton) e.getSource();
			if (b == gp.hostButton) {
				gp.convertToServerPanel();
				gp.clientServer.startConnection(false);
			}

			if (b == gp.clientButton) {
				gp.convertToClientPanel();
				gp.clientServer.startConnection(true);
			}
		}
		//gp.repaint();
		//GameCore.pack();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
