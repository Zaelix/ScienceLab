package multiPlayerCore;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;

public class ClientServer {
	private int port;
	private String ip;
	private ServerSocket server;
	private Socket connection;
	String recievedMessage;
	boolean isClient;
	int playerNum;

	DataOutputStream out;
	DataInputStream in;

	LinkedList<String> commands = new LinkedList<String>();
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
				playerNum = 2;
				System.out.println("Seeking host connection...");
				gp.setConnectionText("Seeking host connection...");
				GameCore.frame.pack();
				gp.repaint();
				connection = new Socket(ip, port);
			} else {
				playerNum = 1;
				gp.setConnectionText("Awaiting client connection...");
				server = new ServerSocket(port);
				System.out.println("Awaiting client connection...");
				connection = server.accept();
				gp.setConnectionText("Connection from Client!");
			}
			System.out.println("Getting streams...");
			out = new DataOutputStream(connection.getOutputStream());
			in = new DataInputStream(connection.getInputStream());
			// out.flush();
			gp.gameState = 1;
			startConnectionLoopThread();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startConnectionLoopThread() {
		Thread thread1 = new Thread(() -> {
			while (connection.isConnected()) {
				if (isClient) {
					//System.out.println("Client Connected!");
					//send("Client checking in!");
				} else {
					//System.out.println("Server Connected!");
				}
				try {
					String input = in.readUTF();
					if (!input.equals("")) {
						commands.add(input);
					}
				} catch (IOException e) {
					gp.gameState = 2;
					gp.setConnectionText("Connection lost.");
				}
			}
			try {
				gp.gameState = 2;
				connection.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		thread1.start();
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
	
	public String getNextCommand() {
		if(commands.size() > 0) {
			return commands.pop();
		}
		else return "";
	}
}
