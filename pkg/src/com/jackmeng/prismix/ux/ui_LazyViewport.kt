// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix.ux

import javax.swing.JComponent
import javax.swing.JViewport
import java.awt.Point

/**
 * Mostly for a JScrollPane where an update in the component
 * view will not trigger the JScrollPane to update itself.
 *
 * @author Jack Meng
 */
class ui_LazyViewport:JViewport()
{
	private var locked=false
	override fun setViewPosition(p:Point)
	{
		if (this.locked()) return
		super.setViewPosition(p)
	}
	
	fun locked():Boolean
	{
		return locked
	}
	
	fun locked(locked:Boolean)
	{
		this.locked=locked
	}
	
	companion object
	{
		@JvmStatic
        fun make(r:JComponent?):ui_LazyViewport
		{
			val e=ui_LazyViewport()
			e.view=r
			return e
		}
	}
}