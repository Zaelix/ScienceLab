package Smileys;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Smiley extends JLabel {
	public static final String YELLOW = "yellow";
	public static final String RED = "red";
	public static final String GREEN = "green";
	public static final String BLUE = "blue";
	public static String color = YELLOW;
	
	Smiley(){
		super(loadIcon("Smileys/smiley_"+ color +".jpg"));
	}
	
	private static Icon loadIcon(String fileName) {
		URL imageURL = Smiley.class.getClassLoader().getResource(fileName);
		Icon icon = new ImageIcon(imageURL);
		return icon;
	}
	
	public static void setColor(String newColor) {
		color = newColor;
	}
}
