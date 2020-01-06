package ChatClient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

/*
 * Using the Click_Chat example, write an application that allows a server computer to chat with a client computer.
 */

public class ChatAppClient implements ActionListener, KeyListener, MouseWheelListener {
	Timer timer;
	static String name = "client";
	static String[] randomNames = { "Randy", "James", "Banana", "Zeus", "Athena", "Romulus", "Remus", "Mars", "Apollo",
			"Julius", "Kirito", "Asuna", "Main", "Nessie", "Luther", "Kakarot", "Link", "Zelda", "Fox", "Mario",
			"Bowser", "Lucario", "Pikachu", "Squirtle", "Anakin", "Obi-Wan", "Yoda", "Baby Yoda", "Jar-Jar", "Aquaman",
			"Baymax", "Bigfoot", "Yeti", "Bond", "Han Solo", "Rocky", "Spock", "Picard", "Joker", "Batman", "Kermit",
			"Zorro", "Aragorn", "Gandalf", "Bilbo", "Frodo", "Isildur", "Pippin", "Gollum", "Saruman", "Sauron",
			"Shelob" };
	static JFrame frame;
	static ChatPanel panel;
	public static JLabel connectedLabel;
	static JLabel textView;
	static JTextField textInput;
	static JButton sender;
	static ClientGreeter client;
	static int font = 0;
	static String[] fonts = { "Verdana", "Garamond", "Cambria", "Courier", "Times" };
	static int fontSize = 2;
	static int totalLines = 0;
	static int startMessage = 0;
	static ArrayList<String> messages = new ArrayList<String>();

	static ArrayList<String> names = new ArrayList<String>();
	static ArrayList<Color> colors = new ArrayList<Color>();

	public static void main(String[] args) {
		ChatAppClient app = new ChatAppClient();
		app.makeFrame();
		app.start();
	}

	public void initializeColors() {
		colors.add(Color.GREEN);
		colors.add(Color.BLUE);
		// colors.add(Color.RED);
		colors.add(Color.YELLOW);
		colors.add(Color.CYAN);
		colors.add(Color.PINK);
		colors.add(Color.MAGENTA);
	}

	public static int getNameIndex(String name) {
		for (int i = 0; i < names.size(); i++) {
			if (names.get(i).contentEquals(name)) {
				return i;
			}
		}
		names.add(name);
		return names.size() - 1;
	}

	public void makeFrame() {
		timer = new Timer(1000 / 30, this);
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
			if (i < msgs.length) {
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

				label.setBackground(colors.get(getNameIndex(senderName) % colors.size()));

				panel.add(label);
			}
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
		name = randomNames[new Random().nextInt(randomNames.length)];
		names.add(name);
		initializeColors();
		client = new ClientGreeter();
		client.start();

		while (client.sock.isConnected()) {

		}

	}

	public static void restartClient() {
		System.out.println("Restarting Client...");
		client = new ClientGreeter();
		connectedLabel.setText("Attempting to Connect...");
		System.out.println("Client recreated, attempting connections...");
		client.start();
	}

	public static void addMessage(String s) {
		messages.add(s);
		System.out.println(s);
		startMessage++;
		rebuildFrame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(sender)) {
			client.send(name + ": " + textInput.getText());
			addMessage(name + ": " + textInput.getText());
			textInput.setText("");
		}
		panel.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 10) {
			client.send(name + ": " + textInput.getText());
			addMessage(name + ": " + textInput.getText());
			textInput.setText("");
		}
		if (e.getKeyCode() == 33) {
			ChatPanel.changeTheme();
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