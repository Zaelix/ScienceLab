package Etc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class SinePanel extends JPanel implements ActionListener, KeyListener{
	
	double[] sine;
	double time = 0;
	int center = 500;
	
	double speed = 0.3;
	double frequency = 0.2;
	int amplitude = 400;

	double targetSpeed = 0.3;
	double targetFrequency = 0.2;
	double targetAmplitude = 400;
	
	public SinePanel(){
		sine = new double[SineVisualizer.WIDTH*2];
		calculateSine(time);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//visualizer.repaint();
		calculateSine(time);
		time+=speed;
		amplitude += (targetAmplitude - amplitude)/20.0;
		frequency += (targetFrequency - frequency)/20.0;
		repaint();
	}
	
	public void calculateSine(double t) {
		for(int i = 0; i < sine.length; i++) {
			sine[i] = (Math.sin(Math.toRadians((t+i)*frequency))*amplitude);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, SineVisualizer.WIDTH, SineVisualizer.HEIGHT);
		g.setColor(Color.RED);
		for(int i = 0; i < sine.length; i++) {
			g.fillRect(i/2, center+(int)sine[i], 3, 10);
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		if(keyCode==38) {
			targetAmplitude+=10;
		}if(keyCode==40) {
			targetAmplitude-=10;
		}
		if(keyCode==37) {
			speed -= 0.1;
		}if(keyCode==39) {
			speed += 0.1;
		}
		if(keyCode==61) {
			targetFrequency += 0.01;
		}
		if(keyCode==45) {
			targetFrequency -= 0.01;
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
}
