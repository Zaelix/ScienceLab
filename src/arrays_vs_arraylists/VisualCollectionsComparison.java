package arrays_vs_arraylists;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.Timer;

public class VisualCollectionsComparison implements ActionListener {
	Timer timer;
	JFrame frame = new JFrame();
	ArrayList<String> list = new ArrayList<String>();
	java.util.ArrayList<String> vanillaList = new java.util.ArrayList<String>();
	String[] array = new String[5];
	int[] ints = new int[5];

	public static void main(String[] args) {
		VisualCollectionsComparison vcc = new VisualCollectionsComparison();
		vcc.setup();
	}

	void setup() {
		timer = new Timer(1000 / 60, this);
		timer.start();
		frame.setVisible(true);
		frame.setTitle("LEAGUE ArrayList Visualizer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(list.displayPanel);
		frame.pack();

		list.add("Hi");
		list.add("Moped");
		list.add("Canoe");
		list.add("Taco");
		list.add("John");
		list.add(2, "Spain");
		list.set(1, "Code");
		list.remove(3);
		list.add("Jack");
		list.size();
		list.contains("Coal");
		list.get(3);
		list.clear();
		
		array[0] = "hi";
		
//		vanillaList.add("Hi");
//		vanillaList.add("Moped");
//		vanillaList.add("Canoe");
//		vanillaList.add("Taco");
//		vanillaList.add("John");
//		vanillaList.add(2, "Spain");
//		vanillaList.set(1, "Code");
//		vanillaList.remove(3);
//		vanillaList.add("Jack");
//		vanillaList.size();
//		vanillaList.contains("Coal");
//		vanillaList.clear();
//		vanillaList.get(3);
//		vanillaList.indexOf("Jack");
//		vanillaList.remove("Jack");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		list.draw();
	}
}
