// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class ui_Faded
		extends
		JPanel
		implements
		Runnable
{
	protected float alpha = 1.0F;
	protected float fadeStep;
	protected long initialDelay, per_step;

	private transient Timer timer;

	public ui_Faded(float fadeStep, long initialDelay)
	{
		this(fadeStep, initialDelay, 50L);
	}

	public ui_Faded(float fadeStep, long initDelay, long delay_per_step)
	{
		assert fadeStep >= 0F && fadeStep <= 1F;
		this.fadeStep = fadeStep;
		this.initialDelay = initDelay;
		this.per_step = delay_per_step;
		addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e)
			{
				if (timer != null)
				{
					alpha = 1.0F;
					timer.cancel();
					timer.purge();
					repaint(10L);
				}
			}

			@Override public void mouseExited(MouseEvent e)
			{
				run();
			}
		});
	}

	@Override public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		try
		{
			AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
			g2d.setComposite(alphaComposite);
		} catch (Exception e)
		{
			// MOST LIKELY SOME ISSUE WITH ALPHA OUT OF RANGE
		}
	}

	@Override public final void run()
	{
		timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override public void run()
			{
				alpha -= fadeStep;
				repaint(30L);
				if (alpha <= 0)
				{
					timer.cancel();
					timer.purge();
					setVisible(false);
					if (getParent() != null)
						getParent().remove(ui_Faded.this);
				}
			}
		}, initialDelay, per_step);
	}
}