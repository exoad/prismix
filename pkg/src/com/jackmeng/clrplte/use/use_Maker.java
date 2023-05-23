// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.clrplte.use;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

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
      if(i != items.length - 1)
        menu.addSeparator();
    }
    return menu;
  }
}