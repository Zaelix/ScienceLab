package ChatClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientGreeter {
	private DataOutputStream output;
	private DataInputStream input;
	private Socket sock;
	private ChatApp app;
	
	ClientGreeter(ChatApp app){
		this.app = app;
	}
	public void start() {

		//String ip = "192.168.7.217";
		String ip = "localhost";
		int port = 80;
		try {
			setSock(new Socket(ip, port));
			System.out.println(getSock().isBound());
			System.out.println(getSock().isConnected());
			output = new DataOutputStream(getSock().getOutputStream());

			input = new DataInputStream(getSock().getInputStream());
			app.connectedLabel.setText("Connected!");
			
			while (getSock().isConnected()) {

				String in = input.readUTF();
				if (!in.equals("")) {
					app.addMessage(in,-1);
				}
			}
			getSock().close();
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
	public Socket getSock() {
		return sock;
	}
	public void setSock(Socket sock) {
		this.sock = sock;
	}
}