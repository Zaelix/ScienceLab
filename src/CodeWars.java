
public class CodeWars {

	public static void main(String[] args) {
		CodeWars cw = new CodeWars();
		long poorTime = cw.test(true);
		long averageTime = cw.test(false);
		
		

		System.out.println("Poor Took " + poorTime + " milliseconds.");
		System.out.println("Average Took " + averageTime + " milliseconds.");
		
	}
	
	long test(boolean poor) {
		long start = System.currentTimeMillis();
		for(int i = 9_999_000; i < 9_999_999; i++) {
			if(poor) printPrime_Poor(i);
			else printPrime_Average(i);
		}
		long end = System.currentTimeMillis();
		long time = end-start;
		return time;
	}
	String printPrime_Poor(int num) {
		if(primeOrNot_Poor(num)) return num + " is Prime.";
		else return num + " is NOT Prime.";
	}
	String printPrime_Average(int num) {
		if(primeOrNot_Average(num)) return num + " is Prime.";
		else return num + " is NOT Prime.";
	}
	
	boolean primeOrNot_Poor(int num) {
		if(num > 1) {
			for(int i = 2; i <= num; i++) {
				if(num%i==0) {
					return false;
				}
			}
			return true;
		}
		else {
			return false;
		}
	}

	boolean primeOrNot_Average(int num) {
		int half = num/2;
		if(num > 1) {
			if(num%2==0) {
				return false;
			}
			for(int i = 2; i <= half; i+=2) {
				if(num%i==0) {
					return false;
				}
			}
			return true;
		}
		else {
			return false;
		}
	}
	boolean primeOrNot_Good(int num) {
		if(num == 2) {
			return true;
		}
		if(num > 1) {
			if(num%2==0) {
				return false;
			}
			for(int i = 3; i <= Math.sqrt(num); i+=2) {
				if(num%i==0) {
					return false;
				}
			}
			return true;
		}
		else {
			return false;
		}
	}
}
