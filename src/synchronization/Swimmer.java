package synchronization;

public class Swimmer extends Thread {
	public final String name;

	public Swimmer(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			SynchronizedSwimming.takeTurn(this);
		}
	}
}