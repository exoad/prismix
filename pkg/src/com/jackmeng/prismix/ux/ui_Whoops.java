// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import lombok.NonNull;

public final class ui_Whoops
		extends JComponent
{ // quick credit, thanks to this post for some of the help
	// :https://stackoverflow.com/a/7158387/14501343 ^w^

	private final int stripeStroke, stripeSpacing;
	private final boolean normalizeAA;

	public ui_Whoops(@NonNull Color stripePrimary, Color stripeSecondary, int stripeSpacing, int stripeStroke,
			boolean normalizeAA)
	// btw a quick trick here was to just stripePrimary lol
	{
		setOpaque(stripeSecondary != null);
		if (stripeSecondary != null)
			setBackground(stripeSecondary);
		setForeground(stripePrimary);
		setPreferredSize(new Dimension(40, 40));
		this.stripeSpacing = stripeSpacing;
		this.stripeStroke = stripeStroke;
		this.normalizeAA = normalizeAA;
	}

	public ui_Whoops()
	{
		this(Color.yellow, Color.black, 4, 4, true);
	}

	public ui_Whoops(int stripeSpacing, int stripeStroke)
	{
		this(Color.yellow, Color.black, stripeSpacing, stripeStroke, true);
	}

	public ui_Whoops with_size(Dimension r)
	{
		setSize(r);
		repaint(70L);
		return this;
	}

	public ui_Whoops of(JComponent r)
	{
		with_size(r.getPreferredSize());
		return this;
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

		if (getBackground() != null)
		{
			Rectangle r = new Rectangle(0, 0, getWidth(), getHeight());
			g2.setColor(getBackground());
			g2.fill(r);
			g2.setClip(r);
		}
		g2.setColor(getForeground());
		g2.setStroke(new BasicStroke(stripeStroke));
		int len = (int) Math.ceil(Math.sqrt(getWidth() * getWidth() + (double) getHeight() * getHeight()));
		for (int i = -len; i < getWidth(); i += stripeSpacing)
			g2.drawLine(i, 0, i + len, len);
		g2.dispose();
	}
}