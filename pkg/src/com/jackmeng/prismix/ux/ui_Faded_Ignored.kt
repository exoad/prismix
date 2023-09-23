// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix.ux

import javax.swing.JPanel
import javax.swing.SwingContainer
import java.awt.AlphaComposite
import java.awt.Graphics
import java.awt.Graphics2D
import java.lang.Exception
import java.util.*

@SwingContainer
open class ui_Faded_Ignored(fadeStep:Float , initDelay:Long , delay_per_step:Long):JPanel() , Runnable
{
	protected var alpha=1.0f
	protected var fadeStep:Float
	private var initialDelay:Long
	private var per_step:Long
	
	@Transient
	private var timer:Timer?=null
	
	init
	{
		assert(fadeStep in 0f..1f)
		this.fadeStep=fadeStep
		initialDelay=initDelay
		per_step=delay_per_step
	}
	
	public override fun paintComponent(g:Graphics)
	{
		super.paintComponent(g)
		val g2d=g as Graphics2D
		try
		{
			val alphaComposite=AlphaComposite.getInstance(AlphaComposite.SRC_OVER , alpha)
			g2d.composite=alphaComposite
		} catch (e:Exception)
		{			// MOST LIKELY SOME ISSUE WITH ALPHA OUT OF RANGE
		}
	}
	
	override fun run()
	{
		timer=Timer()
		timer!!.scheduleAtFixedRate(object:TimerTask()
		{
			override fun run()
			{
				alpha-=fadeStep
				repaint(30L)
				if (alpha<=0)
				{
					timer!!.cancel()
					timer!!.purge()
					isVisible=false
					if (parent!=null) parent.remove(this@ui_Faded_Ignored)
				}
			}
		} , initialDelay , per_step)
	}
}