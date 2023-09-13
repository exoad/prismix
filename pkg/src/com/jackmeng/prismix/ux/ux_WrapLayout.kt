// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix.ux

import javax.swing.JScrollPane
import javax.swing.SwingUtilities
import java.awt.Container
import java.awt.Dimension
import java.awt.FlowLayout

class ux_WrapLayout:FlowLayout
{
	constructor():super()
	
	constructor(align:Int):super(align)
	
	constructor(align:Int , hgap:Int , vgap:Int):super(align , hgap , vgap)
	
	override fun preferredLayoutSize(target:Container):Dimension
	{
		return layoutSize(target , true)
	}
	
	override fun minimumLayoutSize(target:Container):Dimension
	{
		val minimum=layoutSize(target , false)
		minimum.width-=hgap+1
		return minimum
	}
	
	private fun layoutSize(target:Container , preferred:Boolean):Dimension
	{
		synchronized(target.treeLock) {
			var targetWidth=target.size.width
			var container=target
			while (container.size.width==0&&container.parent!=null) container=container.parent
			targetWidth=container.size.width
			if (targetWidth==0) targetWidth=Int.MAX_VALUE
			val hgap=hgap
			val vgap=vgap
			val insets=target.insets
			val horizontalInsetsAndGap=insets.left+insets.right+hgap*2
			val maxWidth=targetWidth-horizontalInsetsAndGap
			val dim=Dimension(0 , 0)
			var rowWidth=0
			var rowHeight=0
			val nmembers=target.componentCount
			for (i in 0 until nmembers)
			{
				val m=target.getComponent(i)
				if (m.isVisible)
				{
					val d=if (preferred) m.preferredSize else m.minimumSize
					if (rowWidth+d.width>maxWidth)
					{
						addRow(dim , rowWidth , rowHeight)
						rowWidth=0
						rowHeight=0
					}
					if (rowWidth!=0) rowWidth+=hgap
					rowWidth+=d.width
					rowHeight=rowHeight.coerceAtLeast(d.height)
				}
			}
			addRow(dim , rowWidth , rowHeight)
			dim.width+=horizontalInsetsAndGap
			dim.height+=insets.top+insets.bottom+vgap*2
			val scrollPane=SwingUtilities.getAncestorOfClass(JScrollPane::class.java , target)
			if (scrollPane!=null&&target.isValid) dim.width-=hgap+1
			return dim
		}
	}
	
	private fun addRow(dim:Dimension , rowWidth:Int , rowHeight:Int)
	{
		dim.width=dim.width.coerceAtLeast(rowWidth)
		if (dim.height>0) dim.height+=vgap
		dim.height+=rowHeight
	}
}