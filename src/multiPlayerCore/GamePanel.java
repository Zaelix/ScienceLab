package multiPlayerCore;

import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	GameListener listener;
	public ClientServer clientServer;
	JButton hostButton;
	JButton clientButton;
	JLabel connStatus = new JLabel("Not connected.");
	
	int gameState = 0;
	
	GamePanel(){
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
		GameCore.frame.pack();
	}
	
	public void setConnectionText(String s) {
		connStatus.setText(s);
		repaint();
		GameCore.frame.pack();
	}
	
	public void paintComponent(Graphics g) {
		if(gameState == 1) {
			g.fillRect(100, 100, 100, 100);
		}
	}

}
