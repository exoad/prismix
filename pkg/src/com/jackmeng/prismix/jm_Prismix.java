// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.formdev.flatlaf.intellijthemes.FlatGrayIJTheme;
import com.formdev.flatlaf.util.SystemInfo;
import com.jackmeng.ansicolors.jm_Ansi;
import com.jackmeng.prismix.stl.extend_stl_Colors;
import com.jackmeng.prismix.user._lua;
import com.jackmeng.prismix.ux.ux;
import com.jackmeng.stl.stl_Callback;
import com.jackmeng.stl.stl_Chrono;
import com.jackmeng.stl.stl_In;
import com.jackmeng.stl.stl_Str;
import com.jackmeng.stl.stl_Wrap;

/**
 * Color Palette Program Entry Point Class
 *
 * @author Jack Meng
 */
public class jm_Prismix
{

  // ! Any IO that happens here should be deemed important and not purely for
  // debug purposes!

  public static final AtomicLong time = new AtomicLong(System.currentTimeMillis());
  public static final long _VERSION_ = 2023_07_01L; // YYYY_MM_DD of the closest month
  public static final PrintStream IO = System.out;

  static final Map< String, stl_Callback< Void, String > > IO_COMMANDS = new HashMap<>();
  static
  {
    System.out.println(
        "==Prismix==\nGUI Color Picker and palette creator\nCopyright (C) Jack Meng (exoad) 2023\nEnjoy!");

    System.setOut(new PrintStream(new OutputStream() {
      @Override public void write(byte[] buffer, int offset, int length)
      {
        log("SYS", new String(buffer, offset, length));
      }

      @Override public void write(int b) throws IOException
      {
        write(new byte[] { (byte) b }, 0, 1);
      }
    }));

    System.setErr(System.out);

    final StringBuilder sb = new StringBuilder();
    System.getProperties().forEach((key, value) -> sb.append(key + " = " + value + "\n"));
    System.out.println("All initialized properties:\n" + sb.toString());
    try
    {
      System.setProperty("sun.java2d.opengl", "True");
      System.setProperty("sun.java2d.trace", "count");
      if (SystemInfo.isLinux || SystemInfo.isWindows_10_orLater)
      {
        System.setProperty("flatlaf.useWindowDecorations", "true");
        System.setProperty("flatlaf.menuBarEmbedded", "true");
        JFrame.setDefaultLookAndFeelDecorated(true);
      }
      else if (SystemInfo.isMacOS)
        System.setProperty("apple.awt.application.appearance", "system");
      /*-------------------------------------------------------------------------------------------------------------- /
      / UIManager.setLookAndFeel(_1const.DARK_MODE ? new FlatHighContrastIJTheme() : new FlatGrayIJTheme()); // or FlatGratIJTheme /
      /---------------------------------------------------------------------------------------------------------------*/
      UIManager.setLookAndFeel(new FlatGrayIJTheme());
      UIManager.put("ScrollBar.background", null);
      UIManager.put("ScrollBar.showButtons", false);
      UIManager.put("JScrollPane.smoothScrolling", true);
      UIManager.put("SplitPaneDivider.gripDotCount", 4);
      UIManager.put("Component.focusedBorderColor", extend_stl_Colors.awt_empty());
      UIManager.put("Component.focusColor", extend_stl_Colors.awt_empty());
      UIManager.put("TabbedPane.tabSeparatorsFullHeight", false);
      UIManager.put("TabbedPane.showTabSeparators", true);
    } catch (final Exception e)
    {
      e.printStackTrace();
    }
    _lua.simple_load(_1const.fetcher.file("assets/lua/_.lua").getAbsolutePath());

  }

  public static void main(final String... x) // !! fuck pre Java 11 users, fuck their dumb shit
  {
    _1const.add(ux._ux, 10L);
    final stl_Wrap< stl_In > reader = new stl_Wrap<>(new stl_In(System.in));
    Runtime.getRuntime().addShutdownHook(
        (new Thread(
            () -> log("PRISMIX", "Alive for: " + (System.currentTimeMillis() - jm_Prismix.time.get())))));
    _1const.worker.scheduleAtFixedRate(new TimerTask() {
      @Override public void run()
      {
        final String str = reader.obj.nextln();
        log("I/O", "Received contract " + str);
      }
    }, 100L, 650L);
    log("PRISMIX", "Program took: " + (System.currentTimeMillis() - jm_Prismix.time.get()) + "ms to startup");

  }

  static final int MAX_LOG_NAME_LEN = 13;

  public static void debug(String content)
  {
    for (String str : content.split("\n"))
    {
      IO.println(
          jm_Ansi.make().bold().toString("| ") + jm_Ansi.make().red().white().toString("DEBUG")
              + jm_Ansi.make().bold().toString(" |") + " " + jm_Ansi.make().bold().toString("| ")
              + jm_Ansi.make().white().cyan()
                  .toString(stl_Chrono.format_time("HH:mm:ss:SSS", System.currentTimeMillis() - time.get()))
              + jm_Ansi.make().bold().toString(" |") + "\t"
              + jm_Ansi.make().cyan().bold().toString("||") + "\t\t" + str);
    }
  }

  public static void log(String name, String content)
  {
    name = name.length() > MAX_LOG_NAME_LEN ? name.substring(0, MAX_LOG_NAME_LEN)
        : name.length() < MAX_LOG_NAME_LEN ? name + (stl_Str.n_copies(MAX_LOG_NAME_LEN - name.length(), "_")) : name;
    for (String str : content.split("\n"))
    {
      IO.println(
          jm_Ansi.make().bold().toString("| ") + jm_Ansi.make().white_bg().red_fg().toString(name)
              + jm_Ansi.make().bold().toString(" |") + " " + jm_Ansi.make().bold().toString("| ")
              + jm_Ansi.make().white().magenta()
                  .toString(stl_Chrono.format_time("HH:mm:ss:SSS", System.currentTimeMillis() - time.get()))
              + jm_Ansi.make().bold().toString(" |") + "\t"
              + jm_Ansi.make().cyan().bold().toString("||") + "\t\t" + str);
    }
    /*------------------------------------------------------------------------------------------------------ /
    / IO.println(                                                                                            /
    /     jm_Ansi.make().bold().toString("| ") + jm_Ansi.make().white_bg().red_fg().toString(name)           /
    /         + jm_Ansi.make().bold().toString(" |") + " " + jm_Ansi.make().bold().toString("| ")            /
    /         + jm_Ansi.make().white().magenta()                                                             /
    /             .toString(stl_Chrono.format_time("HH:mm:ss:SSS", System.currentTimeMillis() - time.get())) /
    /         + jm_Ansi.make().bold().toString(" |") + "\t"                                                  /
    /         + jm_Ansi.make().cyan().bold().toString("||") + "\t\t" + content);                             /
    /-------------------------------------------------------------------------------------------------------*/
  }
}