// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.clrplte;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatArcDarkContrastIJTheme;
import com.jackmeng.clrplte.ui.gui_Container;
import com.jackmeng.clrplte.ui.gui_Main;

public class jm_ColorPalette
{
  static
  {
    System.out.println(
        "==com.jackmeng.ColorPalette==\nGUI Color Picker and palette creator\nCopyright (C) Jack Meng (exoad) 2023\nEnjoy!");
    StringBuilder sb = new StringBuilder();
    System.getProperties().forEach((key, value) -> sb.append(key + " = " + value + "\n"));
    System.out.println("All initialized properties:\n" + sb.toString());
    try
    {
      System.setProperty("sun.java2d.opengl", "True");
      UIManager.setLookAndFeel(new FlatArcDarkContrastIJTheme());
    } catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public static void main(String... args) // !! fuck pre Java 11 users, fuck their dumb shit
  {
    SwingUtilities.invokeLater(new gui_Main(new gui_Container()));
  }
}