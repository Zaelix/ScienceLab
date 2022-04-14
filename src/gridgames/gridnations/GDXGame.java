package gridgames.gridnations;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GDXGame {
	public int width;
	public int height;
	boolean isDisplayingNeighborCounts = false;
	int cubeSize = 35;
	int maxFPS = 30;
	long elapsedTime = 0;
	long lastFrameTime = 0;
	String title;
	
	JFrame window;
	GamePanel panel;
	Tile[][] board;
	Tile[][] blankBoard;

	Tile fakeTile = new Tile(1000, 1000, 1000, 1000, 2, this);

	public GDXGame(int w, int h, String title, GameInput input, int fps, int squareSize) {
		maxFPS = title.equals("SUPER CONWAY BROS") ? 1000 : 30;
		cubeSize = squareSize;
		int realWidth = Math.max(w, 0);
		int realHeight = Math.max(h, 0);
		this.width = realWidth;
		this.height = realHeight;
		this.title = title;
		board = new Tile[realWidth][realHeight];
		blankBoard = new Tile[realWidth][realHeight];
		createTiles();
		window = new JFrame(title);
		int frames = Math.min(fps, maxFPS);
		panel = new GamePanel(this, input, frames);
		window.add(panel);
		window.addKeyListener(panel);
		window.addMouseListener(panel);
		window.addMouseMotionListener(panel);
		window.setPreferredSize(new Dimension(realWidth * (cubeSize) + 17, realHeight * (cubeSize) + 40));
		window.setResizable(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);
		panel.startTimer();
	}
	public GDXGame(int w, int h, String title, GameInput input, int fps) {
		this(w, h, title, input, fps, title.equals("SUPER CONWAY BROS") ? 10 : 35);
	}

	/**
	 * Creates the blank board with empty tile objects using the GDXGame's width and height.
	 */
	private void createTiles() {
		int count = 0;
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				Tile t = new Tile(i, j, i * cubeSize, j * cubeSize, cubeSize, this);
				t.setColor(Color.gray);
				board[i][j] = t;
				blankBoard[i][j] = t;
				count++;
			}
		}
		System.out.println(count + " Squares created, with size " + cubeSize);
	}

	/**
	 * Retrieves this game's game board object.
	 * @return 2D Tile array that belongs to this GDXGame.
	 */
	public Tile[][] getBoard() {
		return board;
	}
	
	/**
	 * Retrieves a blank board for this game's width and size.
	 * @return 2D Tile array of blank Tile objects.
	 */
	public Tile[][] getBlankBoard(){
		return blankBoard;
	}
	/**
	 * Sets this game's board object to the given board.
	 * @param newBoard : the 2D Tile array representing the new board object.
	 */
	public void setBoard(Tile[][] newBoard) {
		this.board = newBoard;
	}

	/**
	 * Retrieves this game board's width, in Tiles
	 * @return an int representing how many tiles wide this game's board is.
	 */
	public int getBoardWidth() {
		return this.width;
	}
	/**
	 * Retrieves this game board's height, in Tiles
	 * @return an int representing how many tiles high this game's board is.
	 */
	public int getBoardHeight() {
		return this.height;
	}
	/**
	 * Retrieves the size of an individual tile.
	 * @return an int representing how many pixels per side each individual tile is.
	 */
	public int getCubeSize() {
		return this.cubeSize;
	}

	public JFrame getWindow() {
		return this.window;
	}
	/**
	 * Retrieves the Tile object representing which tile the user has clicked on.
	 * @param e : The mouse event for the click in question.
	 * @return a Tile object from this board which the user has clicked on.
	 */
	public Tile getTileClicked(MouseEvent event) {
		int x = (int) Math.ceil((event.getX() - 10) / cubeSize);
		int y = (int) Math.ceil((event.getY() - 32) / cubeSize);
		return board[x][y];
	}
	/**
	 * Calculates the amount of time since the last getElapsedTime() call.
	 * @return a long holding the number of milliseconds since the last call.
	 */
	public long getElapsedTime() {
		elapsedTime = System.currentTimeMillis() - lastFrameTime;
		lastFrameTime = System.currentTimeMillis();
		return elapsedTime;
	}
	
	/**
	 * Retrieves the title of this game's JFrame object.
	 * @return a String with the title of the frame as its value.
	 */
	public String getTitle() {
		return this.title;
	}
	/**
	 * Sets the title of this game's JFrame object.
	 * @param title : the new title for this game's JFrame object.
	 */
	public void setTitle(String title) {
		this.getWindow().setTitle(title);
	}
	
	/**
	 * Forces this game's JPanel object to refresh what is being drawn.
	 */
	public void refreshDisplay() {
		panel.repaint();
	}

	/**
	 * Toggles whether this game board's Tiles display the number of neighbor Tiles they have.
	 */
	public void toggleNeighborCountDisplay() {
		isDisplayingNeighborCounts = !isDisplayingNeighborCounts;
		
	}
}
