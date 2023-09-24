package com.jackmeng.prismix.ux

import com.jackmeng.prismix.use_Maker
import com.jackmeng.prismix.stl.extend_stl_Colors
import com.jackmeng.prismix._1const
import com.jackmeng.stl.stl_Struct.struct_Pair
import com.jackmeng.prismix.jm_Prismix
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints

class ui_CPick_GenericDisp:ui_CPick()
{
	init
	{
		isFocusable=true
	}
	
	public override fun paintComponent(g:Graphics)
	{
		super.paintComponent(g)
		use_Maker.db_timed {
			val g2=g as Graphics2D
			g2.setRenderingHint(RenderingHints.KEY_RENDERING , RenderingHints.VALUE_RENDER_SPEED)
			g2.drawImage(
				extend_stl_Colors.cpick_gradient2(parent.preferredSize.height , _1const.last_color()) ,
				null ,
				0 ,
				0
			)
		}
	}
	
	override fun call(p0:struct_Pair<Color? , Boolean?>?):Void?
	{
		if (isVisible||isFocusOwner)
		{
			jm_Prismix.log("CPickDB" , hashCode().toString()+" Picks up a valid color call from the color queue!")
			repaint(75L)
		}
		return null
	}
}