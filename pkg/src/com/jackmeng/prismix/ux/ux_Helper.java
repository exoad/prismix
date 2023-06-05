// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import com.jackmeng.prismix._colors;

public final class ux_Helper
{
  private ux_Helper()
  {
  }

  public static Border bottom_container_AttributesBorder(String name)
  {
    return BorderFactory.createTitledBorder(
        BorderFactory.createEmptyBorder(5, 0, 5, 0),
        "<html><u><strong><p style=\"color:"+_colors.ROSE+"\">" + name + "</p></strong></u></strong>");
  }


}