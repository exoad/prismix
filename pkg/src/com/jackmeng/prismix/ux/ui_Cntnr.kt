// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix.ux

import javax.swing.BorderFactory
import javax.swing.JComponent
import javax.swing.JSplitPane
import java.awt.Dimension

/**
 * Represents the inner shell of the content of the GUI. The parent is the ux
 * handler.
 *
 *
 * Most code for the GUI goes here.
 *
 * @author Jack Meng
 */
class ui_Cntnr:JSplitPane()
{
	@JvmField
	var top:ui_Cntnr_TopPane=ui_Cntnr_TopPane()
	
	@JvmField
	var bottom:ui_Cntnr_BottomPane=ui_Cntnr_BottomPane()
	
	init
	{
		this.preferredSize=Dimension(_2const.WIDTH , _2const.HEIGHT)
		setOrientation(VERTICAL_SPLIT)
		dividerLocation=_2const.HEIGHT/2+_2const.HEIGHT/8
		this.topComponent=top
		this.bottomComponent=bottom
		border=BorderFactory.createEmptyBorder()
	}
	
	@Synchronized
	fun append_attribute(e:JComponent)
	{
		e.alignmentX=CENTER_ALIGNMENT
		e.alignmentY=LEFT_ALIGNMENT
	/*------------------------------------ /
    / this.top.attributes_List.add(e);     /
    / this.top.attributes_List.validate(); /
    /-------------------------------------*/
	}
	
	fun validate_size()
	{
		validate()
	/*------------------------------------------------------------------------------------- /
    / this.top.colorAttributes.setMinimumSize(this.top.colorAttributes.getPreferredSize()); /
    /--------------------------------------------------------------------------------------*//*--------------------------------------------------------------------------------- /
    / this.top.setDividerLocation(this.top.colorChooser.getPreferredSize().width + 50); /
    /----------------------------------------------------------------------------------*/
	}
}