// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.test;

import java.util.Arrays;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class test_ListSwingtags
{
  public static void main(final String[] args)
  {
    System.setProperty("sun.java2d.opengl", "true");
    final UIDefaults defaults = UIManager.getDefaults();
    System.out.println(defaults.size() + " properties defined !");
    final String[] colName = { "Key", "Type", "Value" };
    final String[] tempRowName = new String[defaults.size()];
    int i = 0;
    for (final Enumeration< ? > e = defaults.keys(); e.hasMoreElements(); i++)
    {
      final Object key = e.nextElement();
      tempRowName[i] = key.toString();
    }

    Arrays.sort(tempRowName);
    final String[][] rowData = new String[tempRowName.length][3];
    for (int j = 0; j < tempRowName.length; j++)
    {
      rowData[j][0] = tempRowName[j];
      rowData[j][1] = "" + (defaults.get(tempRowName[j]) == null ? defaults.get(tempRowName[j])
          : defaults.get(tempRowName[j]).getClass().getSimpleName());
      rowData[j][2] = "" + defaults.get(tempRowName[j]);
    }
    final JFrame f = new JFrame("UIManager properties default values");
    final JTable t = new JTable(rowData, colName);
    f.setContentPane(new JScrollPane(t));
    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    f.pack();
    f.setVisible(true);
  }

}