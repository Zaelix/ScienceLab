package ChatClient;

import java.net.*;

import javax.swing.JLabel;

import ChatServer.ChatServer;

import java.io.*;

public class ClientGreeter {
	DataOutputStream output;
	DataInputStream input;
	String Input;
	public Socket sock;
	ChatApp app;
	
	ClientGreeter(ChatApp app){
		this.app = app;
	}
	public void start() {

		//String ip = "192.168.7.217";
		String ip = "localhost";
		int port = 80;
		try {
			sock = new Socket(ip, port);
			System.out.println(sock.isBound());
			System.out.println(sock.isConnected());
			output = new DataOutputStream(sock.getOutputStream());

			input = new DataInputStream(sock.getInputStream());
			app.connectedLabel.setText("Connected!");
			
			while (sock.isConnected()) {

				String in = input.readUTF();
				if (!in.equals("")) {
					Input = in;
					app.addMessage(in,-1);
				}
			}
			sock.close();
		} catch (IOException e) {
			//e.printStackTrace();
			retryLostConnection();
		}
	}
	
	public void retryLostConnection() {
		app.connectedLabel.setText("Server not found.");
		
		for(int i = 5; i >= 0; i--) {
			try {
				//sock.close();
				Thread.sleep(1000);
				app.connectedLabel.setText("Attempting to reconnect in " + i + " seconds...");
				
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		app.restartClient();
	}
	
	public void send(String text) {
		try {
			output.writeUTF(text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}