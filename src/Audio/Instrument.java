package Audio;

import java.util.ArrayList;

public class Instrument {
	String name;
	int channel;
	int center;
	int range;
	int speed;
	ArrayList<Note> phrase;
	boolean isPlaying = false;

	public Instrument(String name, int channel, int center, int range, int speed) {
		if(channel < 0) channel = 0;
		if(channel > 15) channel = 15;
		this.name = name;
		this.channel = channel;
		this.center = center;
		this.range = range;
		this.speed = speed;
		createPhrase();
	}

	public ArrayList<Note> createPhrase() {
		ArrayList<Note> phrase = new ArrayList<Note>();
		int total = 0;
		Note last = new Note(center, 80, speed);
		while (total < 4000) {
			Note note = Note.createNoteFromLastNote(last, 10, center, range);
			last = note;
			if (note.duration <= 4000 - total) {
				total += note.duration;
			} else {
				note.duration = 4000 - total;
				total += note.duration;
			}
			phrase.add(note);
		}
		this.phrase = phrase;
		return phrase;
	}

	public void playPhrase() {
		new Thread(new Runnable() {
			public void run() {
				try {
					isPlaying = true;
					for (Note note : phrase) {
						playNote(note);
						System.out.println(name + ": " + note.noteNumber + ", " + note.duration + "ms" );
						Thread.sleep(note.duration);
					}
					isPlaying = false;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	public void playNote(Note note) {
		new Thread(new Runnable() {
			public void run() {
				MidiBand.channels[channel].noteOn(note.noteNumber, note.volume);
				try {
					Thread.sleep(note.duration);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				MidiBand.channels[channel].noteOff(note.noteNumber);

			}
		}).start();
	}
}
