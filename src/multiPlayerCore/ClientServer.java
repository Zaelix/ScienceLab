package multiPlayerCore;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ClientServer {
	private int port;
	private String ip;
	private ServerSocket server;
	private Socket connection;
	String recievedMessage;
	boolean isClient;

	DataOutputStream out;
	DataInputStream in;

	ArrayList<String> commands = new ArrayList<String>();
	GamePanel gp;

	public ClientServer(String ip, int port, GamePanel gp) {
		this.ip = ip;
		this.port = port;
		this.gp = gp;
	}

	public void startConnection(boolean isClient) {
		this.isClient = isClient;
		try {
			if (isClient) {
				System.out.println("Seeking host connection...");
				gp.setConnectionText("Seeking host connection...");
				GameCore.frame.pack();
				gp.repaint();
				connection = new Socket(ip, port);
			} else {
				gp.setConnectionText("Awaiting client connection...");
				server = new ServerSocket(port);
				System.out.println("Awaiting client connection...");
				connection = server.accept();
				gp.setConnectionText("Connected!");
			}
			System.out.println("Getting streams...");
			out = new DataOutputStream(connection.getOutputStream());
			in = new DataInputStream(connection.getInputStream());
			out.flush();
			gp.gameState = 1;
			while (connection.isConnected()) {
				System.out.println("Connected!");
				String input = in.readUTF();
				if (!input.equals("")) {
					commands.add(input);
				}
			}
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public void send(String s) {
		try {
			if (out != null) {
				out.writeUTF(s);
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
