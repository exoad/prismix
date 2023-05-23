// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.clrplte.use;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.Color;

public final class use_Maker
{
  private use_Maker()
  {
  }

  public static JMenu makeJMenu(String text, JMenuItem... items)
  {
    JMenu menu = new JMenu(text);
    for (int i = 0; i < items.length; i++)
    {
      menu.add(items[i]);
      if (i != items.length - 1)
        menu.addSeparator();
    }
    return menu;
  }

  public static void debug(JComponent e)
  {
    if (e != null)
      if (e.getBorder() != null)
        e.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.pink, 2), e.getBorder()));
  }
}