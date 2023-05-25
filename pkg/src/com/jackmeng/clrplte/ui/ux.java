// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.clrplte.ui;

import javax.swing.*;

import java.awt.Color;
import java.text.MessageFormat;

import com.jackmeng.clrplte._1const;
import com.jackmeng.stl.stl_Callback;
import com.jackmeng.stl.stl_Struct;
import com.jackmeng.stl.stl_SwingHelper;

public final class ux
    implements
    Runnable
{
  private gui_Main mainui;
  private gui_Container childui;

  public ux()
  {
    childui = new gui_Container();
    mainui = new gui_Main(childui);
  }

  // TODO: make a registration system for the components for dynamic loading and
  // offloading of components without having to hardcode each component and its
  // individual interactions with the host program.
  public void register(
      stl_Struct.struct_Pair< JPanel, stl_Struct.struct_Pair< JComponent, stl_Callback< Void, Color > > > e)
  {

  }

  @Override public void run()
  {
    SwingUtilities.invokeLater(() -> {
      mainui.run();
      if (_1const.DEBUG_GUI)
        stl_SwingHelper.listComponents_OfContainer(mainui).forEach(x -> {
          try
          {
            if (x instanceof JComponent)
              ((JComponent) x).setBorder(((JComponent) x).getBorder() == null
                  ? BorderFactory
                      .createLineBorder(new Color((float) Math.random(), (float) Math.random(), (float) Math.random()))
                  : BorderFactory.createCompoundBorder(
                      BorderFactory.createLineBorder(
                          new Color((float) Math.random(), (float) Math.random(), (float) Math.random())),
                      ((JComponent) x).getBorder()));
          } catch (Exception t)
          {
            // IGNORE, probably some .setBorder() not supported bs
          }
        });
    });
  }
}