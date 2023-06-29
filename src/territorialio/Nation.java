package territorialio;

import java.util.ArrayList;

public class Nation {
	int troops = 512;
	int land = 12;
	int radius = 1;
	int expandsPerTick = 2;
	double interest = 7;
	int expandTick;
	double percentSend;
	Send attack;
	ArrayList<Send> sends = new ArrayList<Send>();
	
	Nation(int expandTick){
		this.expandTick = expandTick;
		this.percentSend = 0.50;
	}
	Nation(int expandTick, double percentSend){
		this.expandTick = expandTick;
		this.percentSend = percentSend;
	}
	
	String tick(int tick) {
		if(tick == expandTick) send(percentSend);
		troops += troops * (interest/100);
		int expands = 0;
		for(int i = 0; i < expandsPerTick; i++) {
			if(expand()) {
				expands++;
			}
		}
		if(Territorial_IO_Calculator.debug)	System.out.println("Expanded " + expands + " times this tick.");
		calculateInterest();
		return getStatus();
	}
	
	String getStatus() {
		return "Nation now has " + troops + " troops, " + land + " land, and " + String.format("%1.3f", interest) + "% interest.";
	}
	
	//18 cycles until interest plateau
	void calculateInterest () {
		double interest=1;
	
		if(troops > land*100) {
			interest = 1;
		}
		else{
			double p = (double)((double)troops-((double)land*100.0)) / (double)(land*100.0);
			p = p > 1 ? 1 : p;
			interest=1+Math.pow(4*((double)land/(double)Territorial_IO_Calculator.totalLand),0.5)*p;
		}
		this.interest = Territorial_IO_Calculator.interest*(interest);
		//System.out.println("P="+p + ", LocalInterest=" + String.format("%1.3f", interest));
	}
	
	String cycle() {
		troops += land;
		return "Nation gains " + land + " income, and now has " + troops + " troops.";
	}
	
	void send(double percent) {
		int force = (int) (troops*percent);
		Send newAttack = new Send(percent, force);
		if(attack == null || attack.count == 0) {
			attack = newAttack;
		}
		else {
			attack.count += force;
		}
		troops -= force;
		sends.add(newAttack);
	}
	boolean expand() {
		
		int claimable = 8+(radius*4); // cost = 3 * claimable?
		// Inaccurate; expansion takes multiple subticks, likely based on empty neighboring tiles.
		if(attack != null && attack.count > claimable*3) {
			land += claimable;
			attack.count -= claimable*3;
			radius++;
			return true;
		}
		else if(attack != null && attack.count > 0) {
			attack = null;
			return false;
		}
		return false;
	}
}
