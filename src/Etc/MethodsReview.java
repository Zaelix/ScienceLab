package Etc;

public class MethodsReview {
	public static void main(String[] args) {
		MethodsReview mr = new MethodsReview();
		mr.runAndOrSetup();
	}
	
	int add(int a, int b) {
		int sum = a + b;
		return sum;
	}
	
	void runAndOrSetup() {
		int a = add(6,3);
		System.out.println(a);
		int b = subtract(9,2);
		System.out.println(b);
		int c = multiply(a,b);
		System.out.println(c);
	}
	
	int subtract(int a, int b) {
		int sum = a - b;
		return sum;
	}
	
	int multiply(int a, int b) {
		int sum = a * b;
		return sum;
	}
}
