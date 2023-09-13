// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix.ux

import javax.swing.JComponent
import javax.swing.plaf.LayerUI
import java.awt.Component
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.awt.image.BufferedImageOp
import java.awt.image.ConvolveOp
import java.awt.image.Kernel

class ui_Blurred(blurValue:Int):LayerUI<Component?>()
{
	private var mOffscreenImage:BufferedImage?=null
	private val mOperation:BufferedImageOp
	
	init
	{
		val blurKernel=FloatArray(blurValue*blurValue)
		for (i in 0 until blurValue*blurValue) blurKernel[i]=1.0f/(blurValue*blurValue)
		mOperation=ConvolveOp(Kernel(blurValue , blurValue , blurKernel) , ConvolveOp.EDGE_NO_OP , null)
	}
	
	override fun paint(g:Graphics , c:JComponent)
	{
		val w=c.width
		val h=c.height
		if (w==0||h==0) return
		if (mOffscreenImage==null||mOffscreenImage!!.width!=w||mOffscreenImage!!.height!=h) mOffscreenImage=
			BufferedImage(w , h , BufferedImage.TYPE_INT_RGB)
		val ig2=mOffscreenImage!!.createGraphics()
		ig2.clip=g.clip
		super.paint(ig2 , c)
		ig2.dispose()
		val g2=g as Graphics2D
		g2.drawImage(mOffscreenImage , mOperation , 0 , 0)
	}
}