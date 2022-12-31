package synchronization;

public class SynchronizedSwimming {
	private static final Object swimmingPool = new Object();

	public static void main(String[] args) {
		Swimmer a = new Swimmer("John");
		Swimmer b = new Swimmer("Sally");
		a.start();
		b.start();
	}

	/*
	 * Refactor this method using a synchronized block to ensure a lock must be held
	 * on the swimmingPool object until the swimmer has finished their lap.
	 */
	private static void swimLap(Swimmer swimmer) throws InterruptedException {

		synchronized (swimmingPool) {

			System.out.println(swimmer.name + " started a lap!");
			Thread.sleep(2000);
			System.out.println(swimmer.name + " finished!");
		}
	}

	public static void takeTurn(Swimmer swimmer) {
		try {
			swimLap(swimmer);
			Thread.sleep(100);
		} catch (InterruptedException ignore) {
		}
	}
}