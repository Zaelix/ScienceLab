package gridgames.gridnations;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class GridNations extends GameInput {
	static final int MENU = 0;
	static final int SPAWN = 1;
	static final int GAME = 2;
	static final int END = 3;
	static GDXGame game;
	Random gen = new Random();

	static long time = 0;
	static long counter = 0;
	static long frameCount = 0;
	
	Nation[] nations;
	public static Nation player;
	public static Nation neutral = new Nation(-1, Color.DARK_GRAY);
	public static Color[] nationColors = new Color[] {Color.BLUE, Color.RED, Color.GREEN, Color.PINK, Color.CYAN};
	int currentGameMode = 1;
	
	Queue<Tile> frontier =  new LinkedList<Tile>();
	public static void main(String[] args) {
		GridNations nations = new GridNations();
		game = new GDXGame(150, 100, "Grid Nations", nations, 30, 10);
		//Tile.drawBorder = false;
		time = System.currentTimeMillis();
		nations.initNations();
	}
	
	public void initNations() {
		nations = new Nation[3];
		for(int i = 0; i < nations.length; i++) {
			nations[i] = new Nation(i, nationColors[i]);
		}
		player = nations[0];
		for(Tile[] row : game.board) {
			for(Tile t : row) {
				t.setOwner(neutral);
			}
		}
	}
	
	public void attack(Nation attacker, Tile target, int soldiers) {
		if(soldiers >= attacker.getSoldiers()) soldiers = attacker.getSoldiers();
		attacker.soldiers -= soldiers;
		frontier.clear();
		frontier.add(target);
		while(!frontier.isEmpty() && soldiers > 0) {
			soldiers = attackTile(attacker, frontier.poll(), soldiers);
		}
		attacker.soldiers += Math.max(0, soldiers);
	}
	public int attackTile(Nation attacker, Tile target, int soldiers) {
		if(soldiers <= 0) return 0;
		Nation defender = target.getOwner();
		int defense = defender.getSoldiers()/defender.getLandCount();
		if(defense < 100) defense = 100;
		double ratio = Math.min((double)soldiers/Math.max(defense, 1),5);
		int remainingDefense = (int) (defense - soldiers/ratio);
		int remainingAttack = (int) (soldiers - defense*ratio);
		System.out.println("Ratio: "+ ratio + ", Defense/Remaining: " + defense+"/"+remainingDefense + ", Remaining Attack: " + remainingAttack);
		if(remainingDefense <= 0) {
			target.setOwner(attacker);
			defender.soldiers -= defense;
			Tile[] neighbors = target.getNeighborsWithColor(defender.color);
			for(Tile t : neighbors) {
				frontier.add(t);
			}
			return remainingAttack;
		}
		else {
			defender.soldiers -= defense - remainingDefense;
			return 0;
		}
	}
	
	public void countTiles() {
		neutral.landCount = 0;
		for(Nation n : nations) n.landCount = 0;
		for(Tile[] row : game.board) {
			for(Tile t : row) {
				if(t.owner.id != -1) {
					nations[t.owner.id].landCount++;
				}
				else neutral.landCount++;
			}
		}
		neutral.soldiers = neutral.landCount*100;
	}
	
	public void soldierIncome() {
		for(Nation n : nations) {
			n.soldiers += n.landCount;
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getButton() == 1) {
			Tile tile = game.getTileClicked(e);
			attack(player, tile, player.soldiers);
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		countTiles();
		soldierIncome();
		//System.out.println("Neutral has "+neutral.landCount + " land tiles. Player has " + player.landCount + ", and " + player.soldiers + " troops.");
	}
}
