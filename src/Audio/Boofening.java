package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Boofening extends JPanel implements ActionListener, KeyListener, MouseListener {
	String text;
	Picture image;
	Timer timer = new Timer(1000 / 60, this);
	JFrame window = new JFrame();
	String nowPlace = "start";
	ArrayList<Button> options = new ArrayList<Button>();
	ArrayList<GameObject> piece = new ArrayList<GameObject>();
	final static int WIDTH = 800;
	final static int HEIGHT = 600;
	Font speak = new Font("DialogInput",Font.BOLD ,11);
	static HashMap<String,Clip> clips = new HashMap<String,Clip>();
	static HashMap<String,AudioInputStream> streams = new HashMap<String,AudioInputStream>();
	public static void main(String[] sgra) {
		Boofening card = new Boofening();
		card.createWindow();
	}

	private void createWindow() {
		window.addKeyListener(this);
		window.addMouseListener(this);
		window.add(this);
		window.setVisible(true);
		window.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		loadAudio("cough.wav");
		loadAudio("scraping.wav");
		// speedStart = System.currentTimeMillis();
		timer.start();
		startStory();
	}

	private void startStory() {
		options.clear();
		piece.clear();
		nowPlace = "start";
		text = "The boof is offered. Do you accept?";
		image = new Picture(WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT, 1, "Boof Meister.jpg");
		playSoundAudio("cough.wav",-5);
		options.add(new Button(50, 275, 50, 50, "m&m.jpg", "Yay"));
		options.add(new Button(700, 275, 50, 50, "m&m.jpg", "Neigh"));
		options.add(new Button(403, 100, 50, 50, "m&m.jpg", "Yay"));
		options.add(new Button(403, 200, 50, 50, "m&m.jpg", "yayayayay"));
		options.add(new Button(403, 300, 50, 50, "m&m.jpg", "yayayayayayayayaya"));
		options.add(new Button(403, 403, 50, 50, "m&m.jpg", "yayayayayayyayayayayayaya"));
		options.add(new Button(403, 500, 50, 50, "m&m.jpg", "ZZyzx, California is the name of a real place that actually exists"));
	}

	void yesBoof() {
		options.clear();
		piece.clear();
		text = "The drug is offered. Do you accept?";
		playSoundAudio("scraping.wav",5);
		image = new Picture(WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT, 1, "window drug.png");
		options.add(new Button(50, 275, 50, 50, "m&m.jpg", "Yay"));
		options.add(new Button(700, 275, 50, 50, "m&m.jpg", "Neigh"));
		piece.add(new GameObject(325,-150,220,300,"pane.png"));
		piece.add(new GameObject(200,-50,150,150,"arm.png"));
		nowPlace = "Drug";
	}

	void noBoof() {
		options.clear();
		piece.clear();
		nowPlace = "Labor";
		text = "Home. Do you continue laboring?";
		image = new Picture(WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT, 1, "Bath.jpg");
		options.add(new Button(50, 275, 50, 50, "m&m.jpg", "No, Computer"));
		options.add(new Button(700, 275, 50, 50, "m&m.jpg", "Yes, Chores"));
	}
	
	void com() {
		options.clear();
		piece.clear();
		nowPlace = "Screen";
		text = "What shall you do on the computer?";
		image = new Picture(WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT, 1, "mod.png");
		options.add(new Button(50, 275, 50, 50, "m&m.jpg", "Argue on Twottwyt"));
		options.add(new Button(700, 275, 50, 50, "m&m.jpg", "Watch Better Call Paul Blart"));
	}
	
	void internet() {
		options.clear();
		piece.clear();
		nowPlace = "Twot";
		text = "It is time to sleep. Do you?";
		image = new Picture(WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT, 1, "internet.png");
		options.add(new Button(50, 275, 50, 50, "m&m.jpg", "Argue more"));
		options.add(new Button(700, 275, 50, 50, "m&m.jpg", "Resting rectangle respite"));
	}
	
	void arguement() {
		options.clear();
		piece.clear();
		nowPlace = "Bedtime";
		text = "It is time to sleep. You will.";
		image = new Picture(WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT, 1, "PC.png");
		options.add(new Button(50, 275, 50, 50, "m&m.jpg", "Okay..."));
		options.add(new Button(700, 275, 50, 50, "m&m.jpg", "*In Angry French Accent* Fine!"));
	}
	
	void chores() {
		options.clear();
		piece.clear();
		nowPlace = "productive";
		text = "How shall you work now?";
		image = new Picture(WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT, 1, "oldhallway.png");
		options.add(new Button(50, 275, 50, 50, "m&m.jpg", "Laundry"));
		options.add(new Button(700, 275, 50, 50, "m&m.jpg", "Dishes"));
	}
	
	void yesDrug() {
		options.clear();
		piece.clear();
		nowPlace = "Drink";
		text = "The drink is offered. Do you accept?";
		image = new Picture(WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT, 1, "mariooffering3.png",1);
		options.add(new Button(50, 275, 50, 50, "m&m.jpg", "Yay"));
		options.add(new Button(700, 275, 50, 50, "m&m.jpg", "Neigh"));
	}
	
	void noDrug() {
		options.clear();
		piece.clear();
		nowPlace = "Date 1a";
		text = "Well, you gotta do something at the club. Oh, look! Someone wants to dance with you.";
		image = new Picture(WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT, 1, "pepsi knee.jpg");
		options.add(new Button(50, 275, 50, 50, "m&m.jpg", "Yay"));
		options.add(new Button(700, 275, 50, 50, "m&m.jpg", "Neigh"));
	}
	
	void yesDrink() {
		options.clear();
		piece.clear();
		nowPlace = "Death";
		text = "Oh, wait. Those were sleeping meds! You're not supposed to drink alcohal while on those.";
		image = new Picture(WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT, 1, "death.jpg");
		options.add(new Button(50, 275, 50, 50, "m&m.jpg", "Yay"));
		options.add(new Button(700, 275, 50, 50, "m&m.jpg", "Neigh"));
	}
	
	void noDrink() {
		options.clear();
		piece.clear();
		nowPlace = "Date1b";
		text = "With you rejecting the bottle, a fellow at the club tries stricking up a convo.";
		image = new Picture(WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT, 1, "pepsi knee.jpg");
		options.add(new Button(50, 275, 50, 50, "m&m.jpg", "Yay"));
		options.add(new Button(700, 275, 50, 50, "m&m.jpg", "Neigh"));
	}

	void boofCheck(int clickX, int clickY) {
		if (options.get(0).wasClicked(clickX, clickY)) {
			yesBoof();
			stopSounds();
		}
		if (options.get(1).wasClicked(clickX, clickY)) {
			noBoof();
			stopSounds();
		}
		
	}
	
	void drugCheck(int clickX, int clickY) {
		if (options.get(0).wasClicked(clickX, clickY)) {
			yesDrug();
			stopSounds();
		}
		if (options.get(1).wasClicked(clickX, clickY)) {
			noDrug();
			stopSounds();
		}
		System.out.println("works");
	}

	void drinkCheck(int clickX, int clickY) {
		if (options.get(0).wasClicked(clickX, clickY)) {
			yesDrink();
			stopSounds();
		}
		if (options.get(1).wasClicked(clickX, clickY)) {
			noDrink();
			stopSounds();
		}
	}
	
	void laborCheck(int clickX, int clickY) {
		if (options.get(0).wasClicked(clickX, clickY)) {
			com();
			stopSounds();
		}
		if (options.get(1).wasClicked(clickX, clickY)) {
			//work();
			stopSounds();
		}
	}
	
	void screenCheck(int clickX, int clickY) {
		if (options.get(0).wasClicked(clickX, clickY)) {
			internet();
			stopSounds();
		}
		if (options.get(1).wasClicked(clickX, clickY)) {
			//television();
			stopSounds();
		}
	}
	
	void twotCheck(int clickX, int clickY) {
		if (options.get(0).wasClicked(clickX, clickY)) {
			arguement();
			stopSounds();
		}
		if (options.get(1).wasClicked(clickX, clickY)) {
			//sleep();
			stopSounds();
		}
	}
	
	void arguementCheck(int clickX, int clickY) {
		if (options.get(0).wasClicked(clickX, clickY)) {
			//tired();
			stopSounds();
		}
		if (options.get(1).wasClicked(clickX, clickY)) {
			//tired();
			stopSounds();
		}
	}
	
	public void paintComponent(Graphics gee) {
		draw(gee);
	}

	private void draw(Graphics gee) {
		if (image != null) {
			image.draw(gee);
		}
		for (int i = 0; i < options.size(); i++) {
			options.get(i).draw(gee);
		}
		for (int i = 0; i < piece.size(); i++) {
			piece.get(i).draw(gee);
		}
		gee.setColor(Color.WHITE);
		gee.fillRect(20, 0, WIDTH - 40, 80);
		gee.setColor(Color.BLACK);
		gee.setFont(speak);
		gee.drawString(text+"", 50, 20);
		if(nowPlace == "Drug") {
			if (piece.get(0).y < 200) {
				piece.get(0).y++;
			}
		}
	}

	private static AudioInputStream loadAudio(String fileName) {
		AudioInputStream audio;
		try {
			Clip something = AudioSystem.getClip();
			audio = AudioSystem.getAudioInputStream(new File(fileName));
			//clips.get(fileName).open(audio);
			clips.put(fileName,something);
			streams.put(fileName, audio);
			return audio;
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	static void setVolume(float volume, String fileName) {
		FloatControl ploc = (FloatControl) clips.get(fileName).getControl(FloatControl.Type.MASTER_GAIN);
		ploc.setValue(volume);
	}

	static void playSoundAudio(String fileName, int volume) {
		try {
			//clips.get(fileName).close();
			stopSounds();
			clips.get(fileName).open(streams.get(fileName));
			setVolume(volume,fileName);
			clips.get(fileName).start();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("played");
	}

	public static void resetSound(String fileName) {
		stopSound(fileName);
		loadAudio(fileName);
	}

	static void stopSound(String fileName) {
		clips.get(fileName).stop();
		clips.get(fileName).setMicrosecondPosition(0);
	}
	
	static void stopSounds() {
		for(String key: clips.keySet()) {
			stopSound(key);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (nowPlace.equals("start")) {
			boofCheck(e.getX(), e.getY());
		}
		else if (nowPlace.equals("Drug")) {
			drugCheck(e.getX(), e.getY());
		}
		else if (nowPlace.equals("Drink")) {
			drinkCheck(e.getX(), e.getY());
		}
		else if (nowPlace.equals("Labor")) {
			laborCheck(e.getX(), e.getY());
		}
		else if (nowPlace.equals("Screen")) {
			screenCheck(e.getX(), e.getY());
		}
		else if (nowPlace.equals("Twot")) {
			twotCheck(e.getX(), e.getY());
		}
		//else if (nowPlace.equals("Bedtime")) {
			//bedCheck(e.getX(), e.getY());
		//}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

}
