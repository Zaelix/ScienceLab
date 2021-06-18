package Audio;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MidiKeyboard implements KeyListener{
	Synthesizer synth;
	public MidiChannel[] channels;
	JLabel noteLabel = new JLabel();
	int piano = 0;
	int drums = 9;
	String[] notes = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
	ArrayList<Integer> keyboardRowQP = new ArrayList<Integer>(Arrays.asList(81,87,69,82,84,89,85,73,79,80,91,93));
	ArrayList<Integer> rowAL = new ArrayList<Integer>(Arrays.asList(65,83,68,70,71,72,74,75,76,59,222));
	ArrayList<Integer> rowZM = new ArrayList<Integer>(Arrays.asList(90,88,67,86,66,78,77,44,46,47));
	int lastKey = 0;

	public static void main(String[] args) {
		MidiKeyboard keyboard = new MidiKeyboard();
		keyboard.start();
	}

	private void start() {
		initialize();
		noteLabel.setFont(new Font("Arial", Font.BOLD, 40));
		noteLabel.setHorizontalAlignment(JLabel.CENTER);
		JFrame frame = new JFrame();
		// Set the default close operation of your JFrame to JFrame.EXIT_ON_CLOSE
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Add a key listener to your JFrame
		frame.addKeyListener(this);
		// Set your frame to be visible
		frame.setVisible(true);
		frame.add(noteLabel);
		frame.setPreferredSize(new Dimension(500,500));
		frame.pack();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == lastKey) return;
		lastKey = keyCode;
		System.out.println(keyCode);
		
		// if rowQP contains keyCode...
		if(keyboardRowQP.contains(keyCode)) {
			// call playNote() with keyCode, rowQP, and piano as the arguments
			playNote(keyCode, keyboardRowQP, piano);
		}
		// if rowAL contains keyCode...
		if(rowAL.contains(keyCode)) {
			// call playNote() with keyCode, rowAL, and piano as the arguments
			playNote(keyCode, rowAL, piano);
		}
		// if rowQP contains keyCode...
		if(rowZM.contains(keyCode)) {
			// call playNote() with keyCode, rowZM, and drums as the arguments
			playNote(keyCode, rowZM, drums);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		lastKey = 0;
		
		// if rowQP contains keyCode...
		if(keyboardRowQP.contains(keyCode)) {
			// call stopNote() with keyCode, rowAL, and piano as the arguments
			stopNote(keyCode, keyboardRowQP, piano);
		}
		// if rowAL contains keyCode...
		if(rowAL.contains(keyCode)) {
			// call stopNote() with keyCode, rowAL, and piano as the arguments
			stopNote(keyCode, rowAL, piano);
		}
		// if rowQP contains keyCode...
		if(rowZM.contains(keyCode)) {
			// call stopNote() with keyCode, rowZM, and drums as the arguments
			stopNote(keyCode, rowZM, drums);
		}
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}
	

	public void playNote(int keyCode, ArrayList<Integer> row, int instrument) {
		final int note;
		if(row == rowAL) {
			note = 49+row.indexOf(keyCode);
		}
		else {
			note = 60+row.indexOf(keyCode);
		}
		System.out.println(note);
		if(row != rowZM) noteLabel.setText(getNoteName(note));
		new Thread(new Runnable() {
			public void run() {
				channels[instrument].noteOn(note, 120);
			}
		}).start();
	}
	
	public void stopNote(int keyCode, ArrayList<Integer> row, int instrument) {
		final int note;
		if(row == rowAL) {
			note = 49+row.indexOf(keyCode);
		}
		else {
			note = 60+row.indexOf(keyCode);
		}
		channels[instrument].noteOff(note);
	}

	private String getNoteName(int noteNumber) {
		String name = notes[((noteNumber-12) % notes.length)];
		return name;
	}
	
	void initialize() {
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
			channels = synth.getChannels();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
	}
}
