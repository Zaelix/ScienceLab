package gridgames.gridnations;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Stemsation1
 *
 */
public class Tile implements Comparable<Tile>{
	GDXGame game;
	Nation owner;
	int x;
	int y;
	int boardX;
	int boardY;
	int size;
	Color color;
	Color edgeColor;
	String displayString = "";
	public static boolean drawBorder = true;
	
	public Tile (int bX, int bY, int x, int y, int s, GDXGame g) {
		this.boardX = bX;
		this.boardY = bY;
		this.x = x;
		this.y = y;
		this.size = s;
		this.game = g;
		this.color = new Color(150,150,150);
		this.edgeColor = new Color(255, 255, 255);
		this.owner = GridNations.neutral;
	}
	
	public Nation getOwner() {
		return owner;
	}
	
	public void setOwner(Nation owner) {
		this.owner = owner;
		this.color = owner.color;
	}
	public void setColor(Color col) {
		this.color = col;
	}
	
	public void setColor(int r, int g, int b) {
		this.color = new Color(r, g, b);
	}
	
	public void setRandomColor() {
		Random gen = new Random();
		
		this.color = new Color(gen.nextInt(255),gen.nextInt(255),gen.nextInt(255));
	}
	
	public void setEdgeColor(Color col) {
		this.edgeColor = col;
	}
	
	public void setEdgeColor(int r, int g, int b) {
		this.edgeColor = new Color(r, g, b);
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public Color getEdgeColor() {
		return this.edgeColor;
	}
	
	public String getDisplayString() {
		return this.displayString;
	}
	public void setDisplayString(String str) {
		this.displayString = str;
	}
		
	/**
	 * Counts the number of neighbors of this Tile which are currently the given color, and sets the livingNeighbors count to this number.
	 * @param color : The color you wish to check for.
	 * @return int : The total count of neighbors with the given color.
	 */
	public int countNeighborsWithColor(Color color) {
		Tile[] neighbors = getAllNeighbors();
		int nLiving = 0;
		for (Tile n : neighbors) {
			if (n.getColor().equals(color)) {
				nLiving++;
			}
		}
		return nLiving;
	}
	
	public Tile[] getNeighborsWithColor(Color color) {
		ArrayList<Tile> tiles = new ArrayList<Tile>();

		Tile[] neighbors = getAllNeighbors();
		for (Tile n : neighbors) {
			if (n.getColor().equals(color)) {
				tiles.add(n);
			}
		}
		Tile[] tilesArray = new Tile[tiles.size()];
		tiles.toArray(tilesArray);
		return tilesArray;
	}
	
	/**
	 * Gets all neighboring tiles of the tile that is calling the function.
	 * @return Tile[] : An array of this Tile's eight neighbors.
	 */
	public Tile[] getAllNeighbors() {
		Tile[] neighbors = new Tile[8];

		neighbors[0] = getTopNeighbor();
		neighbors[1] = getRightNeighbor();
		neighbors[2] = getBottomNeighbor();
		neighbors[3] = getLeftNeighbor();
		
		neighbors[4] = getTopLeftNeighbor();
		neighbors[5] = getTopRightNeighbor();
		neighbors[6] = getBottomLeftNeighbor();
		neighbors[7] = getBottomRightNeighbor();
		return neighbors;
	}
	
	/**
	 * Gets all adjacent tiles to the tile that is calling the function.
	 * @return Tile[] : An array of this Tile's four adjacent neighbors.
	 */
	public Tile[] getAdjacentNeighbors() {
		Tile[] neighbors = new Tile[4];
		neighbors[0] = getTopNeighbor();
		neighbors[1] = getRightNeighbor();
		neighbors[2] = getBottomNeighbor();
		neighbors[3] = getLeftNeighbor();
		return neighbors;
	}
	
	/**
	 * Gets all diagonal tiles to the tile that is calling the function.
	 * @return Tile[] : An array of this Tile's four diagonal neighbors.
	 */
	public Tile[] getDiagonalNeighbors() {
		Tile[] neighbors = new Tile[4];
		neighbors[0] = getTopLeftNeighbor();
		neighbors[1] = getTopRightNeighbor();
		neighbors[2] = getBottomLeftNeighbor();
		neighbors[3] = getBottomRightNeighbor();
		return neighbors;
	}
	
	/**
	 * Gets the Tile object directly above the tile object that is calling the function.
	 * @return Tile : The Tile object which is this Tile's Top Center neighbor.
	 */
	public Tile getTopNeighbor() {
		int x = this.getBoardX();
		int y = this.getBoardY();
		if (y-1 >= 0) {
			Tile tile = game.getBoard()[x][y-1];
			return tile;
		}
		else return game.fakeTile; 
	}
	/**
	 * Gets the Tile object directly below the tile object that is calling the function.
	 * @return Tile : The Tile object which is this Tile's Bottom Center neighbor.
	 */
	public Tile getBottomNeighbor() {
		int x = this.getBoardX();
		int y = this.getBoardY();
		if (y+1 < game.getBoardHeight()) {
			Tile tile = game.getBoard()[x][y+1];
			return tile;
		}
		else return game.fakeTile; 
	}
	/**
	 * Gets the Tile object directly to the left of the tile object that is calling the function.
	 * @return Tile : The Tile object which is this Tile's Center Left neighbor.
	 */
	public Tile getLeftNeighbor() {
		int x = this.getBoardX();
		int y = this.getBoardY();
		if (x-1 >= 0) {
			Tile tile = game.getBoard()[x-1][y];
			return tile;
		}
		else return game.fakeTile; 
	}
	/**
	 * Gets the Tile object directly to the right of the tile object that is calling the function.
	 * @return Tile : The Tile object which is this Tile's Center Right neighbor.
	 */
	public Tile getRightNeighbor() {
		int x = this.getBoardX();
		int y = this.getBoardY();
		if (x+1 < game.getBoardWidth()) {
			Tile tile = game.getBoard()[x+1][y];
			return tile;
		}
		else return game.fakeTile; 
	}
	
	/**
	 * Gets the Tile object directly above and to the right of the tile object that is calling the function.
	 * @return Tile : The Tile object which is this Tile's Top Right neighbor.
	 */
	public Tile getTopRightNeighbor() {
		int x = this.getBoardX();
		int y = this.getBoardY();
		if (x+1 < game.getBoardWidth() && y-1 >= 0) {
			Tile tile = game.getBoard()[x+1][y-1];
			return tile;
		}
		else return game.fakeTile; 
	}
	
	/**
	 * Gets the Tile object directly below and to the right of the tile object that is calling the function.
	 * @return Tile : The Tile object which is this Tile's Bottom Right neighbor.
	 */
	public Tile getBottomRightNeighbor() {
		int x = this.getBoardX();
		int y = this.getBoardY();
		if (x+1 < game.getBoardWidth() && y+1 < game.getBoardHeight()) {
			Tile tile = game.getBoard()[x+1][y+1];
			return tile;
		}
		else return game.fakeTile; 
	}
	
	/**
	 * Gets the Tile object directly above and to the left of the tile object that is calling the function.
	 * @return Tile : The Tile object which is this Tile's Top Left neighbor.
	 */
	public Tile getTopLeftNeighbor() {
		int x = this.getBoardX();
		int y = this.getBoardY();
		if (x-1 >= 0 && y-1 >= 0) {
			Tile tile = game.getBoard()[x-1][y-1];
			return tile;
		}
		else return game.fakeTile; 
	}
	
	/**
	 * Gets the Tile object directly below and to the left of the tile object that is calling the function.
	 * @return Tile : The Tile object which is this Tile's Bottom Left neighbor.
	 */
	public Tile getBottomLeftNeighbor() {
		int x = this.getBoardX();
		int y = this.getBoardY();
		if (x-1 >= 0 && y+1 < game.getBoardHeight()) {
			Tile tile = game.getBoard()[x-1][y+1];
			return tile;
		}
		else return game.fakeTile; 
	}
	
	/**
	 * Returns this Tile's y position on the screen.
	 * @return int : This Tile's vertical pixel position.
	 */
	private int getY() {
		return y;
	}

	/**
	 * Returns this Tile's x position on the screen.
	 * @return int : This Tile's horizontal pixel position.
	 */
	private int getX() {
		return x;
	}
	/**
	 * Returns this Tile's y position on the game board array.
	 * @return int : This Tile's vertical position on this game's board object.
	 */
	private int getBoardY() {
		return boardY;
	}
	/**
	 * Returns this Tile's x position on the game board array.
	 * @return int : This Tile's horizontal position on this game's board object.
	 */
	private int getBoardX() {
		return boardX;
	}
	/**
	 * Displays this Tile on the given Graphics object.
	 * @param g : The Graphics object used to display this Tile.
	 */
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, size, size);
		if(drawBorder) {
			g.setColor(edgeColor);
			g.drawRect(x, y, size, size);
		}
		if(game.isDisplayingNeighborCounts && !displayString.equals("0")) {
			g.drawString(displayString, x, y+size);
		}
	}

	@Override
	public int compareTo(Tile o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
