package ChatServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import ChatClient.ChatApp;

public class HubServer {
	private int port;
	private ServerSocket serverSocket;
	private HashMap<Integer, Socket> connections = new HashMap<Integer, Socket>();
	private HashMap<Integer, Thread> threads = new HashMap<Integer, Thread>();
	private HashMap<Integer, DataOutputStream> outs = new HashMap<Integer, DataOutputStream>();
	private HashMap<Integer, DataInputStream> ins = new HashMap<Integer, DataInputStream>();

	private String recievedMessage;

	private static int nextValidServerNum = 0;
	private ChatApp app;

	public HubServer(int port, ServerSocket sock, ChatApp app) {
		this.port = port;
		this.serverSocket = sock;
		this.app = app;
		recievedMessage = "";
	}

	public void start() {
		while (true) {
			try {
				serverSocket.setReuseAddress(true);
				System.out.println("Waiting for Socket.accept()...");
				getConnections().put(nextValidServerNum, serverSocket.accept());
				int serverNum = nextValidServerNum;
				nextValidServerNum++;
				Thread t = new Thread(() -> {
					startConnection(serverNum);
				});
				threads.put(serverNum, t);
				t.start();
			} catch (Exception e) {
				System.out.println("Connection lost.");
			}
		}
	}

	/**
	 * Starts a connection with a client and provides them a specific client ID number.
	 * @param clientNum
	 */
	private void startConnection(int clientNum) {
		try {
			System.out.println("Socket accepted!");

			outs.put(clientNum, new DataOutputStream(getConnections().get(clientNum).getOutputStream()));
			ins.put(clientNum, new DataInputStream(getConnections().get(clientNum).getInputStream()));

			outs.get(clientNum).flush();

			while (getConnections().get(clientNum).isConnected()) {
				try {
					recievedMessage = ins.get(clientNum).readUTF();

					app.addMessage(recievedMessage, clientNum);
				} catch (EOFException e) {
					disconnect(clientNum);
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Connection lost.");
			disconnect(clientNum);
		}
	}

	/**
	 * Disconnects from the given client and closes the connection to them.
	 * @param clientNum
	 */
	private void disconnect(int clientNum) {
		try {
			getConnections().get(clientNum).close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		getConnections().remove(clientNum);
		outs.remove(clientNum);
		ins.remove(clientNum);
		Thread t = threads.get(clientNum);
		threads.remove(clientNum);
		try {
			t.join(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends the given message string to all connected clients except for the source client.
	 * @param message
	 * @param source
	 */
	public void send(String message, int source) {
		for (int key : outs.keySet()) {
			try {
				if (outs.get(key) != null && key != source) {
					outs.get(key).writeUTF(message);
					outs.get(key).flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Gets the port the server is acting on
	 */
	public int getServerPort() {
		return port;
	}

	public HashMap<Integer, Socket> getConnections() {
		return connections;
	}

	public void setConnections(HashMap<Integer, Socket> connections) {
		this.connections = connections;
	}
}