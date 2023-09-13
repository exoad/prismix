// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix.ux

import javax.swing.JComponent
import javax.swing.SwingContainer
import lombok.Getter
import java.awt.*

@SwingContainer
class ui_Line(@field:Getter private val lineColor:Color , @field:Getter private val edgeOffset:Int , @field:Getter private val normalizeAA:Boolean , @field:Getter private val thickness:Int):
		JComponent()
{
	override fun paintComponent(g:Graphics)
	{
		super.paintComponent(g)
		val g2=g as Graphics2D
		if (normalizeAA)
		{
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON)
			g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL , RenderingHints.VALUE_STROKE_NORMALIZE)
		}
		g2.color=lineColor
		g2.stroke=BasicStroke(thickness.toFloat())
		g2.drawLine(edgeOffset , height/2 , width-edgeOffset , height/2)
		g2.dispose()
	}
}