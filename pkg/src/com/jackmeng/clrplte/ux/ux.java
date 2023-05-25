// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.clrplte.ux;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import com.jackmeng.clrplte._1const;
import com.jackmeng.stl.stl_Callback;
import com.jackmeng.stl.stl_Struct;
import com.jackmeng.stl.stl_SwingHelper;

/**
 * Main UI Handler class. This serves as a bridge between the
 *
 * @author Jack Meng
 */
public final class ux
    implements
    Runnable
{
  private gui_Main mainui;
  private gui_Container childui;

  public enum ux_AttributesType {
    COLOR_TEXT, COLOR_VISUAL;
  }

  public ux()
  {
    childui = new gui_Container();
    mainui = new gui_Main();
    mainui.setPreferredSize(new Dimension(childui.getPreferredSize().width, childui.getPreferredSize().height + 60));
    mainui.wrapper.add(childui, BorderLayout.SOUTH);
    mainui.bar.setPreferredSize(new Dimension(childui.getPreferredSize().width, 25));
  }

  // TODO: make a registration system for the components for dynamic loading and
  // offloading of components without having to hardcode each component and its
  // individual interactions with the host program.
  public void register_color_attribute(
      stl_Struct.struct_Pair< JPanel, stl_Struct.struct_Pair< JComponent, stl_Callback< Void, Color > > >[] e)
  {



    /*---------------------------------------------------------------------- /
    / Reference Code from gui_Main that was previously taken out:            /
    / bar.add(makeJMenu("Color Config.", make(new stl_Struct.struct_Pair[] { /
    /     make(                                                              /
    /         "1_Misc. Attributes",                                          /
    /         make(masterView.top.miscAttributes)),                          /
    /     make(                                                              /
    /         "1_RGBA Attributes",                                           /
    /         make(masterView.top.rgbData)),                                 /
    /     make("1_Color Space Attributes",                                   /
    /         make(masterView.top.colorSpace)),                              /
    / })));                                                                  /
    /-----------------------------------------------------------------------*/
  }

  @Override public void run()
  {
    SwingUtilities.invokeLater(() -> {
      childui.validate_size();
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