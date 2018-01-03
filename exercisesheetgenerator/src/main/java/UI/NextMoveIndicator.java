package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;

public class NextMoveIndicator extends JLabel {
	private int nextMove;

	public NextMoveIndicator(int nextMove) {
		this.nextMove = nextMove;
		setOpaque(true);
		setBackground(Color.WHITE);
	}

	public Dimension getPreferredSize() {
		return new Dimension(20, 20);
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Dimension size = getSize();
		double edgeLength = Math.min(size.getHeight(), size.getWidth());

		if (nextMove == 1) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(Color.BLACK);
		}

		g.fillOval(0, 0, (int) edgeLength, (int) edgeLength);
	}
}
