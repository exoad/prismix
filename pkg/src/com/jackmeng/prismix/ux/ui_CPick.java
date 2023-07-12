// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.jackmeng.prismix._1const;
import com.jackmeng.stl.stl_Callback;
import com.jackmeng.stl.stl_Listener;
import com.jackmeng.stl.stl_Struct;
import com.jackmeng.stl.stl_Struct.struct_Pair;

// Implementations of color pickers visually
// All of these should be able to dispatch events to the color queue found in _1const
public abstract class ui_CPick
    extends
    JPanel
    implements
    stl_Listener< stl_Struct.struct_Pair< Color, Boolean > >
{

  @SuppressWarnings("unchecked") public static JComponent attach_asis(JComponent e)
  {
    if (e instanceof stl_Callback) // finalized types at compile time are so fucking stupid
      _1const.COLOR_ENQ.add((stl_Listener< struct_Pair< Color, Boolean > >) e);
    return e;
  }
}
