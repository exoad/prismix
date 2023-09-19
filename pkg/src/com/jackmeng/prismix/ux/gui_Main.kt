// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix.ux

import com.jackmeng.prismix.jm_Prismix
import com.jackmeng.prismix._1const
import com.jackmeng.prismix.use_Maker
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.JPanel
import java.awt.BorderLayout

/*------------------------------------------------ /
/ import static com.jackmeng.prismix.jm_Prismix.*; /
/-------------------------------------------------*/
class gui_Main:gui("Prismix ~ exoad (build_"+jm_Prismix._VERSION_+")")
{
	@JvmField
    val bar:JMenuBar
	@JvmField
    val wrapper:JPanel=JPanel()
	
	init
	{
		wrapper.layout=BorderLayout()
		bar=JMenuBar()
		this.iconImage=_1const.fetcher.image("assets/_icon.png")
		wrapper.preferredSize=this.preferredSize
		this.jMenuBar=bar // should not be added with a wrapper JPanel! (i did not know this was a method)
		this.contentPane.add(wrapper)
		defaultCloseOperation=EXIT_ON_CLOSE
		setDefaultLookAndFeelDecorated(true)
		
		/*------------------------------------------------------------------------------------------------------ /
    / if ("true".equalsIgnoreCase(_1const.val.get_value("debug_gui")))                                       /
    / {                                                                                                      /
    /   log("GUIMAIN", jm_Ansi.make().yellow().toString("Arming a global key listener"));                    /
    /   KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(v -> {                   /
    /     System.out.println("[UX] Received a keyboard press: " + v.getKeyCode() + " -> " + v.getKeyChar()); /
    /     return false;                                                                                      /
    /   });                                                                                                  /
    / }                                                                                                      /
    /-------------------------------------------------------------------------------------------------------*/attach_vnotifier(
		contentPane
	)
	}
	
	fun registerToBar(name:String? , vararg item:JMenuItem?)
	{
		bar.add(use_Maker.makeJMenu(name , *item))
		revalidate()
	}
	
	override fun run()
	{
		super.run()
		deploy_notif(
			"<html><strong>Took: </strong>"+(System.currentTimeMillis()-jm_Prismix.time.get())+"ms</html>" ,
			ux_Theme._theme.fg_awt()
		)
	}
}