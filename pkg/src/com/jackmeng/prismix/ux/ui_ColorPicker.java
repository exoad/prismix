// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import com.jackmeng.stl.stl_Callback;
import com.jackmeng.stl.stl_Struct;
import com.jackmeng.stl.stl_Struct.struct_Pair;

import java.awt.*;

import javax.swing.*;

// Implementations of color pickers visually
public final class ui_ColorPicker
{
  private ui_ColorPicker()
  {
  }

  // Creates a Gradient of White-Black-Color rectangular with sliders to adjust
  // the colors only
  // This should be the default option for the color picker
  public static final class CPick_GradRect
      extends JPanel
      implements
      stl_Callback< Void, stl_Struct.struct_Pair< Color, Boolean > > // Optional to be added but the color picker should
                                                                     // be always attached to the currently color picker
  {

    @Override public Void call(struct_Pair< Color, Boolean > arg0)
    {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'call'");
    }

  }
}