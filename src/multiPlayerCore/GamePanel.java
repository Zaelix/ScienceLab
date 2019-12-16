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

	ObjectManager manager;

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
		gameTimer = new Timer(1000 / 60, this);
		gameTimer.start();
		GameCore.pack();
	}

	public void convertToServerPanel() {
		this.remove(hostButton);
		this.remove(clientButton);
		this.add(new JButton("Disconnect"));
		manager = new ObjectManager(this, true);
		listener.setManager(manager);
	}

	public void convertToClientPanel() {
		this.removeAll();
		manager = new ObjectManager(this, false);
		listener.setManager(manager);
	}

	public void setConnectionText(String s) {
		connStatus.setText(s);
		// repaint();
		// GameCore.pack();
	}

	public void encryptMessage(String command) {
		clientServer.send(command);
		decryptCommand(command);
	}

	public void decryptCommand(String command) {
		String[] parts = command.split(" ");
		if (parts[0].equals("p1")) {
			if (parts[1].equals("up")) {
				
			}
		}
	}

	public void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, GameCore.WIDTH, GameCore.HEIGHT);
		if (gameState == 1) {
			manager.draw(g);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		manager.update();
		repaint();
	}

}
