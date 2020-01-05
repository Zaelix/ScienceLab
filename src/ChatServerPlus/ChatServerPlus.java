package ChatServerPlus;

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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import ChatClient.ChatMessage;
import ChatClient.ChatPanel;

/*
 * Using the Click_Chat example, write an application that allows a server computer to chat with a client computer.
 */

public class ChatServerPlus implements ActionListener, KeyListener, MouseWheelListener {
	Timer timer;
	static String name = "Server";
	static JFrame frame;
	static ChatPanel panel;
	static JLabel textView;
	static JTextField textInput;
	static JButton sender;

	static int clientCount = 0;
	static ArrayList<HubServer> servers = new ArrayList<HubServer>();
	static ArrayList<Thread> threads = new ArrayList<Thread>();

	static JLabel connectedLabel;
	//static String font = "verdana";
	static int font = 0;
	static String[] fonts = {"Verdana", "Garamond", "Cambria", "Courier", "Times"};
	static int fontSize = 2;
	static int totalLines = 0;
	static int startMessage = 0;
	static ArrayList<String> messages = new ArrayList<String>();
	
	static ArrayList<String> names = new ArrayList<String>();
	static ArrayList<Color> colors = new ArrayList<Color>();

	static ServerSocket serverSocket;
	int connectionTimer;
	int connectionCooldown = 60;

	public static void main(String[] args) {
		ChatServerPlus app = new ChatServerPlus();
		app.makeFrame();
		app.start();
	}

	public void initializeColors() {
		colors.add(Color.GREEN);
		colors.add(Color.BLUE);
		colors.add(Color.RED);
		colors.add(Color.YELLOW);
		colors.add(Color.CYAN);
		colors.add(Color.PINK);
		colors.add(Color.MAGENTA);
	}
	
	public static int getNameIndex(String name) {
		for(int i = 0; i < names.size(); i++) {
			if(names.get(i).contentEquals(name)) {
				System.out.println(name + " at index " + i);
				return i;
			}
		}
		names.add(name);
		System.out.println("Added " + name);
		System.out.println(name + " at index " + names.size());
		return names.size()-1;
	}
	
	public void makeFrame() {
		timer = new Timer(1000 / 30, this);
		try {
			serverSocket = new ServerSocket(80);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame = new JFrame();
		panel = new ChatPanel();
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
		names.add(name);
		initializeColors();
		timer.start();
	}

	static public void rebuildFrame() {
		frame.remove(panel);
		panel = new ChatPanel();
		panel.add(connectedLabel);
		panel.add(createMessagesPanel());
		panel.add(textInput);
		panel.add(sender);
		panel.add(createDirectionsLabel());
		frame.add(panel);
		textInput.requestFocus();
		frame.pack();
	}

	public static JPanel createMessagesPanel() {
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
			String s = msgs[i];
			Object[] objs = splitIntoLines(s, 70);
			s = (String) objs[0];
			ChatMessage label = new ChatMessage("<html><pre><font face=\"" + fonts[font] + "\" size=\"" + fontSize
					+ "\" color=\"rgb(255,0,0)\">" + s + "</font></pre></html>");
			label.setLines((int) objs[1]);
			label.init();
			totalLines += label.pixelHeight + 5;
			label.setLocation(5, 750 - totalLines);
			
			String senderName = s.split(":")[0];
			
			label.setBackground(colors.get(getNameIndex(senderName)));
			
			panel.add(label);
		}
		panel = trimMessageList(panel);
		panel.setPreferredSize(new Dimension(500, 750));
		panel.setBackground(Color.BLACK);
		return panel;
	}

	static JLabel createDirectionsLabel() {
		JLabel label = new JLabel();
		label.setText("<html>Page Up: Change theme <br/>Page Down: Change font</html>");
		label.setPreferredSize(new Dimension(490, 100));
		return label;
	}
	
	public static JPanel trimMessageList(JPanel panel) {
		while (totalLines > 9000) {
			ChatMessage message = (ChatMessage) panel.getComponent(0);
			totalLines -= message.pixelHeight + 5;
			messages.remove(0);
		}
		return panel;
	}

	public static Object[] splitIntoLines(String s, int size) {
		ArrayList<String> strings = new ArrayList<String>();
		int lineCount = 0;
		int index = 0;
		while (index < s.length()) {
			strings.add(s.substring(index, Math.min(index + size, s.length())));
			index += size;
			lineCount++;
		}
		String fin = "";
		for (String str : strings) {
			fin += str + "<br/>";
		}
		return new Object[] { fin, lineCount };
	}

	public void start() {
		// name = JOptionPane.showInputDialog("Pick a username!");
		// String ip = JOptionPane.showInputDialog("Enter the IP Address");
		// int port = Integer.parseInt(JOptionPane.showInputDialog("Enter the port
		// number"));
		System.out.println(getIP());
		startServer();
	}

	public void startServer() {
		connectionTimer = 0;
		servers.add(new HubServer(80 + servers.size(), servers.size(), serverSocket));
		System.out.println("Servers: " + servers.size());
		// servers.get(servers.size() - 1).start();
		Thread thread1 = new Thread(() -> {
			servers.get(servers.size() - 1).start();
		});
		// threads.add(thread1);
		thread1.start();
	}

	public static void restartServer(int port, int serverNum) {
		System.out.println("Restarting server...");
		servers.set(serverNum, new HubServer(port, serverNum, serverSocket));
		connectedLabel.setText(clientCount + " Clients Connected.");
		System.out.println("Server recreated, attempting connections...");
		Thread thread1 = new Thread(() -> {
			servers.get(serverNum).start();
		});
		thread1.start();
	}

	ArrayList<Integer> getOpenPorts() {
		ArrayList<Integer> ports = new ArrayList<Integer>();
		int clients = 0;
		for (HubServer s : servers) {
			if (s.status == 1) {
				ports.add(s.getServerPort());
			}
			if (s.status == 2) {
				clients++;
			}
		}
		clientCount = clients;
		connectedLabel.setText(clientCount + " Clients Connected.");
		return ports;
	}

	public static void addMessage(String s, int serverNum) {
		messages.add(s);

		sendToClients(s, serverNum);
		System.out.println(s);
		startMessage++;
		rebuildFrame();
	}

	private void sendMessage() {
		addMessage(name + ": " + textInput.getText(), -1);
		textInput.setText("");
	}

	public static void sendToClients(String message, int source) {
		for (HubServer s : servers) {
			if (s.getServerNumber() != source) {
				s.send(message);
			}
		}
	}

	public static void addClient() {
		clientCount++;
	}

	public static void removeClient() {
		clientCount--;
	}

	public static String getIP() {
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(sender)) {
			sendMessage();
		}

		connectionTimer++;

		ArrayList<Integer> ports = getOpenPorts();
		if (connectionTimer > connectionCooldown && ports.size() == 0) {
			startServer();
		}
		panel.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(e.getKeyCode());
		if (e.getKeyCode() == 10) {
			sendMessage();
		}
		if (e.getKeyCode() == 33) {
			ChatPanel.changeTheme();
		}
		if (e.getKeyCode() == 34) {
			font++;
			if(font >= fonts.length) {
				font = 0;
			}
			System.out.println(fonts[font]);
			rebuildFrame();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		System.out.println("Mouse Scrolled! Start Message is now: " + startMessage);
		startMessage += e.getWheelRotation();
		rebuildFrame();
	}
}