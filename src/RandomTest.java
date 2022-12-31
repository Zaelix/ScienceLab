import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class RandomTest extends JPanel {
	static int rolls = 1000000;
	JFrame frame = new JFrame();
	HashMap<Integer, Integer> numsMath;
	HashMap<Integer, Integer> numsUtil;

	public static void main(String[] args) {
		new RandomTest().start();
	}

	void start() {
		frame.setVisible(true);
		frame.setPreferredSize(new Dimension(500, 300));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.pack();

		System.out.println("Math.Random:");
		testMathRandom();
		System.out.println("java.util.Random:");
		testUtilRandom();
		repaint();
	}

	public void paintComponent(Graphics g) {
		if (numsMath != null && numsUtil != null) {
			int x = 10;
			for (Integer i : numsMath.keySet()) {
				int h = (int) (numsMath.get(i) / (rolls / 10.0) * 100);
				g.fillRect(x, 50 + (100 - h), 10, h);
				x += 11;
			}

			x = 130;
			for (Integer i : numsUtil.keySet()) {
				int h = (int) (numsMath.get(i) / (rolls / 10.0) * 100);
				g.fillRect(x, 50 + (100 - h), 10, h);
				x += 11;
			}
		}
	}

	void testMathRandom() {
		numsMath = new HashMap<Integer, Integer>();
		for (int i = 0; i < 10; i++) {
			numsMath.put(i, 0);
		}
		for (int i = 0; i < rolls; i++) {
			int a = (int) (Math.random() * 10);
			numsMath.put(a, numsMath.get(a) + 1);
		}
		printCounts(numsMath);
	}

	void testUtilRandom() {
		Random g = new Random();
		numsUtil = new HashMap<Integer, Integer>();
		for (int i = 0; i < 10; i++) {
			numsUtil.put(i, 0);
		}
		for (int i = 0; i < rolls; i++) {
			int a = g.nextInt(10);
			numsUtil.put(a, numsUtil.get(a) + 1);
		}
		printCounts(numsUtil);

	}

	void printCounts(HashMap<Integer, Integer> nums) {
		for (Integer i : nums.keySet()) {
			System.out.println(i + " rolled " + nums.get(i) + " times.");
		}
	}

}
