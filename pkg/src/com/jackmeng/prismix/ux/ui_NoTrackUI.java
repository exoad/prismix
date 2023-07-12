// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.Graphics;

import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

public class ui_NoTrackUI
		extends
		BasicSliderUI
{
	public ui_NoTrackUI(JSlider slider)
	{
		super(slider);
	}

	@Override public void paintTrack(Graphics g)
	{
	}
}