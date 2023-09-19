// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix.ux

import com.jackmeng.prismix.ux.ui_LazyViewport.Companion.make
import com.jackmeng.stl.stl_Colors
import com.jackmeng.stl.stl_Wrap
import com.jackmeng.prismix._1const
import com.jackmeng.prismix.jm_Prismix
import com.jackmeng.ansicolors.jm_Ansi
import javax.swing.*
import kotlin.system.exitProcess
import java.awt.*
import java.awt.datatransfer.StringSelection
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.lang.Boolean
import java.lang.Exception
import java.lang.UnsupportedOperationException
import java.net.URI
import java.util.*

class gui_XErr private constructor(content:String , title:String , url:String , closeState:Err_CloseState):gui(title)
{
	enum class Err_CloseState
	{
		EXIT , OK
	}
	
	var r=stl_Colors.hexToRGB(ux_Theme.get()["rose"])
	
	@Transient
	var touched=stl_Wrap(false)
	
	init
	{
		preferredSize=Dimension(710 , 420)
		isAlwaysOnTop=Boolean.TRUE==_1const.`val`.parse("pin_error_dialogs").get()
		defaultCloseOperation=DISPOSE_ON_CLOSE
		defaultCloseOperation=if (closeState==Err_CloseState.EXIT) EXIT_ON_CLOSE else DISPOSE_ON_CLOSE
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
		jsp.horizontalScrollBar.background=Color.black
		jsp.verticalScrollBar.unitIncrement=8
		jsp.isOpaque=true
		jsp.setViewportView(make(jep))
		jsp.border=BorderFactory.createEmptyBorder()
		pane.add(jsp , BorderLayout.CENTER)
		val closeState_btn=JButton(closeState.name)
		closeState_btn.background=stl_Colors.hexToRGB(ux_Theme.get()["mint"])
		closeState_btn.foreground=Color.black
		closeState_btn.isRolloverEnabled=false
		closeState_btn.isFocusPainted=false
		closeState_btn.isBorderPainted=false
		closeState_btn.addActionListener(if (closeState==Err_CloseState.EXIT) ActionListener {
			exitProcess(
				1
			)
		}
		else ActionListener { })
		val url_btn=JButton("Help")
		url_btn.background=stl_Colors.hexToRGB(ux_Theme.get()["orange"])
		url_btn.foreground=Color.black
		url_btn.isRolloverEnabled=false
		url_btn.isFocusPainted=false
		url_btn.isBorderPainted=false
		url_btn.addActionListener { ev:ActionEvent?->
			try
			{
				Desktop.getDesktop().browse(URI(url))
			} catch (e:Exception)
			{
				if (e is UnsupportedOperationException) jm_Prismix.log(
					"PRISMIX" ,
					jm_Ansi.make().bold().underline().cyan_bg().black()
						.toString("PLEASE VISIT $url FOR SUPPORT ON YOUR ISSUE!")
				)
				e.printStackTrace()
				if (e is UnsupportedOperationException)
				{
					var i=0
					while (i<5)
					{
						jm_Prismix.log(
							"PRISMIX" ,
							jm_Ansi.make().bold().underline().cyan_bg().black()
								.toString("PLEASE VISIT $url FOR SUPPORT ON YOUR ISSUE!")
						)
						i++
					}
				}
			}
		}
		val copy_btn=JButton("Copy Text")
		copy_btn.background=ux_Theme.get().get_awt("cyan")
		copy_btn.foreground=Color.black
		copy_btn.isRolloverEnabled=false
		copy_btn.isFocusPainted=false
		copy_btn.isBorderPainted=false
		copy_btn.addActionListener {
			Toolkit.getDefaultToolkit().systemClipboard.setContents(
					StringSelection(stx_HTMLDebuff.parse(jep.text)) ,
					null
				)
		}
		val wrap=JPanel()
		wrap.layout=GridLayout(1 , 3)
		wrap.isOpaque=false
		wrap.add(url_btn)
		wrap.add(copy_btn)
		wrap.add(closeState_btn)
		contentPane.background=Color.black
		contentPane.layout=BoxLayout(contentPane , BoxLayout.Y_AXIS)
		contentPane.add(pane)
		contentPane.add(wrap)
		if (Boolean.TRUE==_1const.`val`.parse("use_flashing_error_window").get())
		{
			jsp.addMouseListener(object:MouseAdapter()
			{
				override fun mouseEntered(e:MouseEvent)
				{
					touched.obj(true)
				}
				
				override fun mouseExited(e:MouseEvent)
				{
					touched.obj(false)
				}
			})
			_1const.worker2.scheduleAtFixedRate(object:TimerTask()
			{
				var alternate=true
				override fun run()
				{
					if (Boolean.FALSE==touched.obj)
					{
						if (isActive||isVisible)
						{
							jep.background=if (alternate) r else Color.black
							jep.foreground=if (alternate) Color.black else Color.white
							alternate=!alternate
						}
					}
				}
			} , 0L , 750L)
		}
	}
	
	companion object
	{
		@JvmStatic
		operator fun invoke(content:String , title:String , url:String , closeState:Err_CloseState)
		{
			jm_Prismix.log("ERRW" , "Invoking a new window: $title")
			SwingUtilities.invokeLater(gui_XErr(content , title , url , closeState))
		}
	}
}