package com.jackmeng.prismix.ux;

import static com.jackmeng.prismix.jm_Prismix.log;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import com.jackmeng.prismix._1const;
import com.jackmeng.prismix.use_Maker;
import com.jackmeng.prismix.stl.extend_stl_Colors;
import com.jackmeng.stl.stl_Struct.struct_Pair;

public final class ui_CPick_GenericDisp
		extends ui_CPick
{

	public ui_CPick_GenericDisp()
	{
		this.setFocusable(true);
	}

	@Override public void paintComponent(final Graphics g)
	{
		super.paintComponent(g);
		use_Maker.db_timed(() -> {
			final Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
			g2.drawImage(
					extend_stl_Colors.cpick_gradient2(this.getParent().getPreferredSize().height,
							_1const.last_color()),
					null, 0,
					0);
			g2.dispose();
		});
	}

	@Override public Void call(final struct_Pair< Color, Boolean > arg0)
	{
		if (this.isVisible() || this.isFocusOwner())
		{
			log("CPickDB", hashCode() + " Picks up a valid color call from the color queue!");
			this.repaint(75L);
		}
		return (Void) null;
	}

}