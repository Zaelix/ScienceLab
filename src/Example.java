import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JOptionPane;

public class Example {
	public static void main(String[] args) {
		getVideoFromFile("butter.mp4");
	}
	
	static void getVideoFromFile(String file) {
		try {
			FileInputStream fin = new FileInputStream("src/" + file);
			
			byte[] b = new byte[(int) fin.available()];
			System.out.println("Size: "+fin.read(b) + " bytes");

			File nf = new File("video.mp4");
			FileOutputStream fw = new FileOutputStream(nf);
			fw.write(b);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}