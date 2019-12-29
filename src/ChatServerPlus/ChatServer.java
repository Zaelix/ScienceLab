package ChatServerPlus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * Using the Click_Chat example, write an application that allows a server computer to chat with a client computer.
 */

public class ChatServer implements ActionListener, KeyListener {

	JFrame frame;
	JPanel panel;
	static JLabel connectedLabel;
	static JLabel[] messages = new JLabel[25];
	JPanel[] messagePanels = new JPanel[25];
	JTextField messageBox;
	JButton send;

	String name = "Server";

	static Server server;

	public static void main(String[] args) {
		new ChatServer();
	}

	public ChatServer() {
		frame = new JFrame();
		panel = new JPanel();
		connectedLabel = new JLabel();
		messageBox = new JTextField();
		send = new JButton("Send");

		frame.add(panel);
		panel.add(connectedLabel);

		for (int i = 0; i < messages.length; i++) {
			messages[i] = new JLabel();
			messages[i].setPreferredSize(new Dimension(400, 20));
			panel.add(messages[i]);
		}

		panel.add(messageBox);
		panel.add(send);

		send.addActionListener(this);
		messageBox.addKeyListener(this);
		messageBox.setPreferredSize(new Dimension(300, 20));
		messageBox.setText(" ");

		// name = JOptionPane.showInputDialog("Enter a username: ");

		frame.setPreferredSize(new Dimension(460, 900));
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* ================================================= */

		server = new Server(80);
		connectedLabel.setText("Unconnected.");
		server.start(connectedLabel);

	}

	public static void restartServer() {
		System.out.println("Restarting server...");
		server = new Server(80);
		connectedLabel.setText("Unconnected.");
		System.out.println("Server recreated, attempting connections...");
		server.start(connectedLabel);
	}

	void sendMessage() {
		for (int i = 0; i < messages.length - 1; i++) {
			messages[i].setText(messages[i + 1].getText());
		}
		messages[messages.length - 1].setText(name + ":" + messageBox.getText());
		messages[messages.length - 1].setOpaque(true);
		messages[messages.length - 1].setBackground(new Color(202, 244, 255));
		// if(!server.connection.isConnected())messages[messages.length-1].setText(name
		// + ":" + messageBox.getText() + " (sent into the void)");

		server.send(name + ": " + messageBox.getText());
		messageBox.setText(" ");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		sendMessage();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 10)
			sendMessage();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}