// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix.ux

import com.jackmeng.prismix.jm_Prismix
import com.jackmeng.ansicolors.jm_Ansi
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.ComponentListener

/**
 * A bunch of premade AWT and Swing Listeners for debugging/logging purposes
 *
 * @author Jack Meng
 */
object ux_Listen
{
	@JvmStatic
    fun VISIBILITY():ComponentListener
	{
		return object:ComponentAdapter()
		{
			override fun componentHidden(e:ComponentEvent)
			{
				jm_Prismix.log("VISIBILITY" , e.component.name+" IS NOW "+jm_Ansi.make().red().toString("HIDDEN"))
			}
			
			override fun componentShown(r:ComponentEvent)
			{
				jm_Prismix.log("VISIBILITY" , r.component.name+" IS NOW "+jm_Ansi.make().green().toString("VISIBLE"))
			}
		}
	}
}