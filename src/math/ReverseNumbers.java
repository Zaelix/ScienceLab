package math;

import java.math.BigInteger;

public class ReverseNumbers {

	static String current;
	static String reverse;
	static String work;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for (int i = 200; i < 1000; i++) {
			long c = getReverseCount(BigInteger.valueOf(i));
			System.out.println("For " + i + " count was " + c + ". " + work);
		}
	}

	public static long getReverseCount(BigInteger num) {
		long count = 0;
		BigInteger a = num.add(BigInteger.valueOf(0));
		BigInteger b = num.add(BigInteger.valueOf(0));
		String showWork = "";
		while (b != null) {
			b = getReverseNumber(a);
			if (isPalindrome()) {
				if (count == 0)
					showWork = a + " is already a palindrome  ";
				// System.out.print(showWork.substring(0,showWork.length()-2));
				work = showWork.substring(0, showWork.length() - 2);
				break;
			}
			BigInteger next = a.add(b);
			String step = a.toString() + "+" + b.toString() + "=" + next;
			showWork += step + ", ";
			// System.out.print(step + ", ");
			count++;
			a = next;
			if (count % 1000 == 0) {
				if (count == 1000)
					System.out.println("For " + num.toString() + "...");
				System.out.println("..." + count + " steps in...");
				showWork = "";
			}
		}
		return count;
	}

	public static BigInteger getReverseNumber(BigInteger num) {
		current = num.toString();
		reverse = "";
		for (int i = current.length() - 1; i >= 0; i--) {
			reverse += current.charAt(i);
		}
		BigInteger rev = new BigInteger(reverse);
		return rev;
	}

	public static boolean isPalindrome() {
		if (current.equals(reverse))
			return true;
		return false;
	}
}
