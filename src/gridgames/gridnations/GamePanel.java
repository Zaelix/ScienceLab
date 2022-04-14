package gridgames.gridnations;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener{

	private static final long serialVersionUID = 1L;
	
	GDXGame game;
	GameInput input;
	Timer timer;
	Font titleFont;
	Font optionsFont;
	
	public GamePanel(GDXGame g, GameInput input, int fps) {
		this.game = g;
		this.input = input;
		titleFont = new Font("Arial", Font.PLAIN, 48);
		optionsFont = new Font("Arial", Font.PLAIN, 24);
		
		timer = new Timer(1000 / fps, this);
		//timer.start();
	}
	
	public void paintComponent(Graphics g){
		Tile[][] board = game.getBoard();
		
		for (int j = 0; j < game.height; j++) {
			for (int i = 0; i < game.width; i++) {
				board[i][j].render(g);
			}
		}
	}
	public void startTimer() {
		timer.start();
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		input.mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		input.mouseMoved(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		input.mouseClicked(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		input.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		input.mouseReleased(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		input.mouseEntered(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		input.mouseExited(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		input.keyTyped(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		// TODO Auto-generated method stub
		if(key == KeyEvent.VK_ESCAPE) { 
			timer.stop(); 
			System.exit(0);
		}
		input.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		input.keyReleased(e);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		game.setTitle(game.getTitle() + " (" + 1000/game.getElapsedTime() + " FPS), Troops: " + GridNations.player.soldiers);
		input.actionPerformed(e);
		repaint();
	}
}
