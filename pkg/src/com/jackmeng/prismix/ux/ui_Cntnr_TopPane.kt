package com.jackmeng.prismix.ux

import com.jackmeng.prismix._1const
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.JTabbedPane
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension

class ui_Cntnr_TopPane:JPanel()
{
	fun exports():Array<JPanel>
	{
		return arrayOf()
	}
	
	// section where it cannot be
	// exported and thus let ux
	// change its visibility at any
	// time!
	var colorChooser // this is the left side of the panel where the color gradient is
			:JTabbedPane
	
	/*----------------------------- /
	/ final JPanel attributes_List; /
	/------------------------------*/
	init
	{
		this.preferredSize=Dimension(_2const.WIDTH , _2const.HEIGHT/2)
		this.minimumSize=this.preferredSize
		/*---------------------------------------------------------------- /
		/ this.setDividerLocation(_2const.WIDTH / 2 + _2const.WIDTH / 13); /
		/ this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);                /
		/-----------------------------------------------------------------*/
		border=BorderFactory.createEmptyBorder()
		colorChooser=JTabbedPane()
		/*------------------------------------------------------------------------------------------------------------- /
		/ this.colorChooser.setPreferredSize(new Dimension(this.getDividerLocation(), this.getPreferredSize().height)); /
		/--------------------------------------------------------------------------------------------------------------*/
		val colorChooser_Shades=ui_CPick_SugList()
		_1const.COLOR_ENQ.add(colorChooser_Shades)
		colorChooser.addTab("Generator" , colorChooser_Shades)
		val colorChooser_gradientRect=
			ui_CPick_GradRect() // RGBA																																									// Gradient
		// Rectangle
		_1const.COLOR_ENQ.add(colorChooser_gradientRect)
		colorChooser.addTab("GradRect Pick" , colorChooser_gradientRect)
		if ("true".equals(_1const.`val`.get_value("soft_debug") , ignoreCase=true))
		{
			val colorChooser_Debug=ui_CPick_GenericDisp()
			colorChooser_Debug.border=BorderFactory.createLineBorder(Color.MAGENTA , 1)
			_1const.COLOR_ENQ.add(colorChooser_Debug)
			colorChooser.addTab("!Debug" , colorChooser_Debug)
		}
		layout=BorderLayout()
		add(colorChooser , BorderLayout.CENTER)
	}
	
	@Synchronized
	fun redo()
	{
		this.minimumSize=Dimension(this.preferredSize.width , this.preferredSize.height+65)
		doLayout()
		revalidate()
		this.repaint(100L)
	}
}