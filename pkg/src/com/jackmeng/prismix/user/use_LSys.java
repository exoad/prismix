// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.function.BiConsumer;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import com.jackmeng.ansicolors.jm_Ansi;
import com.jackmeng.prismix._1const;
import com.jackmeng.stl.stl_In;

import static com.jackmeng.prismix.jm_Prismix.*;

/**
 * A bare utility class for things to do with Local FileSystem
 *
 * @author Jack Meng
 */
public final class use_LSys
{
  static String locale = _here() + "/" + new String(new byte[] { 0x65, 0x78, 0x6F, 0x61, 0x64 });

  private use_LSys()
  {
  }

  public static String _home()
  {
    return System.getProperty("user.home");
  }

  public static String _here()
  {
    return System.getProperty("user.dir");
  }

  public static synchronized void init()
  {
    File f = new File(locale);
    boolean ran = false;
    if (!f.exists() && !f.isDirectory())
      ran = f.mkdir();
    log("L_SYS",
        "Initted LSYS with res: "
            + (ran ? jm_Ansi.make().green_bg().white().toString("OK") : jm_Ansi.make().red_bg().white().toString("ERR"))
            + " @ " + f.getAbsolutePath());
  }

  public static String read_all(File f)
  {
    StringBuilder sb = new StringBuilder();
    try
    {
      stl_In in = new stl_In(new FileInputStream(f));
      while (in.reader().ready())
        sb.append(in.nextln());
    } catch (Exception e)
    {
      e.printStackTrace();
    }
    return sb.toString();
  }

  public static void write(String args, Object name, boolean overwrite)
  {
    File f = new File(locale + "/" + name);

    _1const.worker2.schedule(new TimerTask() {
      @Override public void run()
      {
        try
        {
          PrintWriter pw = new PrintWriter(new FileOutputStream(f, overwrite), true, StandardCharsets.UTF_16);
          pw.print(args);
          pw.close();
          log("L_SYS", jm_Ansi.make().blue().toString("Wrote: " + args.length() + " to " + f.getAbsolutePath()));
        } catch (IOException e)
        {
          e.printStackTrace();
        }
      }
    }, 0);
    /*------------------------------------------------------------------------------------------------------ /
    / }                                                                                                      /
    / else log("L_SYS", jm_Ansi.make().red_bg().white()                                                      /
    /     .toString("Failed to write because the init system was not ran first or returned faulty.") + " | " /
    /     + f.getAbsolutePath());                                                                            /
    /-------------------------------------------------------------------------------------------------------*/
  }

  static final DumperOptions options = new DumperOptions();
  static
  {
    options.setIndent(4);
    options.setPrettyFlow(true);
    options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
  }

  public static void write(use_Map map)
  {
    File f = new File(locale + "/" + map.name.replace("\\s+", "%") + ".yml");

    Yaml yaml = new Yaml(options);
    if (f.exists() || f.isFile())
      f.delete();
    try (FileWriter fw = new FileWriter(f, StandardCharsets.UTF_16, false))
    {
      Map< String, Object > create_map = new HashMap<>();
      map.forEach((x, y) -> create_map.put(x, y.second[1]));
      yaml.dump(create_map, fw);
    } catch (IOException e)
    {
      e.printStackTrace();
    }
    log("L_SYS", "Wrote: " + map.name + " to " + f.getAbsolutePath());
    /*-------------------------------------------------------------------------------------------------------- /
    / }else                                                                                                    /
    /                                                                                                          /
    / log("L_SYS", jm_Ansi.make().red_bg().white()                                                             /
    /       .toString("Failed to write because the init system was not ran first or returned faulty.") + " | " /
    /       + f.getAbsolutePath());                                                                            /
    /---------------------------------------------------------------------------------------------------------*/
  }

  public static void load_map(String name, BiConsumer< String, String > callback)
  {
    File f = new File(locale + "/" + name + ".yml");
    Yaml yaml = new Yaml();
    try
    {
      ((LinkedHashMap< ?, ? >) yaml.load(new FileInputStream(f)))
          .forEach((x, y) -> callback.accept(x.toString(), y.toString()));
    } catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    /*----------------------------------------------------------------------------------------------------- /
    / }                                                                                                     /
    / else log("L_SYS", jm_Ansi.make().red_bg().white()                                                     /
    /     .toString("Failed to read because the init system was not ran first or returned faulty.") + " | " /
    /     + f.getAbsolutePath());                                                                           /
    /------------------------------------------------------------------------------------------------------*/
  }
}