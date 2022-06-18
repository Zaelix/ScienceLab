package territorialio;

import java.util.ArrayList;

public class Territorial_IO_Calculator {
	static final int ticksPerCycle = 10;
	static int totalLand = Maps.WHITE_ARENA.totalLand;
	static int cycle = 0;
	static double interest = 6.77;
	static boolean debug = false;

	static ArrayList<Nation> nations = new ArrayList<Nation>();

	public static void main(String[] args) {
		//testOne(8, 0.50);
		testFull();
	}

	static void testOne(int tick, double percent) {
		Nation tester = new Nation(tick, percent);
		nations.add(tester);
		//System.out.println(getTime(0) + ", " + tester.getStatus());
		for (int i = 0; i < 10; i++) {
			cycle(tester);
		}
		//System.out.println("End of 10th Cycle. " + tester.getStatus() + " Tick=" + tick + ", Percent=" + percent);
	}
	
	static void testFull() {
		for (int tick = 0; tick < 10; tick++) {
			for (double p = 0.01; p <= 1; p+=0.01) {
				cycle = 0;
				interest = 6.77;
				testOne(tick, p);
			}
		}
		sortByLand();
		printTopFive();
	}
	
	static void sortByLand() {
		boolean isSorted = false;
		while(!isSorted) {
			isSorted = true;
			for(int i = nations.size()-1; i > 0; i--) {
				if(nations.get(i).land > nations.get(i-1).land) {
					Nation swap = nations.get(i);
					nations.set(i, nations.get(i-1));
					nations.set(i-1, swap);
					isSorted = false;
				}
			}
		}
	}
	
	static void printTopFive() {
		for(int i = 0; i < 5; i++) {
			Nation n = nations.get(i);
			System.out.println("End of 10th Cycle. " + n.getStatus() + " Tick=" + n.expandTick + ", Percent=" + n.percentSend);
		}
	}

	static void tick(Nation nation, int tick) {
		if (debug) {
			String outp = getTime(tick) + ", " + nation.tick(tick);
			System.out.println(outp);
		}else nation.tick(tick);
		interest = interest - 0.03166666 > 1 ? interest - 0.03166666 : 1;
	}

	static Nation cycle(Nation nation) {
		for (int i = 1; i < 11; i++) {
			tick(nation, i);
		}
		if (debug) {
			String outp = "End of Cycle " + cycle + ", " + nation.cycle();
			System.out.println(outp);
		}
		else nation.cycle();
		cycle++;
		return nation;
	}

	static String getTime(int tick) {
		return "C" + cycle + ":T" + tick;
	}

}
