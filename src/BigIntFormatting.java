import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BigIntFormatting extends JPanel implements KeyListener{
	BigInteger number = BigInteger.valueOf(1);
	public static void main(String[] args) {
		BigIntFormatting bif = new BigIntFormatting();
		bif.start();
	}

	private void start() {
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(800,600));
		frame.add(this);
		frame.addKeyListener(this);
		frame.pack();
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		NumberFormat formatter = new DecimalFormat("0.###E0");
	    String str = formatter.format(number);
		g.drawString(str, 100, 100);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub\
		int keyCode = e.getKeyCode();
		System.out.println(keyCode);
		if(keyCode == 37) {
			number = number.multiply(BigInteger.valueOf(2));
		}if(keyCode == 38) {
			number = number.multiply(BigInteger.valueOf(10));
		}if(keyCode == 39) {
			number = number.multiply(BigInteger.valueOf(101));
		}if(keyCode == 40) {
			number = number.multiply(BigInteger.valueOf(1103));
		}
		
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
