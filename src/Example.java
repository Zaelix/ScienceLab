import java.applet.AudioClip;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JApplet;


public class Example {
	public static void main(String[] args) {
		//getVideoFromFile("butter.mp4");
		new Example().playEureka();
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
	public void playEureka() {
		try {
			AudioClip sound = JApplet.newAudioClip(getClass().getResource("sawing.wav"));
			sound.play();
			//Thread.sleep(3400);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static synchronized void playSound(final String url) {
		  new Thread(new Runnable() {
		  // The wrapper thread is unnecessary, unless it blocks on the
		  // Clip finishing; see comments.
		    public void run() {
		      try {
		        Clip clip = AudioSystem.getClip();
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(
		          new File("sawing.wav"));
		        clip.open(inputStream);
		        clip.start(); 
		      } catch (Exception e) {
		        System.err.println(e.getMessage());
		      }
		    }
		  }).start();
		}

}