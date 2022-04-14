package pathfinding;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Grid extends JPanel implements ActionListener, KeyListener {
	public static final int WIDTH = 1016;
	public static final int HEIGHT = 1000;
	public static final int NODESIZE = 50;
	static Node[][] grid = new Node[50][50];
	JFrame frame = new JFrame();
	Timer timer = new Timer(1000 / 30, this);
	public static Node start;
	public static Node goal;

	ArrayList<Node> path = new ArrayList<Node>();
	ArrayList<Node> checked = new ArrayList<Node>();
	long frameCounter = 0;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Grid grid = new Grid();
		grid.setup();
	}

	void setup() {
		frame.add(this);
		frame.setVisible(true);
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(this);
		frame.addMouseListener(new ClickListener());
		frame.pack();
		initGrid();
		timer.start();
	}

	void initGrid() {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				grid[x][y] = new Node(x, y);
			}
		}
	}

	public void paintComponent(Graphics g) {
		draw(g);
	}

	void draw(Graphics g) {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				grid[x][y].draw(g);
			}
		}
		highlights();
		frameCounter++;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println();
		if (e.getKeyCode() == 10 && start != null && goal != null) {
			path = findPath(start, goal);
			System.out.println();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}

	public static int[] convertToGridPosition(int x, int y) {
		int[] indices = { (x - 8) / NODESIZE, (y - 30) / NODESIZE };
		return indices;
	}

	public static Node getNode(int x, int y) {
		if (x < grid.length && y < grid[x].length && x >= 0 && y >= 0) {
			return grid[x][y];
		} else {
			return null;
		}
	}

	public static ArrayList<Node> getNeighborsOf(Node node) {
		int x = node.getX();
		int y = node.getY();

		ArrayList<Node> neighbors = node.getNeighbors();
		if (neighbors == null) {
			neighbors = new ArrayList<Node>();
			Node north = getNode(x, y + 1);
			Node south = getNode(x, y - 1);
			Node east = getNode(x + 1, y);
			Node west = getNode(x - 1, y);

			if ((x + y) % 2 == 1) {
				if (north != null)
					neighbors.add(north);
				if (south != null)
					neighbors.add(south);
				if (east != null)
					neighbors.add(east);
				if (west != null)
					neighbors.add(west);
			} else {
				if (east != null)
					neighbors.add(east);
				if (west != null)
					neighbors.add(west);
				if (north != null)
					neighbors.add(north);
				if (south != null)
					neighbors.add(south);
			}
			node.setNeighbors(neighbors);
			return neighbors;
		} else {
			return neighbors;
		}
	}

	ArrayList<Node> findPath(Node start, Node goal) {
		clearHighlights();
		PriorityQueue<Node> frontier = new PriorityQueue<Node>();
		HashMap<Node, Node> came_from = new HashMap<Node, Node>();
		HashMap<Node, Integer> costs_so_far = new HashMap<Node, Integer>();
		frontier.add(start);
		came_from.put(start, null);
		costs_so_far.put(start, 0);
		Node end = start;
		int nodeCount = 0;

		pathing: while (frontier.size() > 0) {

			Node current = frontier.remove();
			checked.add(current); // For display purposes only
			if (current == goal) {
				break;
			}
			for (Node next : getNeighborsOf(current)) {
				checked.add(next); // For display purposes only
				int new_cost = costs_so_far.get(current) + cost(current, next);
				if (!costs_so_far.containsKey(next) || new_cost < costs_so_far.get(next)) {
					costs_so_far.put(next, new_cost);
					int priority = new_cost + heuristic(goal, next);
					next.priority = priority;
					frontier.add(next);
					came_from.put(next, current);
					nodeCount++;
				}
				if (nodeCount > 1000) {
					end = start;
					System.out.println("Path too big");
					break pathing;
				}
			}
		}
		end = goal;
		return fillPath(start, came_from, end);
	}

	public static int heuristic(Node a, Node b) {
		// Manhattan distance on a square grid
		return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
	}

	public static int cost(Node a, Node b) {
		return b.cost;
	}

	public ArrayList<Node> fillPath(Node start, HashMap<Node, Node> came_from, Node end) {
		Node current = end;
		ArrayList<Node> path = new ArrayList<Node>();
		while (current != start) {
			path.add(current);
			current = came_from.get(current);
		}
		ArrayList<Node> reversePath = new ArrayList<Node>();
		for (int i = path.size() - 1; i >= 0; i--) {
			reversePath.add(path.get(i));
		}
		return reversePath;
	}

	public void clearHighlights() {
		checked.clear();
		path.clear();
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				if (grid[x][y].color != Color.RED)
					grid[x][y].setColor(Color.LIGHT_GRAY);
			}
		}
		start.setColor(Color.GREEN);
		goal.setColor(Color.BLUE);
	}

	public void highlights() {
		if(frameCounter%10 != 0) return;
		if (checked.size() > 0) {
			Node n = checked.get(0);
			if (grid[n.x][n.y].color == Color.LIGHT_GRAY) {
				checked.remove(0);
				if(checked.contains(n)) grid[n.x][n.y].setColor(Color.DARK_GRAY);
				else grid[n.x][n.y].setColor(Color.CYAN);
				System.out.println("LG:" + n.x + "," + n.y);
			} else {
				checked.remove(0);
			}
		} else if (path.size() > 0) {
			Node n = path.get(0);
			if (n != goal) {
				grid[n.x][n.y].setColor(Color.YELLOW);
				System.out.println("Y:" + n.x + "," + n.y);
			}
			path.remove(0);

		}
	}
}
