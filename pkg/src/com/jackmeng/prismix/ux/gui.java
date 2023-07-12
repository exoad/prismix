// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import javax.swing.JFrame;
import javax.swing.SwingContainer;

import com.jackmeng.prismix._1const;

@SwingContainer abstract sealed class gui
		extends
		JFrame
		implements
		Runnable
		permits
		gui_Main,
		gui_XErr,
		gui_XInf,
		gui_Lua,
		gui_Config
{

	protected gui(String r)
	{
		super(r);
		setIconImage(_1const.fetcher.image("assets/_icon.png"));
	}

	@Override public void run()
	{
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}