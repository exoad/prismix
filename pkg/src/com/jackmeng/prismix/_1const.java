// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix;

import java.awt.Color;
import java.lang.ref.SoftReference;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.jackmeng.ansicolors.jm_Ansi;
import com.jackmeng.prismix.user.use_Map;
import static com.jackmeng.prismix.user.use_Map.*;
import static com.jackmeng.prismix.jm_Prismix.*;
import com.jackmeng.stl.stl_AssetFetcher;
import com.jackmeng.stl.stl_AssetFetcher.assetfetcher_FetcherStyle;
import com.jackmeng.stl.stl_ListenerPool;
import com.jackmeng.stl.stl_ListenerPool.ListenerPool_Attached.Attached_States;
import com.jackmeng.stl.stl_Struct;

/**
 * Critical constants for the program itself as a whole
 *
 * @author Jack Meng
 */
public final class _1const
{
  /*---------------------------------------------- /
  / public static final boolean DEBUG_GUI = false; /
  / public static final boolean SOFT_DEBUG = true; /
  /-----------------------------------------------*/
  public static final Random RNG = new Random();
  public static Timer worker = new Timer("com-jackmeng-clrplte-worker01");
  public static stl_AssetFetcher fetcher = new stl_AssetFetcher(assetfetcher_FetcherStyle.WEAK);
  private static SoftReference< Color > lastColor = new SoftReference<>(new Color(1F, 1F, 1F, 0F));

  // property-name, {property_type, default_value, {valid_values},
  // description}
  public static final use_Map val = new use_Map("com_jackmeng_prismix_CONFIG", "com.jackmeng.prismix.");
  static
  {
    val.put_("debug_gui", parse_Bool, new Object[] { Bool, "false", type_Bool,
        "Draw the GUI differently in order to debug layout issues and other graphical issues." });
    val.put_("soft_debug", parse_Bool, new Object[] { Bool, "true", type_Bool,
        "Enable basic debug layers, like CLI debug, and more." });
    val.put_("smart_gui", parse_Bool, new Object[] { Bool, "true", type_Bool,
        "Uses a hide and show paint schema instead of showing and painting." });
    val.put_("use_current_dir", parse_Bool,
        new Object[] { Bool, "true", type_Bool,
            "Uses the current directory of the program instead of HOME for storage." });
    val.put_("suggestions_sorted", parse_Bool, new Object[] { Bool, "true", type_Bool,
        "Try to make sure the colors in a color picker are sorted (especially for suggestions)" });
    val.put_("suggestions_sort_light_to_dark", parse_Bool, new Object[] { Bool, "true", type_Bool,
        "When suggestions_sorted is set to true, use lightest to darkest sorting, else if false, use darkest to lightest." });
    val.put_("containers_rounded", parse_Bool, new Object[] { Bool, "false", type_Bool,
        "Used to determine whether to use rounded components or not. (For that eye candy ^_^)" });
  } // put program properties

  /**
   * PAIR[0] = (java.awt.Color) Color payload
   * PAIR[1] = (java.lang.Boolean) Ignore Payload for storage
   */
  public static stl_ListenerPool.ListenerPool_Attached< stl_Struct.struct_Pair< Color, Boolean > > COLOR_ENQ = new stl_ListenerPool.ListenerPool_Attached<>(
      "current-processing-pool");
  static
  {
    _1const.COLOR_ENQ.attach(payload -> {
      if (payload.first != null)
      {
        log("COLORPANEL", jm_Ansi.make().white_bg().black_fg().toString("Pool Listener:  received: " + payload));
        if (payload.second == Attached_States.ADD_LISTENER)
          log("COLORPANEL", jm_Ansi.make().blue().toString("Enqueued a POOL Listener: " + payload.first.toString()));
        else if (payload.second == Attached_States.ATTACHED)
          log("COLORPANEL", jm_Ansi.make().green().toString("The current pool listener  has been attached"));
        else if (payload.second == Attached_States.DETACHED)
          log("COLORPANEL", jm_Ansi.make().yellow_fg().toString("The current pool listener  has been detached"));
        else if (payload.second == Attached_States.RMF_LISTENER)
          log("COLORPANEL", jm_Ansi.make().yellow_bg().black_fg().toString(
              "Dequeued a POOL_LISTENER: " + payload.first.toString()));
        else
          log("COLORPANEL", jm_Ansi.make().red_bg().bold().white_fg().toString(
              "The current pool listener  received an invalid signal: "
                  + payload.toString()));
      }
      return (Void) null;
    });
    _1const.COLOR_ENQ.add(x -> {
      log("COLORPANEL", jm_Ansi.make().blue_fg().toString(
          "Enqueued another color for GUI elements to process: rgba(" + x.first.getRed()
              + "," + x.first.getGreen() + "," + x.first.getBlue() + "," + x.first.getAlpha() + ")"));
      _1const.lastColor = new SoftReference<>(x.first);
      return (Void) null;
    });
  }

  public static Color last_color()
  {
    return _1const.lastColor.get();
  }

  public static synchronized void add(final Runnable r, final long delay)
  {
    _1const.worker.schedule(new TimerTask() {
      @Override public void run()
      {
        r.run();
      }
    }, delay);
  }

  public static synchronized void add(final Runnable r, final long delay, final long rep_delay)
  {
    _1const.worker.scheduleAtFixedRate(new TimerTask() {
      @Override public void run()
      {
        r.run();
      }
    }, delay, rep_delay);
  }
}