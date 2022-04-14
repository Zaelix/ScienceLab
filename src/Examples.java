import java.util.ArrayList;
import java.util.Iterator;

public class Examples {
	int testCount = 10000;
	int testDepth = 1000;
	static int iterations = 1000;

	long[] timesNoReturn = new long[iterations];
	long[] timesReturn = new long[iterations];

	public static void main(String[] args) {
		Examples e = new Examples();

		for (int i = 0; i < iterations; i++) {
			e.testDoStuff(i);
			e.testDoOtherStuff(i);
			System.out.println("Iteration " + i + " Complete.");
		}
		e.printAverages();
	}
	
	void printAverages() {
		long sumNR = 0;
		for (int i = 0; i < timesNoReturn.length; i++) {
			sumNR += timesNoReturn[i];
		}
		double avgNR = sumNR / (double)timesNoReturn.length;
		
		long sumR = 0;
		for (int i = 0; i < timesReturn.length; i++) {
			sumR += timesReturn[i];
		}
		double avgR = sumR / (double)timesReturn.length;
		System.out.println("Time taken (no returns): " + avgNR);
		System.out.println("Time taken (returns): " + avgR);
	}

	void testDoStuff(int index) {
		long startTime = System.nanoTime();
		for (int i = 0; i < testCount; i++) {
			doStuff();
		}
		long endTime = System.nanoTime();
		timesNoReturn[index] = endTime - startTime;
	}

	void testDoOtherStuff(int index) {
		long startTime = System.nanoTime();
		for (int i = 0; i < testCount; i++) {
			doOtherStuff();
		}
		long endTime = System.nanoTime();
		timesReturn[index] = endTime - startTime;
	}

	void doStuff() {
		double f = 0;
		for (int i = 0; i < testDepth; i++) {
			double x = Math.sqrt(i);
			if (x > f) {
				f = x;
			}
		}
	}

	Examples doOtherStuff() {
		double f = 0;
		for (int i = 0; i < testDepth; i++) {
			double x = Math.sqrt(i);
			if (x > f) {
				f = x;
			}
		}
		return this;
	}
}
