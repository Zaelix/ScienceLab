import java.util.Iterator;

public class SwapExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a = {1,2,3,4,5,6};
		print(a);
		swap(a);
	}
	
	public static void swap(int[] array) {
		for (int i = 1; i < array.length; i+=2) {
			int temp = array[i];
			array[i] = array[i-1];
			array[i-1] = temp;
		}
		print(array);
	}

	public static void print(int[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]+",");
		}
	}
}
