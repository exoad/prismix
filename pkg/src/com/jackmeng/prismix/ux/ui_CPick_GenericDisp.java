package com.jackmeng.prismix.ux;

import com.jackmeng.prismix._1const;
import com.jackmeng.prismix.jm_Prismix;
import com.jackmeng.prismix.stl.extend_stl_Colors;
import com.jackmeng.prismix.use_Maker;
import com.jackmeng.stl.stl_Struct;

import java.awt.*;

public class ui_CPick_GenericDisp
	extends ui_CPick
{
	public ui_CPick_GenericDisp()
	{
		setFocusable(true);
	}

	@Override public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		use_Maker.db_timed(() -> {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
			g2.drawImage(extend_stl_Colors.cpick_gradient2(getParent().getPreferredSize().height, _1const.last_color()), null, 0, 0);
		});
	}

	@Override public Void call(stl_Struct.struct_Pair<Color, Boolean> colorBooleanstruct_pair)
	{
		if(isVisible() || isFocusOwner())
		{
			jm_Prismix.log("CPickDB" , hashCode() +" Picks up a valid color call from the color queue!");
			repaint(75L);
		}
		return null;
	}
}
