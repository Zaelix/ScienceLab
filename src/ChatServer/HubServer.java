package ChatServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class HubServer {
	private int port;
	public ServerSocket serverSocket;
	Socket connection;
	HashMap<Integer, Socket> connections = new HashMap<Integer, Socket>();
	HashMap<Integer, Thread> threads = new HashMap<Integer, Thread>();
	HashMap<Integer, DataOutputStream> outs = new HashMap<Integer, DataOutputStream>();
	HashMap<Integer, DataInputStream> ins = new HashMap<Integer, DataInputStream>();

	String recievedMessage;

	public static int nextValidServerNum = 0;

	public HubServer(int port, ServerSocket sock) {
		this.port = port;
		this.serverSocket = sock;
		recievedMessage = "";
	}

	public void start() {
		while (true) {
			try {
				serverSocket.setReuseAddress(true);
				System.out.println("Waiting for Socket.accept()...");
				connections.put(nextValidServerNum, serverSocket.accept());
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

	public void startConnection(int serverNum) {
		try {
			System.out.println("Socket accepted!");

			outs.put(serverNum, new DataOutputStream(connections.get(serverNum).getOutputStream()));
			ins.put(serverNum, new DataInputStream(connections.get(serverNum).getInputStream()));

			outs.get(serverNum).flush();

			while (connections.get(serverNum).isConnected()) {
				try {
					recievedMessage = ins.get(serverNum).readUTF();

					ChatServer.addMessage(recievedMessage, serverNum);
				} catch (EOFException e) {
					disconnect(serverNum);
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Connection lost.");
			disconnect(serverNum);
		}
	}

	public void disconnect(int serverNum) {
		try {
			connections.get(serverNum).close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connections.remove(serverNum);
		outs.remove(serverNum);
		ins.remove(serverNum);
		Thread t = threads.get(serverNum);
		threads.remove(serverNum);
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void send(String s, int source) {
		for (int key : outs.keySet()) {
			try {
				if (outs.get(key) != null && key != source) {
					outs.get(key).writeUTF(s);
					outs.get(key).flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public int getServerPort() {
		return port;
	}
}