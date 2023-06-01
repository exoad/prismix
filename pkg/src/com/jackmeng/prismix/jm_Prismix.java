// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

import javax.swing.UIManager;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatArcDarkContrastIJTheme;
import com.jackmeng.prismix.ux.ux;
import com.jackmeng.stl.stl_Callback;
import com.jackmeng.stl.stl_In;
import com.jackmeng.stl.stl_Wrap;

/**
 * Color Palette Program Entry Point Class
 *
 * @author Jack Meng
 */
public class jm_Prismix
{

  public static final long _VERSION_ = 2023_05_01L; // YYYY_MM_DD of the closest month

  static AtomicLong time = new AtomicLong(System.currentTimeMillis());
  static final Map< String, stl_Callback< Void, String > > IO_COMMANDS = new HashMap<>();
  static
  {
    System.out.println(
        "==Prismix==\nGUI Color Picker and palette creator\nCopyright (C) Jack Meng (exoad) 2023\nEnjoy!");
    StringBuilder sb = new StringBuilder();
    System.getProperties().forEach((key, value) -> sb.append(key + " = " + value + "\n"));
    System.out.println("All initialized properties:\n" + sb.toString());
    try
    {
      System.setProperty("sun.java2d.opengl", "True");
      System.setProperty("sun.java2d.trace", "count");
      UIManager.setLookAndFeel(new FlatArcDarkContrastIJTheme());
    } catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public static void main(String... args) // !! fuck pre Java 11 users, fuck their dumb shit
  {
    _1const.add(ux.ux, 10L);
    stl_Wrap< stl_In > reader = new stl_Wrap<>(new stl_In(System.in));
    Runtime.getRuntime().addShutdownHook(
        (new Thread(() -> System.out.println("[PROGRAM] Alive for: " + (System.currentTimeMillis() - time.get())))));
    _1const.worker.scheduleAtFixedRate(new TimerTask() {
      @Override public void run()
      {
        String str = reader.obj.nextln();
        System.out.println("[I/O] Received contract: " + str);
      }
    }, 100L, 650L);
    System.out.println("[Program] Program took: " + (System.currentTimeMillis() - time.get()) + "ms to startup");
  }
}