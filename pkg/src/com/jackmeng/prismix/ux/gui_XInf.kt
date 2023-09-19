// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix.ux

import com.jackmeng.prismix.ux.ui_LazyViewport.Companion.make
import com.jackmeng.stl.stl_Colors
import com.jackmeng.prismix._1const
import com.jackmeng.prismix.jm_Prismix
import javax.swing.*
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.GridLayout
import java.awt.event.ActionEvent

class gui_XInf private constructor(content:String? , title:String):gui(title)
{
	var r:Color=stl_Colors.hexToRGB(ux_Theme.get()["blue"])
	
	init
	{
		preferredSize=Dimension(710 , 420) // 420 funny numero
		isAlwaysOnTop=true
		defaultCloseOperation=DISPOSE_ON_CLOSE
		val pane=JPanel()
		pane.border=BorderFactory.createEmptyBorder()
		pane.preferredSize=Dimension(0 , 350)
		pane.layout=BorderLayout()
		val jep=JEditorPane()
		jep.contentType="text/html"
		jep.editorKit=ux_HTMLPRE()
		jep.text=content
		jep.isOpaque=true
		jep.isEditable=false
		jep.border=BorderFactory.createEmptyBorder(7 , 7 , 7 , 7)
		jep.background=Color.black
		jep.foreground=Color.white
		val jsp=JScrollPane()
		jsp.verticalScrollBar.background=Color.black
		jsp.verticalScrollBar.unitIncrement=6
		jsp.horizontalScrollBar.background=Color.black
		jsp.horizontalScrollBar.unitIncrement=6
		jsp.isOpaque=true
		jsp.background=Color.black
		jsp.setViewportView(make(jep))
		jsp.border=BorderFactory.createEmptyBorder()
		pane.add(jsp , BorderLayout.CENTER)
		val closeState_btn=JButton("OK")
		closeState_btn.background=stl_Colors.hexToRGB(ux_Theme.get()["cyan"])
		closeState_btn.foreground=Color.black
		closeState_btn.isRolloverEnabled=false
		closeState_btn.isFocusPainted=false
		closeState_btn.isBorderPainted=false
		closeState_btn.addActionListener { ev:ActionEvent?-> dispose() }
		val wrap=JPanel()
		wrap.layout=GridLayout(1 , 1)
		wrap.isOpaque=false
		wrap.add(closeState_btn)
		contentPane.background=Color.black
		contentPane.layout=BoxLayout(contentPane , BoxLayout.Y_AXIS)
		contentPane.add(pane)
		contentPane.add(wrap)
	}
	
	companion object
	{
		@JvmStatic
		operator fun invoke(content:String? , title:String)
		{
			if (java.lang.Boolean.FALSE ==_1const.`val`.parse("shush_info_dialogs").get()) force_invoke(content , title)
		}
		
		@JvmStatic
		fun force_invoke(content:String? , title:String)
		{
			jm_Prismix.log("INFW" , "Invoking a new window: $title")
			SwingUtilities.invokeLater(gui_XInf(content , title))
		}
	}
}