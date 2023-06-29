package recursion_practice;

public class FindMinValue {
	public static void main(String[] args) {
		int arr[] = { 12, 1234, 45, 67, 1 };
		int n = arr.length;
		System.out.print("Minimum element of array: " + getMin(arr, 0, n) + "\n");
	}

	static int getMin(int arr[], int i, int n) {
		// If there is single element, return it.
		// Else return minimum of first element and
		// minimum of remaining array.
		return (n == 1) ? arr[i] : Math.min(arr[i], getMin(arr, i + 1, n - 1));
	}
	static int getMin2(int arr[], int i, int n) {
		if(n==1) return arr[i];
		else {
			return Math.min(arr[i], getMin2(arr, i+1, n-1));
		}
	}
}
