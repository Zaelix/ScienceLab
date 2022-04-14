package galaxysim;

public class SectorPosition {
	int x;
	int y;
	public SectorPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public boolean equals(SectorPosition other) {
		return this.x==other.x&&this.y==other.y;
	}
	
	public String toString() {
		return "["+x+","+y+"]";
	}
}
