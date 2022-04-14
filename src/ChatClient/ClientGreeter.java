package ChatClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientGreeter {
	private DataOutputStream output;
	private DataInputStream input;
	private Socket socket;
	private ChatApp app;

	ClientGreeter(ChatApp app) {
		this.app = app;
	}

	public void start() {
		String ip = "localhost";
		int port = 8080;
		try {
			setSocket(new Socket(ip, port));
			System.out.println(getSocket().isBound());
			System.out.println(getSocket().isConnected());
			output = new DataOutputStream(getSocket().getOutputStream());

			input = new DataInputStream(getSocket().getInputStream());
			app.getConnectedLabel().setText("Connected!");

			while (getSocket().isConnected()) {

				String in = input.readUTF();
				if (!in.equals("")) {
					app.addMessage(in, -1);
				}
			}
			getSocket().close();
		} catch (IOException e) {
			retryLostConnection();
		}
	}

	/**
	 * Begins the countdown to attempt a reconnect to the server. Initiates a 
	 * reconnection attempt at the end of the countdown using ChatApp.restartClient().
	 */
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

	/**
	 * Sends the text message through the currently active output connection UTF.
	 * @param text
	 */
	public void send(String text) {
		try {
			output.writeUTF(text);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket sock) {
		this.socket = sock;
	}
}