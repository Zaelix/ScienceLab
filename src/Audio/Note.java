package Audio;

import java.util.Random;

public class Note {
	public static Random gen = new Random();
	int noteNumber;
	int volume;
	int duration;
	Note(int noteNumber, int volume, int duration){
		if(noteNumber > 127) noteNumber = 127;
		if(noteNumber < 0) noteNumber = 0;
		if(volume > 127) volume = 127;
		if(volume < 0) volume = 0;
		this.noteNumber = noteNumber;
		this.volume = volume;
		this.duration = duration;
	}
	public static int GetRandomNote() {
		return gen.nextInt(127);
	}
	
	public static int GetRandomDuration() {
		return (gen.nextInt(10)+1)*100;
	}
	
	public static Note createRandomNote(int center, int range, int duration) {
		return new Note((gen.nextInt(range)/2)+center, gen.nextInt(87)+40, duration);
	}
	public static Note createRandomNote() {
		return createRandomNote(47, 47, (gen.nextInt(10)+1)*100);
	}
	public static Note createNoteFromLastNote(Note last, int accuracy, int center, int range) {
		if(last==null) return createRandomNote();
		int[] possibles = new int[accuracy];
		for (int i = 0; i < possibles.length; i++) {
			possibles[i] = (gen.nextInt(range)/2)+center;
		}
		int noteNum = getNearest(possibles, last.noteNumber);
		
		int[] possibleDurations = new int[accuracy];
		for (int i = 0; i < possibles.length; i++) {
			possibleDurations[i] = (gen.nextInt(10)+1)*100;
		}
		int duration = getNearest(possibleDurations, last.duration);
		
		return new Note(noteNum, gen.nextInt(87)+40, duration);
	}
	
	static int getNearest(int[] possibles, int target) {
		int value = 0;
		int smallestDiff = 1000;
		for (int i = 0; i < possibles.length; i++) {
			int diff = Math.abs(possibles[i] - target);
			if(diff < smallestDiff && diff != 0) {
				smallestDiff = diff;
				value = possibles[i];
			}
		}
		return value;
	}
	
}
