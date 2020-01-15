package ChatClient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import ChatServer.HubServer;

/*
 * Using the Click_Chat example, write an application that allows a server computer to chat with a client computer.
 */

public class ChatApp implements ActionListener, KeyListener, MouseWheelListener {

	// CLIENT ONLY VARIABLES
	static String[] randomNames = { "Randy", "James", "Banana", "Zeus", "Athena", "Romulus", "Remus", "Mars", "Apollo",
			"Julius", "Kirito", "Asuna", "Main", "Nessie", "Luther", "Kakarot", "Link", "Zelda", "Fox", "Mario",
			"Bowser", "Lucario", "Pikachu", "Squirtle", "Anakin", "Obi-Wan", "Yoda", "Baby Yoda", "Jar-Jar", "Aquaman",
			"Baymax", "Bigfoot", "Yeti", "Bond", "Han Solo", "Rocky", "Spock", "Picard", "Joker", "Batman", "Kermit",
			"Zorro", "Aragorn", "Gandalf", "Bilbo", "Frodo", "Isildur", "Pippin", "Gollum", "Saruman", "Sauron",
			"Shelob" };

	ClientGreeter client;

	// SHARED CODE FOR CLIENT AND SERVER
	Timer timer;
	String name = "Server";
	JFrame frame;
	ChatPanel panel;
	JLabel textView;
	JTextField textInput;
	JButton sender;

	int clientCount = 0;
	HubServer server;

	JLabel connectedLabel;
	int font = 0;
	static String[] fonts = { "Verdana", "Garamond", "Cambria", "Courier", "Times" };
	int fontSize = 2;
	int totalLines = 0;
	int startMessage = 0;
	ArrayList<String> messages = new ArrayList<String>();

	ArrayList<String> names = new ArrayList<String>();
	ArrayList<Color> colors = new ArrayList<Color>();

	ServerSocket serverSocket;
	int connectionTimer;
	int connectionCooldown = 60;

	boolean isServer = false;

	private int maximumLines = 900;

	public static void main(String[] args) {
		ChatApp app = new ChatApp();
		app.makeFrame();
		app.start();
	}

	public void initializeColors() {
		colors.add(Color.GREEN);
		colors.add(Color.BLUE);
		colors.add(Color.YELLOW);
		colors.add(Color.CYAN);
		colors.add(Color.PINK);
		colors.add(Color.MAGENTA);
	}

	/**
	 * Gets the index of a given name in our names array and returns it. If the name doesn't exist in the array, it adds it and then returns its index.
	 * @param name
	 * @return
	 */
	public int getNameIndex(String name) {
		for (int i = 0; i < names.size(); i++) {
			if (names.get(i).contentEquals(name)) {
				return i;
			}
		}
		names.add(name);
		return names.size() - 1;
	}

	/**
	 * Asks the user whether they want to run as a client, or as a server. Returns true if they want to be a server.
	 * @return
	 */
	public boolean askClientOrServer() {
		int ans = JOptionPane.showOptionDialog(null, "Would you like to start a client, or server?", "Initialization",
				0, JOptionPane.INFORMATION_MESSAGE, null, new String[] { "Client", "Server" }, null);
		return ans % 2 == 1;
	}

	/**
	 * Does the initial setup of creating the frame and all necessary dependencies. Only meant to be called once.
	 */
	public void makeFrame() {
		timer = new Timer(1000 / 30, this);
		isServer = askClientOrServer();
		System.out.println(isServer);
		if (isServer) {
			try {
				serverSocket = new ServerSocket(80);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		frame = new JFrame();
		panel = new ChatPanel(0,0);
		JPanel textPanel = new JPanel();
		textPanel.setPreferredSize(new Dimension(500, 750));
		textPanel.setBackground(Color.BLACK);
		connectedLabel = new JLabel("Waiting for Connection");
		connectedLabel.setBackground(Color.white);
		connectedLabel.setOpaque(true);
		panel.add(connectedLabel);
		panel.add(textPanel);
		textInput = new JTextField();
		textInput.setPreferredSize(new Dimension(300, 40));
		sender = new JButton("Send");
		sender.addActionListener(this);
		panel.add(textInput);
		panel.add(sender);
		panel.add(createDirectionsLabel());
		frame.add(panel);
		frame.setPreferredSize(new Dimension(520, 1000));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		textInput.addKeyListener(this);
		frame.addMouseWheelListener(this);
		frame.pack();
	}

	/**
	 * Rebuilds the frame. Usable any time the frame contents need to change and be redrawn.
	 */
	public void rebuildFrame() {
		frame.remove(panel);
		panel = new ChatPanel(panel.getShifter(), panel.getTheme());
		panel.add(connectedLabel);
		panel.add(createMessagesPanel());
		panel.add(textInput);
		panel.add(sender);
		panel.add(createDirectionsLabel());
		frame.add(panel);
		textInput.requestFocus();
		frame.pack();
	}

	/**
	 * Creates the panel that contains all the message labels in it. Also creates the message labels from the array of message strings.
	 * @return
	 */
	public JPanel createMessagesPanel() {
		JPanel panel = new JPanel(null);
		String[] msgs = new String[messages.size()];
		messages.toArray(msgs);
		totalLines = 0;
		if (startMessage > msgs.length - 1) {
			startMessage = msgs.length - 1;
		}
		if (startMessage < 0) {
			startMessage = 0;
		}
		for (int i = startMessage; i >= 0; i--) {
			if (i < msgs.length) {
				String message = msgs[i];
				Object[] objs = splitIntoLines(message, 70);
				message = (String) objs[0];
				ChatMessage label = new ChatMessage("<html><pre><font face=\"" + fonts[font] + "\" size=\"" + fontSize
						+ "\" color=\"rgb(255,0,0)\">" + message + "</font></pre></html>");
				label.setLines((int) objs[1]);
				label.init();
				totalLines += label.pixelHeight + 5;
				label.setLocation(5, 750 - totalLines);

				String senderName = message.split(":")[0];

				label.setBackground(colors.get(getNameIndex(senderName) % colors.size()));

				panel.add(label);
			}
		}
		panel = trimMessageList(panel);
		panel.setPreferredSize(new Dimension(500, 750));
		panel.setBackground(Color.BLACK);
		return panel;
	}

	/**
	 * Draws the label at the bottom of the frame which says which hotkeys are used and what they do
	 * @return
	 */
	JLabel createDirectionsLabel() {
		JLabel label = new JLabel();
		label.setText("<html>Page Up: Change theme <br/>Page Down: Change font</html>");
		label.setPreferredSize(new Dimension(490, 100));
		return label;
	}

	/**
	 * Trims messages off the message list if there are more than the maximum message count.
	 * @param panel
	 * @return
	 */
	public JPanel trimMessageList(JPanel panel) {
		while (totalLines > maximumLines ) {
			ChatMessage message = (ChatMessage) panel.getComponent(0);
			totalLines -= message.pixelHeight + 5;
			messages.remove(0);
		}
		return panel;
	}

	/**
	 * Splits a message string into lines by adding an HTML line break tag into it at specific intervals
	 * @param message
	 * @param size
	 * @return
	 */
	public Object[] splitIntoLines(String message, int size) {
		ArrayList<String> strings = new ArrayList<String>();
		int lineCount = 0;
		int index = 0;
		while (index < message.length()) {
			strings.add(message.substring(index, Math.min(index + size, message.length())));
			index += size;
			lineCount++;
		}
		String fin = "";
		for (String str : strings) {
			fin += str + "<br/>";
		}
		return new Object[] { fin, lineCount };
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		startMessage += e.getWheelRotation();
		rebuildFrame();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	private void sendMessage() {
		String message = name + ": " + textInput.getText();
		addMessage(message, -1);
		textInput.setText("");
		if (!isServer) {
			client.send(message);
		}
	}

	// SHARED, BUT DIFFERENT FOR CLIENT AND SERVER
	public void start() {
		if (isServer) {
			names.add(name);
			initializeColors();
			System.out.println(getIP());
			startServer();
		} else {
			name = randomNames[new Random().nextInt(randomNames.length)];
			names.add(name);
			initializeColors();
			client = new ClientGreeter(this);
			timer.start();
			client.start();

			while (client.getSock().isConnected()) {

			}
		}
	}

	public void restartClient() {
		System.out.println("Restarting Client...");
		client = new ClientGreeter(this);
		connectedLabel.setText("Attempting to Connect...");
		System.out.println("Client recreated, attempting connections...");
		client.start();
	}

	public void addMessage(String s, int serverNum) {
		messages.add(s);

		if (isServer) {
			sendToClients(s, serverNum);
		} 
		System.out.println(s);
		startMessage++;
		rebuildFrame();
	}

	public void setClientCountLabel() {
		clientCount = server.getConnections().values().size();
		connectedLabel.setText(clientCount + " Clients Connected.");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(sender)) {
			sendMessage();
		}
		if (isServer) {
			connectionTimer++;
			setClientCountLabel();
		}
		panel.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// System.out.println(e.getKeyCode());
		if (e.getKeyCode() == 10) {
			sendMessage();
		}
		if (e.getKeyCode() == 33) {
			panel.changeTheme();
		}
		if (e.getKeyCode() == 34) {
			font++;
			if (font >= fonts.length) {
				font = 0;
			}
			System.out.println(fonts[font]);
			rebuildFrame();
		}
	}

	// SERVER ONLY
	public void startServer() {
		connectionTimer = 0;
		server = new HubServer(80, serverSocket, this);
		timer.start();
		server.start();
	}

	public void sendToClients(String message, int source) {
		server.send(message, source);
	}

	public String getIP() {
		InetAddress inetAddress;
		try {
			inetAddress = InetAddress.getLocalHost();
			System.out.println("Host Name:- " + inetAddress.getHostName());
			return "IP Address:- " + inetAddress.getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "IP Not Found";
	}

}