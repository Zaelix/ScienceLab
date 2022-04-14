
public class StaticTest {
	private int b;
	StaticTest(int a){
		b = a;
	}
	public static final void main(String[] args) {
		StaticTest t = new StaticTest(3);
		System.out.println(t.b);
	}
}
