package Etc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JPanel;

public class SuperConways extends JPanel implements ActionListener, KeyListener, MouseListener {
	Random gen = new Random();
	byte[][] board = new byte[200][200];
	byte[][] shareModifiers = new byte[200][200];
	byte[][][] smallestNeighbor = new byte[200][200][2];
	Color[] cellColors;
	int cellSize = 20;

	boolean isPaused = false;
	static long time = 0;
	static long counter = 0;
	static long frameCount = 0;

	public SuperConways() {
		cellColors = new Color[] { Color.white,new Color(155, 0, 0), new Color(255, 0, 0), new Color(0, 155, 0),
				new Color(0, 255, 0),new Color(0, 0, 155),new Color(0, 0, 255), Color.black, 
				Color.pink, Color.magenta};
		for(int i = 1; i < cellColors.length-3; i++) {
			cellColors[i] = new Color(i*40,0,0);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!isPaused)
			for (int i = 0; i < 1; i++)
				step();
		// repaint();
	}

	public void step() {
		calculateFrameRate();
		byte[][] livingNeighbors = new byte[board.length][board[0].length];
		byte[][] newBoard = new byte[board.length][board[0].length];
		smallestNeighbor = new byte[board.length][board[0].length][2];
		shareModifiers = new byte[board.length][board[0].length];

		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				livingNeighbors[x][y] = getNeighborCount(x, y);
			}
		}

		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				newBoard[x][y] = stepCell(livingNeighbors[x][y], board[x][y]);
				if(newBoard[x][y] >= 5) share(x,y);
			}
		}
		
		for (int x = 0; x < newBoard.length; x++) {
			for (int y = 0; y < newBoard[x].length; y++) {
				newBoard[x][y] += shareModifiers[x][y];
			}
		}
		
		board = newBoard;
		repaint();
	}

	public byte stepCellConways(byte livingNeighbors, byte currentValue) {
		if (livingNeighbors < 2 || livingNeighbors > 3)
			return 0;
		else if (livingNeighbors == 3)
			return 1;
		else
			return currentValue;
	}
	public byte stepCellBurn(byte livingNeighbors, byte currentValue) {
		byte dN = currentValue;
		if(livingNeighbors == 0 && currentValue == 0) return 1;
		dN = (byte) ((currentValue - (livingNeighbors >> 2)) % 7);
		if (livingNeighbors < 24)
			dN = (byte) ((currentValue + (livingNeighbors >> 3)) % 7);
		return (byte) Math.max(dN,0);
	}
	public byte stepCellFlameOut(byte livingNeighbors, byte currentValue) {
		byte dN = currentValue;
		dN = (byte) ((currentValue - (livingNeighbors >> 3)) % 7);
		if (livingNeighbors < 24)
			dN = (byte) ((currentValue + (livingNeighbors >> 3)) % 7);
		return dN;
	}
	public byte stepCell(byte livingNeighbors, byte currentValue) {
		if(currentValue > 3) {
			if(livingNeighbors == 48) return (byte) Math.max(currentValue-2,0);
			else if(livingNeighbors > 40) return (byte) Math.max(currentValue-1,0);
			else return (byte) Math.min(currentValue+1,6);
		}
		else {
			if(livingNeighbors < 5) return (byte) (Math.max(currentValue-1,0));
			else return (byte) Math.min(currentValue+1,6);
		}
	}
	public void share(int x, int y) {
		int xx = x + smallestNeighbor[x][y][0];
		int yy = y + smallestNeighbor[x][y][1];
		//shareModifiers[x][y]--;
		shareModifiers[xx][yy]++;
	}
	public byte getNeighborCount(int x, int y) {
		byte[] smallestPos = new byte[2];
		byte smallest = 127;
		byte neighbors = 0;
		for(byte i = -1; i < 2; i++) {
			for(byte k = -1; k < 2; k++) {
				if(i != 0 || k != 0) {
					if(x+i >= 0 && x+i < board.length && y+k >= 0 && y+k < board.length) {
						if(board[x+i][y+k] != 0) {
							neighbors += board[x+i][y+k];
						}
						if(board[x+i][y+k] < smallest) {
							smallest = board[x+i][y+k];
							smallestPos[0] = i;
							smallestPos[1] = k;
						}
					}
				}
			}
		}
		smallestNeighbor[x][y] = smallestPos;
		return neighbors;
	}
	public byte getNeighborCountConways(int x, int y) {
		byte neighbors = 0;
		for(byte i = -1; i < 2; i++) {
			for(byte k = -1; k < 2; k++) {
				if((i != 0 || k != 0) && board[x+i][y+k] != 0) {
					neighbors++;
				}
			}
		}
		return neighbors;
	}
	public void randomize() {
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[0].length; y++) {
				board[x][y] = (byte) gen.nextInt(2);
			}
		}
		repaint();
	}
	public void clear() {
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[0].length; y++) {
				board[x][y] = 0;
			}
		}
		repaint();
	}
	public void drawCells(Graphics g) {
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[0].length; y++) {
				
				Color c = cellColors[Math.max(Math.min(board[x][y], 9), 0)];
				g.setColor(c);
				g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
				g.setColor(Color.white);
				g.drawString(board[x][y]+"", x*cellSize, y*cellSize+cellSize);
			}
		}
	}
	public void calculateFrameRate() {
		frameCount++;
		long dv = System.currentTimeMillis() - time;
		time = System.currentTimeMillis();
		counter += dv;
		if (counter > 1000) {
			System.out.println("Current FPS: " + frameCount);
			frameCount = 0;
			counter = 0;
		}
	}
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, SuperConwaysRunner.WIDTH, SuperConwaysRunner.HEIGHT);
		drawCells(g);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		if (keyCode == 32)
			isPaused = !isPaused;
		if (keyCode == 10)
			randomize();
		if (keyCode == 8)
			clear();
		if (keyCode == 61) {
		}
		if (keyCode == 45) {
		}
		System.out.println(keyCode);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = (int) Math.ceil((e.getX() - 10) / cellSize);
		int y = (int) Math.ceil((e.getY() - 32) / cellSize);
		board[x][y] = (byte) (++board[x][y] % 2);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
