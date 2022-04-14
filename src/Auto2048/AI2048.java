package Auto2048;

import java.util.Random;

public class AI2048 {
	Random gen = new Random();
	static int UP = 87;
	static int DOWN = 83;
	static int LEFT = 65;
	static int RIGHT = 68;
	public static boolean isActive = false;
	int[] dirVals = new int[4]; //up down left right
	int[] blankVals = new int[4];
	
	public void step() {
		if(isActive) {
			calcVals();
			int max = Math.max(dirVals[0], Math.max(dirVals[1], Math.max(dirVals[2], dirVals[3])));
			if(max == dirVals[0] && max != 0) move(UP);
			else if(max == dirVals[1] && max != 0) move(DOWN);
			else if(max == dirVals[2] && max != 0) move(LEFT);
			else if(max == dirVals[3] && max != 0) move(RIGHT);
			else {
				int maxBlanks = Math.max(blankVals[0], Math.max(blankVals[1], Math.max(blankVals[2], blankVals[3])));
				if(maxBlanks == blankVals[0]) move(UP);
				else if(maxBlanks == blankVals[1]) move(DOWN);
				else if(maxBlanks == blankVals[2]) move(LEFT);
				else if(maxBlanks == blankVals[3]) move(RIGHT);
				
				//int[] dirs = {87,83,65,88};
				//int dir = dirs[gen.nextInt(4)];
				//move(dir);
			}
		}
	}
	
	private void calcVals() {
		dirVals[0] = calcUp();
		dirVals[1] = calcDown();
		dirVals[2] = calcLeft();
		dirVals[3] = calcRight();
	}
	
	private void move(int key) {
		ObjectManager2048.scanbot.keyPress(key);
	}
	
	private int calcUp() {
		int val = 0;
		blankVals[0] = 0;
		for(int x = 0; x < 4; x++) {
			for(int y = 1; y < 4; y++) {
				if(ObjectManager2048.grid[x][y-1].value == 0) blankVals[0]++;
				if(ObjectManager2048.grid[x][y].value == 0) continue;
				if(ObjectManager2048.grid[x][y].value == ObjectManager2048.grid[x][y-1].value) {
					val += ObjectManager2048.grid[x][y].value;
				}
				else if(ObjectManager2048.grid[x][y-1].value == 0) {
					if(y-2 >= 0 && ObjectManager2048.grid[x][y].value == ObjectManager2048.grid[x][y-2].value) {
						val += ObjectManager2048.grid[x][y].value;
					}
					else if(y-2 >= 0 && ObjectManager2048.grid[x][y-2].value == 0) {
						if(y-3 >= 0 && ObjectManager2048.grid[x][y].value == ObjectManager2048.grid[x][y-3].value) {
							val += ObjectManager2048.grid[x][y].value;
						}
					}
				}
			}
		}
		return val;
	}
	
	private int calcDown() {
		int val = 0;
		blankVals[1] = 0;
		for(int x = 0; x < 4; x++) {
			for(int y = 0; y < 3; y++) {
				if(ObjectManager2048.grid[x][y+1].value == 0) blankVals[1]++;
				if(ObjectManager2048.grid[x][y].value == 0) continue;
				if(ObjectManager2048.grid[x][y].value == ObjectManager2048.grid[x][y+1].value) {
					val += ObjectManager2048.grid[x][y].value;
				}
				else if(ObjectManager2048.grid[x][y+1].value == 0) {
					if(y+2 < 4 && ObjectManager2048.grid[x][y].value == ObjectManager2048.grid[x][y+2].value) {
						val += ObjectManager2048.grid[x][y].value;
					}
					else if(y+2 < 4 && ObjectManager2048.grid[x][y+2].value == 0) {
						if(y+3 < 4 && ObjectManager2048.grid[x][y].value == ObjectManager2048.grid[x][y+3].value) {
							val += ObjectManager2048.grid[x][y].value;
						}
					}
				}
			}
		}
		return val;
	}
	private int calcLeft() {
		int val = 0;
		blankVals[2] = 0;
		for(int x = 1; x < 4; x++) {
			for(int y = 0; y < 4; y++) {
				if(ObjectManager2048.grid[x-1][y].value == 0) blankVals[2]++;
				if(ObjectManager2048.grid[x][y].value == 0) continue;
				if(ObjectManager2048.grid[x][y].value == ObjectManager2048.grid[x-1][y].value) {
					val += ObjectManager2048.grid[x][y].value;
				}
				else if(ObjectManager2048.grid[x-1][y].value == 0) {
					if(x-2 >= 0 && ObjectManager2048.grid[x][y].value == ObjectManager2048.grid[x-2][y].value) {
						val += ObjectManager2048.grid[x][y].value;
					}
					else if(x-2 >= 0 && ObjectManager2048.grid[x-2][y].value == 0) {
						if(x-3 >= 0 && ObjectManager2048.grid[x][y].value == ObjectManager2048.grid[x-3][y].value) {
							val += ObjectManager2048.grid[x][y].value;
						}
					}
				}
			}
		}
		return val;
	}private int calcRight() {
		int val = 0;
		blankVals[3] = 0;
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 4; y++) {
				if(ObjectManager2048.grid[x+1][y].value == 0) blankVals[3]++;
				if(ObjectManager2048.grid[x][y].value == 0) continue;
				if(ObjectManager2048.grid[x][y].value == ObjectManager2048.grid[x+1][y].value) {
					val += ObjectManager2048.grid[x][y].value;
				}
				else if(ObjectManager2048.grid[x+1][y].value == 0) {
					if(x+2 < 4 && ObjectManager2048.grid[x][y].value == ObjectManager2048.grid[x+2][y].value) {
						val += ObjectManager2048.grid[x][y].value;
					}
					else if(x+2 < 4 && ObjectManager2048.grid[x+2][y].value == 0) {
						if(x+3 < 4 && ObjectManager2048.grid[x][y].value == ObjectManager2048.grid[x+3][y].value) {
							val += ObjectManager2048.grid[x][y].value;
						}
					}
				}
			}
		}
		return val;
	}
}
