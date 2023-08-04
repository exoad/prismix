// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import javax.swing.JTabbedPane;

public abstract class ui_Cntnr_BottomPane_Palette
    extends JTabbedPane
{

  private ux_Palette paletteModel;

  protected ui_Cntnr_BottomPane_Palette(ux_Palette model)
  {
    this.paletteModel = model;
    setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
  }

}