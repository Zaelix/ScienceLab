package pathfinding;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickListener implements MouseListener{

	@Override
	public void mouseClicked(MouseEvent e) {
		int[] pos = Grid.convertToGridPosition(e.getX(), e.getY());
		Node clicked = Grid.getNode(pos[0], pos[1]);
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (clicked.color != Color.RED)
				clicked.setColor(Color.RED);
			else
				clicked.setColor(Color.LIGHT_GRAY);
		}
		if (e.getButton() == MouseEvent.BUTTON3 && e.isShiftDown()) {
			clicked.setColor(Color.GREEN);
			Grid.start = clicked;
		}
		if (e.getButton() == MouseEvent.BUTTON3 && !e.isShiftDown()) {
			clicked.setColor(Color.BLUE);
			Grid.goal = clicked;
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
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
