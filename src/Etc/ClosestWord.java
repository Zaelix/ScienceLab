package Etc;

public class ClosestWord {
	String[] words;

	ClosestWord(String[] words) {
		this.words = words;
	}

	public static void main(String[] args) {
		String[] strs = new String[] { "cherry", "pineapple", "melon", "strawberry", "raspberry" };
		ClosestWord cw = new ClosestWord(strs);
		System.out.println("Closest is "+cw.findMostSimilar("strawbe"));
	}

	public String findMostSimilar(String to) {
		// TODO: this is your task ;)
		int closestDiff = Integer.MAX_VALUE;
		String closest = "";
		for (String w : words) {
			int diff = countDiff(to, w);
			System.out.println(w + ":" + to + ":" + diff);
			if (diff < closestDiff) {
				closestDiff = diff;
				closest = w;
			}

		}
		return closest;
	}

	public int countDiff(String wrd1, String wrd2) {
		int diff = 0;
		int sim = 0;
		int m = 0;
		for (int i = 0; i < wrd1.length(); i++) {
			char c1 = wrd1.charAt(i);
			for (int k = m; k < wrd2.length(); k++) {
				if (wrd2.charAt(k) == c1) {
					sim += 1;
					m = k+1;
					break;
				}
			}
		}
		int lon = wrd1.length() > wrd2.length() ? wrd1.length() : wrd2.length();
		diff = lon - sim;
		return diff;
	}
}
