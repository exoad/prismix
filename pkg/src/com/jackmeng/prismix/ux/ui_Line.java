// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;
import javax.swing.SwingContainer;

@SwingContainer public final class ui_Line
		extends JComponent
{
	private final Color lineColor;
	private final int edgeOffset, thickness;
	private final boolean normalizeAA;

	public ui_Line(Color lineColor, int edgeOffset, boolean normalizeAA, int thickness)
	{
		this.lineColor = lineColor;
		this.edgeOffset = edgeOffset;
		this.thickness = thickness;
		this.normalizeAA = normalizeAA;

	}

	@Override protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (normalizeAA)
		{
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
		}
		g2.setColor(lineColor);
		g2.setStroke(new BasicStroke(thickness));
		g2.drawLine(edgeOffset, getHeight() / 2, getWidth() - edgeOffset, getHeight() / 2);
		g2.dispose();
	}

}