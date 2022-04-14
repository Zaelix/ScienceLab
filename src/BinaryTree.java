import java.util.ArrayList;

public class BinaryTree {
	int value;
	BinaryTree left;
	BinaryTree right;
	
	public void add(int num) {
		if(num < value) {
			if(left == null) {
				left = new BinaryTree();
				left.value = num;
			}
			else {
				left.add(num);
			}
		}
	}
}


class TestClass{
	public static void main(String[] args) {
		BinaryTree a = new BinaryTree();
		a.value = 5;

		a.add(3);
	
		
		ArrayList<Double> list = new ArrayList<Double>();
		
	}
}

