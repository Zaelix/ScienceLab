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

	ClientGreeter(ChatApp app) {
		this.app = app;
	}

	public void start() {
		String ip = "localhost";
		int port = 80;
		try {
			setSock(new Socket(ip, port));
			System.out.println(getSock().isBound());
			System.out.println(getSock().isConnected());
			output = new DataOutputStream(getSock().getOutputStream());

			input = new DataInputStream(getSock().getInputStream());
			app.getConnectedLabel().setText("Connected!");

			while (getSock().isConnected()) {

				String in = input.readUTF();
				if (!in.equals("")) {
					app.addMessage(in, -1);
				}
			}
			getSock().close();
		} catch (IOException e) {
			retryLostConnection();
		}
	}

	public void retryLostConnection() {
		app.getConnectedLabel().setText("Server not found.");

		for (int i = 5; i >= 0; i--) {
			try {
				Thread.sleep(1000);
				app.getConnectedLabel().setText("Attempting to reconnect in " + i + " seconds...");

			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}

		app.restartClient();
	}

	public void send(String text) {
		try {
			output.writeUTF(text);
		} catch (IOException e) {
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