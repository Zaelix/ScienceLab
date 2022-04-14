package gridgames.gridnations;

import java.awt.Color;

public class Nation {
	int id;
	Color color;
	int landCount;
	int soldiers = 100;
	
	public Nation(int id, Color color) {
		this.id = id;
		this.color = color;
	}
	
	public int getSoldiers() {
		return soldiers;
	}
	
	public int getLandCount() {
		return landCount;
	}
}
