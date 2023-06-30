// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import com.jackmeng.prismix.stl.extend_stl_Colors;

import javax.swing.*;
import java.awt.*;

public static class CPick_Button
    extends JButton
{

  public CPick_Button(Color initialColor)
  {
    setBackground(initialColor);
    setForeground(extend_stl_Colors
        .awt_remake(extend_stl_Colors.binary_fg_decider(extend_stl_Colors.awt_strip_rgba(initialColor))));
  }

}