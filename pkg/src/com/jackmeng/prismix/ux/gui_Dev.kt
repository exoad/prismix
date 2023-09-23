// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix.ux

import com.jackmeng.prismix.ux.stx_Helper.quick_btn
import com.jackmeng.prismix.ux.gui_XInf.Companion.force_invoke
import com.jackmeng.prismix.ux.ui_LazyViewport.Companion.make
import com.jackmeng.prismix.stl.extend_stl_Colors
import com.jackmeng.prismix.jm_Prismix
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.JScrollPane
import kotlin.system.exitProcess
import java.awt.*
import java.awt.event.WindowEvent
import java.lang.RuntimeException
import java.rmi.UnexpectedException

class gui_Dev:gui("Prismix : dev_window")
{
	/*--------------------------- /
	/ private ux_WindowRes reser; /
	/----------------------------*/ // refer to why setUndecorated was commented out
	/*------------------- /
	/ private int pX, pY; /
	/--------------------*/ // refer to why setUndecorated was commented out
	init
	{		/*-------------------------------------------- /
		/ reser = new ux_WindowRes();                  /
		/ reser.registerComponent(getFrame());         /
		/ reser.setSnapSize(new Dimension(5, 5));      /
		/ reser.setDragInsets(new Insets(4, 4, 4, 4)); /
		/---------------------------------------------*/ // refer to why setUndecorated was commented out
		defaultCloseOperation=DISPOSE_ON_CLOSE
		preferredSize=Dimension(470 , 350)
		background=Color.black
		val pane=JPanel()
		pane.border=BorderFactory.createLineBorder(Color.YELLOW , 2 , false)
		pane.layout=ux_WrapLayout(FlowLayout.LEFT , 8 , 10)
		pane.isOpaque=true
		pane.background=Color.black
		pane.add(quick_btn("GC") { System.gc() })
		pane.add(quick_btn("Hide MAINUI") { __ux._ux.mainUI.isVisible=false })
		pane.add(quick_btn("Show MAINUI") { __ux._ux.mainUI.isVisible=true })
		pane.add(quick_btn("Reval MAINUI") { __ux._ux.mainUI.contentPane.revalidate() })
		pane.add(quick_btn("Repaint MAINUI") { __ux._ux.mainUI.repaint() })
		pane.add(quick_btn("Update XOR") {
			val g=__ux._ux.mainUI.graphics
			g.setXORMode(extend_stl_Colors.awt_random_Color())
			__ux._ux.mainUI.update(g)
		})
		pane.add(quick_btn("Intrp_BIC") {
			val g=__ux._ux.mainUI.graphics as Graphics2D
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION , RenderingHints.VALUE_INTERPOLATION_BICUBIC)
			__ux._ux.mainUI.update(g)
		})
		pane.add(quick_btn("Intrp_AA") {
			val g=__ux._ux.mainUI.graphics as Graphics2D
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON)
			__ux._ux.mainUI.update(g)
		})
		pane.add(quick_btn("AlphaC::0.5") {
			val g=__ux._ux.mainUI.graphics as Graphics2D
			g.composite=AlphaComposite.getInstance(AlphaComposite.SRC_ATOP , 0.5f)
			__ux._ux.mainUI.update(g)
		})
		pane.add(quick_btn(
			"Vali MainUI"
		) { __ux._ux.mainUI.paintComponents(__ux._ux.mainUI.graphics) })
		pane.add(quick_btn("EmitEX") {
			try
			{
				throw UnexpectedException("CHECKED ERROR [DEVELOPER CONSOLE]")
			} catch (e:UnexpectedException)
			{
				throw RuntimeException("DEVELOPER_CONTROLLED" , e)
			}
		})
		pane.add(quick_btn(
			"EmitIN"
		) { force_invoke("<html><body>Test info windowing</html></body>" , "Test Info Window") })
		pane.add(quick_btn("ExitDev") { dispose() })
		pane.add(quick_btn("Kill0") { exitProcess(0) })
		pane.add(quick_btn("Kill1") { exitProcess(1) })
		pane.add(quick_btn("ClearLog") { println("\u001b[H\u001b[2J") })		// TODO: pane.add(stx_Helper.quick_btn("DeleteLogs", () -> ))
		var i=0
		for (r in arrayOf(Runnable { ui_Graff.main(null) } ,
			Runnable { gui_XAddColor.request() })) pane.add(quick_btn("Action#"+i++ , r))
		val jsp=JScrollPane()
		jsp.background=Color.black
		jsp.border=BorderFactory.createEmptyBorder()
		jsp.setViewportView(make(pane))
		contentPane.add(jsp)
		with_WindowOpened { ev:WindowEvent?->
			jm_Prismix.log("DEV_WINDOW" , "DevWindow requests focus after initial launch")
			isAlwaysOnTop=true
			isAlwaysOnTop=false // dumbest implementation lmfao
		}
	}
}