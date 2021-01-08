package Audio;

import java.util.Random;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class MidiBand {
	Synthesizer synth;
	public static MidiChannel[] channels;
	static int channel = 0; // 0 is a piano, 9 is percussion, other channels are for other instruments. 0 - 15

	int volume = 120; // between 0 and 127
	int duration = 200; // in milliseconds

	Instrument piano = new Instrument("Piano", 0, 60, 15, 300);
	Instrument bass = new Instrument("Bass", 1, 30, 15, 1000);
	public static void main(String[] args) {
		MidiBand band = new MidiBand();
		band.initialize();
		band.repeatPhrase(4);
		//band.playNonsense();
	}
	
	void playPhrase() {
		piano.playPhrase();
		bass.playPhrase();
	}
	

	void repeatPhrase(int count) {
		for (int i = 0; i < count; i++) {
			playPhrase();
			System.out.println("Phrase "+i+" Complete!");
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		stopTheBand();
	}

	void initialize() {
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
			channels = synth.getChannels();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		channels = synth.getChannels();
	}

	public void playTest() {

		try {

			// --------------------------------------
			// Play a few notes.
			// The two arguments to the noteOn() method are:
			// "MIDI note number" (pitch of the note),
			// and "velocity" (i.e., volume, or intensity).
			// Each of these arguments is between 0 and 127.
			channels[channel].noteOn(60, volume); // C note
			Thread.sleep(duration);
			channels[channel].noteOff(60);
			channels[channel].noteOn(62, volume); // D note
			Thread.sleep(duration);
			channels[channel].noteOff(62);
			channels[channel].noteOn(64, volume); // E note
			Thread.sleep(duration);
			channels[channel].noteOff(64);

			Thread.sleep(500);

			// --------------------------------------
			// Play a C major chord.
			channels[channel].noteOn(60, volume); // C
			channels[channel].noteOn(64, volume); // E
			channels[channel].noteOn(67, volume); // G
			Thread.sleep(3000);
			channels[channel].allNotesOff();
			Thread.sleep(500);

			synth.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void playNote(Note note) {
		new Thread(new Runnable() {
			public void run() {
				channels[channel].noteOn(note.noteNumber, note.volume);
				try {
					Thread.sleep(note.duration);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				channels[channel].noteOff(note.noteNumber);

			}
		}).start();
	}
	
	void stopTheBand() {
		try {
			Thread.sleep(3000);
			channels[channel].allNotesOff();
			Thread.sleep(500);
			synth.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
