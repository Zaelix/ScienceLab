package pathfinding;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Node implements Comparable<Node> {
	int x;
	int y;
	int priority = 0;
	int cost = 1;
	ArrayList<Node> neighbors;
	Color color;
	Node(int x, int y, Color color){
		this.x = x;
		this.y = y;
		this.color = color;
	}
	Node(int x, int y){
		this.x = x;
		this.y = y;
		this.color = Color.LIGHT_GRAY;
	}
	
	public void setColor(Color c) {
		this.color = c;
		if(c == Color.RED) cost = 100;
		else cost = 0;
	}
	
	public void setNeighbors(ArrayList<Node> n) {
		this.neighbors = n;
	}
	
	void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x*Grid.NODESIZE, y*Grid.NODESIZE, Grid.NODESIZE, Grid.NODESIZE);
		g.setColor(Color.BLACK);
		g.drawRect(x*Grid.NODESIZE, y*Grid.NODESIZE, 50, 50);
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public ArrayList<Node> getNeighbors() {
		return neighbors;
	}
	
	public int compareTo(Node n){
		if(priority > n.priority) return 1;
		else return -1;
	}
}
