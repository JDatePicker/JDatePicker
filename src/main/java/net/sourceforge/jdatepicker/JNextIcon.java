package net.sourceforge.jdatepicker;
import java.awt.Component;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.Icon;

public class JNextIcon implements Icon {
	
	protected int width;
	protected int height;

	protected int[] xPoints = new int[3];
	protected int[] yPoints = new int[3];

	public JNextIcon(int width, int height) {
		setDimension(width, height);
	}

	public int getIconWidth() {
		return width;
	}

	public int getIconHeight() {
		return height;
	}

	public void setDimension(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		xPoints[0] = x + width;
		yPoints[0] = y + (height / 2);
		
		xPoints[1] = x;
		yPoints[1] = y - 1;
		
		xPoints[2] = x;
		yPoints[2] = y + height;
		
		g.setColor(Color.BLACK);
		g.fillPolygon(xPoints, yPoints, 3);
	}
}
