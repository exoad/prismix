// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix.ux

import javax.swing.JLabel
import java.awt.*

/**
 * Color label
 */
class ui_R_ColorLabel(text:String? , private val cornerRadius:Int , private var borderColor:Color , private var borderThickness:Int):
		JLabel(text)
{
	fun borderColor(r:Color)
	{
		borderColor=r
		repaint(70L)
	}
	
	fun borderColor():Color
	{
		return borderColor
	}
	
	fun borderThickness(i:Int)
	{
		borderThickness=i
		repaint(70L)
	}
	
	fun borderThickness():Int
	{
		return borderThickness
	}
	
	public override fun paintComponent(g:Graphics)
	{
		val g2d=g as Graphics2D
		val dimension=size
		val width=dimension.width
		val height=dimension.height
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON)
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION , RenderingHints.VALUE_INTERPOLATION_BILINEAR)
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING , RenderingHints.VALUE_COLOR_RENDER_QUALITY)
		/*---------------------------------------------------------------------------------------------------- /
        / g2d.setClip(new RoundRectangle2D.Double(0, 0, width - 1D, height - 1D, cornerRadius, cornerRadius)); /
        /-----------------------------------------------------------------------------------------------------*/
		g2d.color=background
		g2d.fillRoundRect(
			borderThickness ,
			borderThickness ,
			width-borderThickness-2 ,
			height-borderThickness-2 ,
			cornerRadius ,
			cornerRadius
		)
		if (borderThickness>0)
		{
			g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL , RenderingHints.VALUE_STROKE_PURE)
			g2d.color=borderColor
			g2d.stroke=BasicStroke(borderThickness.toFloat())
			g2d.drawRoundRect(
				borderThickness ,
				borderThickness ,
				width-borderThickness-2 ,
				height-borderThickness-2 ,
				cornerRadius ,
				cornerRadius
			)
		} // super.paintComponent(g2d);
		g2d.dispose()
	}
}