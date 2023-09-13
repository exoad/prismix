// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.AbstractButton;
import javax.swing.DefaultButtonModel;

import com.jackmeng.stl.stl_Listener;
import com.jackmeng.stl.stl_ListenerPool;

import lombok.Getter;
import lombok.NonNull;

public class ui_FatSlider
		extends AbstractButton
{
	@Getter private final String trueString;
	@Getter private final String falseString;
	@Getter private final Color trueColor, falseColor, thumbColor;
	@Getter private final int borderArc, thumbWidth, shadowDrawDist, thumbBumpPad;
private final transient stl_ListenerPool< Boolean > listenerPool;

	public ui_FatSlider(@NonNull String trueString, @NonNull String falseString, @NonNull Color trueColor,
			@NonNull Color falseColor, @NonNull Color thumbColor,
			boolean initVal, int borderArc, int thumbWidth, int shadowDrawDist, int thumbBumpPad) // thumb
																																														// is on
																																														// right
																																														// side
	// -> TRUE else FALSE
	{
		this.trueColor = trueColor;
		this.trueString = trueString;
		this.falseColor = falseColor;
		this.falseString = falseString;
		this.thumbColor = thumbColor;
		this.borderArc = borderArc;
		this.thumbWidth = thumbWidth;
		this.shadowDrawDist = shadowDrawDist;
		this.thumbBumpPad = thumbBumpPad;
		listenerPool = new stl_ListenerPool<>("com.jackmeng.ui_FatSlider#" + hashCode());
		setPreferredSize(new Dimension(70, 40));
		setModel(new DefaultButtonModel());
		setSelected(initVal);
		addMouseListener(new MouseAdapter() {
			@Override public void mouseReleased(MouseEvent e)
			{
				if (new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), getHeight(), getHeight())
						.contains(e.getPoint()))
					setSelected(!isSelected());
			}
		});

		setPreferredSize(new Dimension(80, 40));
	}

	public void add_listener(stl_Listener< Boolean > listener)
	{
		listenerPool.add(listener);
	}

	public void rmf_listener(stl_Listener< Boolean > listener)
	{
		listenerPool.rmf(listener);
	}

	@Override public void setSelected(boolean b)
	{
		listenerPool.dispatch(b);
		setToolTipText(b ? trueString : falseString);
		super.setSelected(b);
	}

	@Override public void paintComponent(Graphics gr)
	{
		Graphics2D g = (Graphics2D) gr;
		g.clearRect(0, 0, getWidth(), getHeight());
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		g.setColor(isSelected() ? trueColor : falseColor);
		g.fillRoundRect(0, 0, getWidth(), getHeight(), borderArc, borderArc);
		/*------------------------------------------------------------------------------------------------------------- /
		/ g.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight() + 2, getHeight() + 2);                             /
		/                                                                                                               /
		/ int thumbW = getHeight() - 4;                                                                                 /
		/                                                                                                               /
		/ g.setColor(thumbColor);                                                                                       /
		/ int thumbX = isSelected() ? getWidth() - thumbW + 2 : 2;                                                      /
		/ g.fillOval(thumbX, getHeight() % 2 == 0 ? 2 : 3, thumbW - 4, getHeight() - (getHeight() % 2 == 0 ? 4 : 5));   /
		/ System.out.println(thumbX + " ," + (getHeight() % 2 == 0 ? 2 : 3) + " ," + (thumbW - 2) + " ," + (getHeight() /
		/     - (getHeight() % 2 == 0 ? 4 : 5)));                                                                       /
		/--------------------------------------------------------------------------------------------------------------*/

		AlphaComposite shadowComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3F);

		Composite originalComposite = g.getComposite();
		g.setComposite(shadowComposite);

		int height = getHeight() % 2 == 0 ? 2 : 3, end_height = getHeight() - (getHeight() % 2 == 0 ? 5 : 4);
		if (isSelected()) // right shadow
		{
			g.setColor(Color.BLACK);
			g.fillRoundRect(getWidth() - thumbWidth - thumbBumpPad - shadowDrawDist, height, thumbWidth + shadowDrawDist,
					end_height, borderArc, borderArc);
		}
		else
		{
			g.setColor(Color.BLACK);
			g.fillRoundRect(thumbBumpPad, height, thumbWidth + shadowDrawDist, end_height,
					borderArc, borderArc);

		}
		g.setComposite(originalComposite);

		g.setColor(thumbColor);
		g.fillRoundRect(isSelected() ? getWidth() - thumbWidth - thumbBumpPad : thumbBumpPad, height, thumbWidth,
				end_height, borderArc,
				borderArc);

		/*--------------------------------------------------------------------------------------------------------------- /
		/ g.setStroke(new BasicStroke(2, 2, 0));                                                                          /
		/                                                                                                                 /
		/ if (isSelected())                                                                                               /
		/ {                                                                                                               /
		/   g.setColor(extend_stl_Colors                                                                                  /
		/       .awt_remake(extend_stl_Colors.binary_fg_decider(extend_stl_Colors.awt_strip_rgba(trueColor))));           /
		/   g.drawLine((2 + (getWidth() - thumbWidth - thumbBumpPad - shadowDrawDist)) / 2, getHeight() % 2 == 0 ? 6 : 7, /
		/       (2 + (getWidth() - thumbWidth - thumbBumpPad - shadowDrawDist)) / 2,                                      /
		/       getHeight() - (getHeight() % 2 == 0 ? 8 : 9));                                                            /
		/ }                                                                                                               /
		/ else                                                                                                            /
		/ {                                                                                                               /
		/   g.setColor(Color.pink);                                                                                       /
		/   g.drawOval((2 + thumbWidth + shadowDrawDist) / 2, getHeight() % 2 == 0 ? 6 : 7,                               /
		/       (2 + thumbWidth + shadowDrawDist) / 2,                                                                    /
		/       getHeight() - (getHeight() % 2 == 0 ? 8 : 9));                                                            /
		/ }                                                                                                               /
		/----------------------------------------------------------------------------------------------------------------*/

		g.dispose();
	}
}