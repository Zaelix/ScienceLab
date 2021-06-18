package recursion_practice;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class RecursionPractice {
	public void test() {

	}
	// if div is bigger than num, you can't divide anymore
	public static int divideBy(int num, int div) {
		if(div == 0) {
			return 0;
		}
		if (div > num) {
			return 0;
		}
		return 1 + divideBy(num - div, div);
	}

	public static int power(int num, int pow) {
		if (pow == 0) {
			return 1;
		}
		return num * power(num, pow - 1);
	}
	
	public static int power(int pow) {
		return (int) Math.pow(2,pow);
	}

	@Test
	void testMultiplication() {
		power(5);
		power(21,5);
		assertEquals(3, divideBy(12, 4));
		assertEquals(5, divideBy(10, 2));
		assertEquals(2, divideBy(6, 3));
		assertEquals(13, divideBy(65, 5));
		assertEquals(1, divideBy(22, 22));
		assertEquals(0, divideBy(50, 100));
		assertEquals(0, divideBy(773, 0));
	}

	@Test
	void testPower() {
		assertEquals(9, power(3, 2));
		assertEquals(64, power(4, 3));
		assertEquals(32, power(2, 5));
		assertEquals(25, power(5, 2));
		assertEquals(10, power(10, 1));
	}
}
