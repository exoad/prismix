// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.stl;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.jackmeng.prismix.ux.ui_LazyViewport;

import java.awt.BorderLayout;
import java.awt.Dimension;

public final class extend_stl_SwingHelper
{
  private extend_stl_SwingHelper()
  {
  }

  public static JPanel wrap_SideToSide1(JComponent left, JComponent right)
  {
    JPanel temp = new JPanel();
    temp.setPreferredSize(new Dimension(left.getPreferredSize().width + right.getPreferredSize().width,
        Math.max(left.getPreferredSize().height, right.getPreferredSize().height)));
    temp.setLayout(new BorderLayout());
    temp.add(left, BorderLayout.WEST);
    temp.add(right, BorderLayout.EAST);

    return temp;
  }

  public static JScrollPane wrap_AsScrollable_Lazy(JComponent e)
  {
    JScrollPane r = new JScrollPane();
    ui_LazyViewport viewport = new ui_LazyViewport();
    r.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    r.setHorizontalScrollBarPolicy(
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    viewport.setView(e);
    r.setViewport(viewport);
    return r;
  }
}