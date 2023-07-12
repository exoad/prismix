
// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class gui_Config
    extends
    JFrame
    implements
    Runnable
{

  public gui_Config()
  {
    super("Primsix ~ Config");
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
  }

  @Override public void run()
  {
    pack();
    setVisible(true);
  }


}