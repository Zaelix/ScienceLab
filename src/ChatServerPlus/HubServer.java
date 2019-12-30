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
	int status = 0;
	public ServerSocket serverSocket;
	Socket connection;

	String recievedMessage;

	DataOutputStream out;
	DataInputStream in;

	private int serverNum;
	
	public HubServer(int port, int serverNum, ServerSocket sock) {
		this.port = port;
		this.serverNum = serverNum;
		this.serverSocket = sock;
		status = 1;
		recievedMessage = "";
	}

	public void start() {
		try {
			connection = serverSocket.accept();
			status = 2;

			out = new DataOutputStream(connection.getOutputStream());
			in = new DataInputStream(connection.getInputStream());

			out.flush();

			while (connection.isConnected()) {
				try {
					recievedMessage = in.readUTF();
					
					ChatServerPlus.addMessage(recievedMessage, serverNum);
				} catch (EOFException e) {
					retryLostConnection();
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Connection lost.");
			try {
				serverSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			retryLostConnection();
		}
	}

	public void retryLostConnection() {
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
	
	public int getServerPort() {
		return port;
	}
}