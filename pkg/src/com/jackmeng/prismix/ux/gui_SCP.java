// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public final class gui_SCP
		extends
		JFrame
		implements
		Runnable

{

	@Override public void run()
	{
		SwingUtilities.invokeLater(() -> {
			pack();
			setVisible(true);
		});
	}

}