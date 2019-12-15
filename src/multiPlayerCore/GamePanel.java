package multiPlayerCore;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {
	Timer gameTimer;
	GameListener listener;
	public ClientServer clientServer;
	JButton hostButton;
	JButton clientButton;
	JLabel connStatus = new JLabel("Not connected.");

	int gameState = 0;

	GamePanel() {
		setup();
	}

	void setup() {
		clientServer = new ClientServer("localhost", 80, this);
		listener = new GameListener(this);
		hostButton = new JButton("Start Server");
		hostButton.addActionListener(listener);
		clientButton = new JButton("Start Client");
		clientButton.addActionListener(listener);
		this.add(hostButton);
		this.add(clientButton);
		this.add(connStatus);
		gameTimer = new Timer(1000/60, this);
		gameTimer.start();
		GameCore.pack();
	}

	public void convertToServerPanel() {
		this.remove(hostButton);
		this.remove(clientButton);
		this.add(new JButton("Disconnect"));
	}

	public void convertToClientPanel() {
		this.removeAll();
	}

	public void setConnectionText(String s) {
		connStatus.setText(s);
		//repaint();
		//GameCore.pack();
	}

	public void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, GameCore.WIDTH, GameCore.HEIGHT);
		if (clientServer.isClient && gameState == 1) {
			g.setColor(getForeground());
			g.fillRect(100, 100, 100, 100);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}

}
