// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix.ux

import kotlin.jvm.JvmOverloads
import com.jackmeng.prismix.ux.ui_Whoops
import javax.swing.JComponent
import kotlin.math.ceil
import kotlin.math.sqrt
import java.awt.*

class ui_Whoops @JvmOverloads constructor(
		stripePrimary:Color=Color.yellow , stripeSecondary:Color?=Color.black , stripeSpacing:Int=4 , stripeStroke:Int=4 , normalizeAA:Boolean=true
):JComponent()
{
	// quick credit, thanks to this post for some of the help
	// :https://stackoverflow.com/a/7158387/14501343 ^w^
	private val stripeStroke:Int
	private val stripeSpacing:Int
	private val normalizeAA:Boolean
	
	init  // btw a quick trick here was to just stripePrimary lol
	{
		isOpaque=stripeSecondary!=null
		if (stripeSecondary!=null) background=stripeSecondary
		foreground=stripePrimary
		preferredSize=Dimension(40 , 40)
		this.stripeSpacing=stripeSpacing
		this.stripeStroke=stripeStroke
		this.normalizeAA=normalizeAA
	}
	
	constructor(stripeSpacing:Int , stripeStroke:Int):this(
		Color.yellow ,
		Color.black ,
		stripeSpacing ,
		stripeStroke ,
		true
	)
	
	fun with_size(r:Dimension?):ui_Whoops
	{
		size=r
		repaint(70L)
		return this
	}
	
	fun of(r:JComponent):ui_Whoops
	{
		with_size(r.preferredSize)
		return this
	}
	
	override fun paintComponent(g:Graphics)
	{
		super.paintComponent(g)
		val g2=g as Graphics2D
		if (normalizeAA)
		{
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON)
			g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL , RenderingHints.VALUE_STROKE_NORMALIZE)
		}
		if (background!=null)
		{
			val r=Rectangle(0 , 0 , width , height)
			g2.color=background
			g2.fill(r)
			g2.clip=r
		}
		g2.color=foreground
		g2.stroke=BasicStroke(stripeStroke.toFloat())
		val len=ceil(sqrt(width*width+height.toDouble()*height)).toInt()
		var i=-len
		while (i<width)
		{
			g2.drawLine(i , 0 , i+len , len)
			i+=stripeSpacing
		}
		g2.dispose()
	}
}