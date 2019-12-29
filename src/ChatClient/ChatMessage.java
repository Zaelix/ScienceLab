package ChatClient;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ChatMessage extends JPanel {
	String user;
	String messsage;
	Color color;
	int lines = 0;
	int pixelHeight = 0;
	JLabel label;

	public ChatMessage(String string) {
		super(new GridLayout());
		label = new JLabel(string, SwingConstants.LEFT);
	}

	public void init() {
		setSize(490, 5 + 17 * lines);
		label.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(label);
		// setPreferredSize(new Dimension(490, 5+17*lines));
		pixelHeight = 5 + 17 * lines;
	}

	public void setLines(int lines) {
		this.lines = lines;
	}

	public int getLines() {
		return lines;
	}
}
