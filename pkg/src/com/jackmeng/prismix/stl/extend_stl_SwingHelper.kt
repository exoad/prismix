// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix.stl

import com.jackmeng.prismix.ux.ui_LazyViewport
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.ScrollPaneConstants
import java.awt.BorderLayout
import java.awt.Dimension

object extend_stl_SwingHelper
{
	fun wrap_SideToSide1(left:JComponent , right:JComponent):JPanel
	{
		val temp=JPanel()
		temp.preferredSize=Dimension(
			left.preferredSize.width+right.preferredSize.width ,
			left.preferredSize.height.coerceAtLeast(right.preferredSize.height)
		)
		temp.layout=BorderLayout()
		temp.add(left , BorderLayout.WEST)
		temp.add(right , BorderLayout.EAST)
		return temp
	}
	
	fun wrap_AsScrollable_Lazy(e:JComponent?):JScrollPane
	{
		val r=JScrollPane()
		val viewport=ui_LazyViewport()
		r.verticalScrollBarPolicy=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
		r.horizontalScrollBarPolicy=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
		viewport.view=e
		r.viewport=viewport
		return r
	}
}