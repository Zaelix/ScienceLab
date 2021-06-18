import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Labels {
	JFrame f = new JFrame();
	JPanel p = new JPanel();
	JLabel l = new JLabel();
	JLabel l2 = new JLabel();
	public static void main(String[] args) {
		Labels ls = new Labels();
		ls.setup();
	}
	
	void setup() {
		f.setVisible(true);
	}
}
