// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import javax.swing.*;

import com.jackmeng.prismix._1const;
import com.jackmeng.prismix.ux.model.stx_Bar;
import com.jackmeng.prismix.ux.model.stx_BarConfig;

import java.awt.*;
import java.util.List;

import static com.jackmeng.prismix.jm_Prismix.*;

public final class ui_UnsignedBarG extends JComponent
{

	private List< stx_Bar > bars;
	private String title;
	private stx_BarConfig config;

	public ui_UnsignedBarG(String title, stx_BarConfig config, stx_Bar... bars)
	{
		this.title = title;
		this.config = config;
		this.bars = List.of(bars);
	}

	@Override protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
		g2d.setColor(config.axisColors());
		g2d.setStroke(new BasicStroke(2));
		int fontHeight = g2d.getFontMetrics().getHeight() * 2;
		// draw x-axis
		g2d.drawLine(config.totalInset(), getHeight() - fontHeight - config.totalInset(),
				getWidth() - config.totalInset(), getHeight() - fontHeight - config.totalInset());

		// draw title
		g2d.setColor(config.textColors());
		g2d.setFont(_1const._Mono_().deriveFont(10.5F));
		int y_title = config.totalInset() + g2d.getFontMetrics().getHeight();
		for (char r : title.toCharArray())
		{
			g2d.drawString(String.valueOf(r), getWidth() - config.totalInset() - g2d.getFontMetrics().charWidth(r), y_title);
			y_title += g2d.getFontMetrics().getHeight() + 4;
		}

		// draw ticks
		g2d.setStroke(new BasicStroke(1));
		int maxLines = config.lines();
		int range = getHeight() - fontHeight - (2 * config.totalInset());

		int value = config.max() / maxLines;
		int j = 0;

		for (int i = 0; i <= maxLines; i++)
		{
			int labelY = getHeight() - fontHeight - (i * (range / maxLines)) - config.totalInset();
			if (i == maxLines)
				g2d.drawString(String.valueOf(config.max()), config.totalInset(), labelY + 4);
			else
				g2d.drawString(String.valueOf(j), config.totalInset(), labelY);
			g2d.drawLine(config.totalInset() + 20, labelY, getWidth() - config.totalInset() - 20, labelY);
			j += value;
		}

		// draw bars

		g2d.dispose();

	}

	public static void main(String[] args)
	{
		stx_Bar[] bars = {
				new stx_Bar("Bar 1", 50, Color.RED),
				new stx_Bar("Bar 2", 80, Color.BLUE),
				new stx_Bar("Bar 3", 30, Color.GREEN),
				new stx_Bar("Bar 4", 60, Color.ORANGE)
		};

		ui_UnsignedBarG barGraph = new ui_UnsignedBarG("Bar Graph",
				new stx_BarConfig(10, 50, 15, 100, 10, Color.gray, Color.gray),
				bars);

		JFrame frame = new JFrame("Test:BarGraphs");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(500, 400);
		frame.getContentPane().add(barGraph);
		frame.setVisible(true);
	}
}
