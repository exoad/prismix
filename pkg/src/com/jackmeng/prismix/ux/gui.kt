// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix.ux

import com.jackmeng.prismix._1const
import com.jackmeng.prismix.jm_Prismix
import com.jackmeng.ansicolors.jm_Ansi
import com.jackmeng.prismix.stl.extend_stl_Colors
import javax.swing.*
import java.awt.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.util.function.Consumer

@SwingContainer
internal abstract class gui constructor(r:String?):JFrame(r) , Runnable
{
	
	private var notifier:JPanel?=null
	
	init
	{
		iconImage=_1const.fetcher.image("assets/_icon.png")
		background=Color.black
	}
	
	fun with_WindowOpened(callback:Consumer<WindowEvent?>)
	{
		addWindowListener(object:WindowAdapter()
		{
			override fun windowOpened(e:WindowEvent)
			{
				callback.accept(e)
			}
		})
	}
	
	protected val frame:JFrame
		get()=this
	
	protected fun attach_vnotifier(master:Component?)
	{
		if (notifier==null)
		{
			if (isVisible) jm_Prismix.log(
				"GUI_MX" , jm_Ansi.make().yellow_bg().black().toString(
					"Attach_VisualNotifier should be done when the GUI is offline/invisible | This is not a fatal bug."
				)
			)
			notifier=JPanel()
			notifier!!.isOpaque=false
			notifier!!.layout=ux_WrapLayout(FlowLayout.CENTER , 6 , 10)
			notifier!!.setBounds(0 , 0 , preferredSize.width , preferredSize.height)
			val wrapper1=JLayeredPane()
			wrapper1.layout=
				OverlayLayout(wrapper1)			// this fixes the issue with the menubar and layeredpane being on different
			// layers within the ui stack which is dumb but ok
			if (menuBar!=null)
			{
				wrapper1.add(notifier , JLayeredPane.DEFAULT_LAYER+1)
				wrapper1.add(master , JLayeredPane.DEFAULT_LAYER)
				menuBar=menuBar
			}
			else
			{
				wrapper1.add(notifier , 1)
				wrapper1.add(master , 2)
			}
			contentPane=wrapper1
			revalidate()
			repaint(70L)
		}
		else jm_Prismix.log(
			"GUI_MX" ,
			jm_Ansi.make().red_bg().white()
				.toString("Already had an attached VisualNotifier | This is not a fatal bug.")
		)
	}
	
	@Synchronized
	fun deploy_notif(
			htmlContent:String , bg:Color? , fadeStep:Float , totalDuration:Long , durationOnStep:Long , strokeWidth:Int , noFill:Boolean
	)
	{
		if (notifier!=null)
		{
			val faded=if (noFill) object:ui_Faded(fadeStep , totalDuration , durationOnStep)
			{
				override fun paintComponent(g:Graphics)
				{
					super.paintComponent(g)
					val g2=g as Graphics2D
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON)
					g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL , RenderingHints.VALUE_STROKE_PURE)
					g2.stroke=BasicStroke(strokeWidth.toFloat())
					g2.color=bg
					g2.drawRoundRect(0 , 0 , width-1 , height-1 , 10 , 10)
				}
			}
			else object:ui_Faded(fadeStep , totalDuration , durationOnStep)
			{
				override fun paintComponent(g:Graphics)
				{
					super.paintComponent(g)					/*------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ /
					/ absolutely fuck this dumb fucking ass shit. i hate this framework painting schema so fucking much, i spent half a fucking day trying to see why "jl" wasnt beign printed and its apparantely all because dispose was called. sometimes it works, sometimes it doesnt, fuck ass undefined behavior with opengl suck me bitch fuck this. ok it works now lets go ^w^ /
					/-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
					val g2=g as Graphics2D
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON)
					g2.color=bg
					g2.fillRoundRect(
						0 ,
						0 ,
						width-1 ,
						height-1 ,
						10 ,
						10
					)					// dont dispose here (see above angry comment >w<) im going mental
				}
			}
			val jl=JLabel(htmlContent)
			jl.horizontalAlignment=SwingConstants.CENTER
			jl.verticalAlignment=SwingConstants.CENTER
			jl.foreground=
				extend_stl_Colors.awt_remake(extend_stl_Colors.binary_fg_decider(extend_stl_Colors.awt_strip_rgba(bg)))
			faded.preferredSize=Dimension(jl.preferredSize.width+64 , jl.preferredSize.height+10)
			faded.layout=BorderLayout()
			faded.isOpaque=false
			faded.setBounds(
				0 , (width-faded.preferredSize.width)/2 , faded.preferredSize.width , preferredSize.height
			)
			faded.layout=BorderLayout()
			faded.add(jl as JComponent , BorderLayout.CENTER)
			notifier!!.add(faded)
			notifier!!.revalidate()
			notifier!!.repaint(70L)
			SwingUtilities.invokeLater(faded)
			jm_Prismix.log("GUI_MX" , "Deployed notif: "+htmlContent+" @ "+faded.x+","+faded.y)
		}
	}
	
	@Synchronized
	fun deploy_notif(htmlContent:String , bg:Color?)
	{
		deploy_notif(htmlContent , bg , 0.05f , 2000L , 50L , -1 , false)
	}
	
	override fun run()
	{
		pack()
		setLocationRelativeTo(null)
		isVisible=true
		jm_Prismix.log("GUI_MX" , "Rendered a new window to the screen ["+javaClass.canonicalName+"#"+hashCode()+"]")
	}
}