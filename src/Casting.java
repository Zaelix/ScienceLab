
public class Casting {
	public static void main(String[] args) {
		Casting c = new Dog();
		((Dog) c).stuff();
	}
}

class Dog extends Casting {
	void stuff() {
		
	}
}