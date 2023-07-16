// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import javax.swing.JComponent;
import javax.swing.plaf.LayerUI;

public class ui_Blurred extends LayerUI< Component >
{

	private BufferedImage mOffscreenImage;
	private final BufferedImageOp mOperation;

	public ui_Blurred(int blurValue)
	{
		float[] blurKernel = new float[blurValue * blurValue];
		for (int i = 0; i < blurValue * blurValue; i++)
			blurKernel[i] = 1.0f / (blurValue * blurValue);
		mOperation = new ConvolveOp(new Kernel(blurValue, blurValue, blurKernel), ConvolveOp.EDGE_NO_OP, null);
	}

	@Override public void paint(Graphics g, JComponent c)
	{
		int w = c.getWidth();
		int h = c.getHeight();
		if (w == 0 || h == 0)
			return;
		if (mOffscreenImage == null || mOffscreenImage.getWidth() != w || mOffscreenImage.getHeight() != h)
			mOffscreenImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D ig2 = mOffscreenImage.createGraphics();
		ig2.setClip(g.getClip());
		super.paint(ig2, c);
		ig2.dispose();
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(mOffscreenImage, mOperation, 0, 0);
	}

}