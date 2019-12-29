package ChatServerPlus;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JLabel;

public class HubServer {
	private int port;

	private ServerSocket serverSocket;
	Socket connection;

	String recievedMessage;

	DataOutputStream out;
	DataInputStream in;

	private int serverNum;
	
	public HubServer(int port, int serverNum) {
		this.port = port;
		this.serverNum = serverNum;
		recievedMessage = "";
	}

	public void start(JLabel connectedLabel) {
		try {
			System.out.println("Creating ServerSocket...");
			serverSocket = new ServerSocket(port);
			System.out.println("ServerSocket created!");
			// connectedLabel.setText("Waiting for connection...");
			Thread thread1 = new Thread(() -> {
				for (int i = 0; true; i++) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (i % 3 == 0)
						connectedLabel.setText("Waiting for connection.");
					else if (i % 3 == 1)
						connectedLabel.setText("Waiting for connection..");
					else if (i % 3 == 2)
						connectedLabel.setText("Waiting for connection...");
				}
			});
			thread1.start();
			connection = serverSocket.accept();
			thread1.stop();
			connectedLabel.setText("Client Connected!");
			ChatServerPlus.addClient();

			out = new DataOutputStream(connection.getOutputStream());
			in = new DataInputStream(connection.getInputStream());

			out.flush();

			while (connection.isConnected()) {
				try {
					recievedMessage = in.readUTF();
					
					ChatServerPlus.addMessage(recievedMessage);
				} catch (EOFException e) {
					retryLostConnection(connectedLabel);
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Connection lost.");
			ChatServerPlus.removeClient();
			try {
				serverSocket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			retryLostConnection(connectedLabel);
		}
	}

	public void retryLostConnection(JLabel connectedLabel) {
		connectedLabel.setText("Connection lost.");
		try {
			serverSocket.close();
			Thread.sleep(1000);
		} catch (InterruptedException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ChatServerPlus.restartServer(port, serverNum);
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
	
	public int getServerNumber() {
		return serverNum;
	}
}