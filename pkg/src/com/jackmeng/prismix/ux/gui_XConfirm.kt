// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix.ux

import com.jackmeng.prismix.ux.ui_LazyViewport.Companion.make
import com.jackmeng.stl.stl_Colors
import javax.swing.*
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.GridLayout
import java.awt.event.ActionEvent

class gui_XConfirm(content:String? , title:String? , onNo:Runnable , onYes:Runnable , yesString:String? , noString:String?):
		gui(title)
{
	init
	{
		preferredSize=Dimension(500 , 250)
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
		val no_btn=JButton(noString)
		no_btn.background=stl_Colors.hexToRGB(ux_Theme.get()["rose"])
		no_btn.foreground=Color.black
		no_btn.isRolloverEnabled=false
		no_btn.isFocusPainted=false
		no_btn.isBorderPainted=false
		no_btn.addActionListener {
			onNo.run()
			dispose()
		}
		val yes_btn=JButton(yesString)
		yes_btn.background=stl_Colors.hexToRGB(ux_Theme.get()["rose"])
		yes_btn.foreground=Color.black
		yes_btn.isRolloverEnabled=false
		yes_btn.isFocusPainted=false
		yes_btn.isBorderPainted=false
		yes_btn.addActionListener {
			onYes.run()
			dispose()
		}
		val wrap=JPanel()
		wrap.layout=GridLayout(1 , 2)
		wrap.isOpaque=false
		wrap.add(no_btn)
		wrap.add(yes_btn)
		contentPane.background=Color.black
		contentPane.layout=BoxLayout(contentPane , BoxLayout.Y_AXIS)
		contentPane.add(pane)
		contentPane.add(wrap)
	}
}