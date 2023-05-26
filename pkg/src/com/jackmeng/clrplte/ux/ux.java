// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.clrplte.ux;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import com.jackmeng.clrplte._1const;
import com.jackmeng.stl.stl_SwingHelper;

/**
 * Main UI Handler class. This serves as a bridge between the
 * mainui and the childui facilitating regstering child elements to certain
 * elements in the mainui. On the other hand, it also facilitates as an
 * abstraction
 * layer for the main(String[]) function defined in jm_ColorPalette
 *
 * @author Jack Meng
 */
public final class ux
    implements
    Runnable
{
  private gui_Main mainui;
  private gui_Container childui;

  public ux()
  {
    childui = new gui_Container();
    mainui = new gui_Main();
    mainui.setPreferredSize(new Dimension(childui.getPreferredSize().width, childui.getPreferredSize().height + 60));
    mainui.wrapper.add(childui, BorderLayout.SOUTH);
    mainui.bar.setPreferredSize(new Dimension(childui.getPreferredSize().width, 25));
  }


  @Override public void run()
  {
    System.out.println("[UX] Dispatched a run event for the current UI creation! Hoping this goes well...");
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