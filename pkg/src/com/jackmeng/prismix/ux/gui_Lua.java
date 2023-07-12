// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.Dimension;

import javax.swing.WindowConstants;

public final class gui_Lua
		extends
		_gui
{

	public gui_Lua()
	{
		super("Prismix ~ Lua");
		setPreferredSize(new Dimension(740, 560));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	public static void main(String... a)
	{
		new gui_Lua().run();
	}

}