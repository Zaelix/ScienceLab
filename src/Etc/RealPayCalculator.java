package Etc;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class RealPayCalculator implements ActionListener {
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	JButton calculate = new JButton("Calculate");
	JTextField hourly = new JTextField();
	JTextField hoursWorked = new JTextField();
	JTextField commuteTime = new JTextField();
	JTextField commuteDistance = new JTextField();
	JTextField fuelEfficiency = new JTextField();
	JTextField gasCost = new JTextField();
	JLabel result = new JLabel("Click to Calculate...");
	
	public static void main(String[] args) {
		RealPayCalculator calc = new RealPayCalculator();
		calc.setup();
	}
	
	public void setup() {
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setPreferredSize(new Dimension(310,500));
		Dimension fieldWidth = new Dimension(100,30);
		Dimension labelWidth = new Dimension(160,30);
		
		JLabel hourlyLabel = new JLabel("Hourly rate");
		hourlyLabel.setPreferredSize(labelWidth);
		JLabel hoursWorkedLabel = new JLabel("Hours Worked");
		hoursWorkedLabel.setPreferredSize(labelWidth);
		JLabel commuteTimeLabel = new JLabel("Commute Time (minutes)");
		commuteTimeLabel.setPreferredSize(labelWidth);
		JLabel commuteDistanceLabel = new JLabel("Commute Distance (miles)");
		commuteDistanceLabel.setPreferredSize(labelWidth);
		JLabel fuelEfficiencyLabel = new JLabel("Fuel Efficiency");
		fuelEfficiencyLabel.setPreferredSize(labelWidth);
		JLabel gasCostLabel = new JLabel("Cost of Gas");
		gasCostLabel.setPreferredSize(labelWidth);
		
		hourly.setPreferredSize(fieldWidth);
		hoursWorked.setPreferredSize(fieldWidth);
		commuteTime.setPreferredSize(fieldWidth);
		commuteDistance.setPreferredSize(fieldWidth);
		fuelEfficiency.setPreferredSize(fieldWidth);
		gasCost.setPreferredSize(fieldWidth);
		calculate.setPreferredSize(new Dimension(290,30));
		result.setPreferredSize(new Dimension(290,300));
		calculate.addActionListener(this);
		result.setVerticalAlignment(SwingConstants.TOP);
		panel.add(hourlyLabel);
		panel.add(hourly);
		panel.add(hoursWorkedLabel);
		panel.add(hoursWorked);
		panel.add(commuteTimeLabel);
		panel.add(commuteTime);
		panel.add(commuteDistanceLabel);
		panel.add(commuteDistance);
		panel.add(fuelEfficiencyLabel);
		panel.add(fuelEfficiency);
		panel.add(gasCostLabel);
		panel.add(gasCost);
		panel.add(calculate);
		panel.add(result);
		frame.pack();
	}
	
	public double calculateFuelCostRoundTrip() {
		double dist = Double.parseDouble(commuteDistance.getText());
		double eff = Double.parseDouble(fuelEfficiency.getText());
		double gas = Double.parseDouble(gasCost.getText());
		double cost = (dist/eff)*gas;
		return cost*2;
	}
	
	public double calculateHourlyWithTravelTime(double hours, double rate, double comm) {
		double total = hours/(hours + ((comm*2)/60));
		return total*rate;
	}
	
	public double calculateTotalHourlyRate(double hours, double rate, double comm) {
		double totalPay = hours*rate;
		totalPay -= calculateFuelCostRoundTrip();
		double modifiedHours = (hours + ((comm*2)/60));
		return totalPay/modifiedHours;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		double hours = Double.parseDouble(hoursWorked.getText());
		double rate = Double.parseDouble(hourly.getText());
		double comm = Double.parseDouble(commuteTime.getText());
		double totalPay = hours*rate;
		
		double fCost = calculateFuelCostRoundTrip();
		double hRate = calculateHourlyWithTravelTime(hours, rate, comm);
		double tHourlyRate = calculateTotalHourlyRate(hours, rate, comm);
		
		String labelText = "<html>" + "Total pay: $" + format(totalPay) + "<br>" 
				+ "Fuel Cost: $" + format(fCost) + "<br>" 
				+ "Net pay: $" + format(totalPay-fCost) + "<br>" 
				+ "Hourly With Travel: $" + format(hRate) + "/hour<br>"
				+ "Total Hourly: $" + format(tHourlyRate) + "/hour<br>";
		result.setText(labelText);
	}
	
	public String format(double n) {
		return String.format("%.2f", n);
	}
}
