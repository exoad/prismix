// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.clrplte;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatArcDarkContrastIJTheme;
import com.jackmeng.clrplte.ui.gui_Container;
import com.jackmeng.clrplte.ui.gui_Main;
import com.jackmeng.stl.stl_Callback;
import com.jackmeng.stl.stl_In;
import com.jackmeng.stl.stl_SwingHelper;
import com.jackmeng.stl.stl_Wrap;

public class jm_ColorPalette
{
  static AtomicLong time = new AtomicLong(System.currentTimeMillis());
  static final Map< String, stl_Callback< Void, String > > IO_COMMANDS = new HashMap<>();
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

  static void exec(String command)
  {

  }

  public static void main(String... args) // !! fuck pre Java 11 users, fuck their dumb shit
  {
    gui_Main e = new gui_Main(new gui_Container());
    SwingUtilities.invokeLater(() -> {
      e.run();
      if (_1const.DEBUG_GUI)
        stl_SwingHelper.listComponents_OfContainer(e).forEach(x -> {
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
    stl_Wrap< stl_In > reader = new stl_Wrap<>(new stl_In(System.in));
    _1const.worker.scheduleAtFixedRate(new TimerTask() {
      @Override public void run()
      {
        String str = reader.obj.nextln();
        System.out.println("[I/O] Received contract: " + str);
      }
    }, 100L, 650L);
    System.out.println("[Program] Program took: " + (System.currentTimeMillis() - time.getAcquire()) + "ms to startup");
  }
}