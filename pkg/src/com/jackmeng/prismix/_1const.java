// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix;

import static com.jackmeng.prismix.jm_Prismix.log;
import java.awt.Color;
import java.lang.ref.SoftReference;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jackmeng.ansicolors.jm_Ansi;
import com.jackmeng.prismix.ux.stx_Map;
import com.jackmeng.stl.stl_AssetFetcher;
import com.jackmeng.stl.stl_AssetFetcher.assetfetcher_FetcherStyle;
import com.jackmeng.stl.stl_ListenerPool;
import com.jackmeng.stl.stl_ListenerPool.ListenerPool_Attached.Attached_States;
import com.jackmeng.stl.stl_Struct;
import com.jackmeng.stl.stl_Struct.struct_Pair;

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
  public static Timer worker = new Timer("com-jackmeng-prismix-worker01"),
      worker2 = new Timer("com-jackmeng-prismix-worker02");
  public static ExecutorService worker3 = Executors.newWorkStealingPool(4);
  public static stl_AssetFetcher fetcher = new stl_AssetFetcher(assetfetcher_FetcherStyle.WEAK);

  private static SoftReference< Color > lastColor = new SoftReference<>(new Color(1F, 1F, 1F, 0F));

  // property-name, {property_type, default_value, {valid_values},
  // description}
  public static final stx_Map val = new stx_Map("com_jackmeng_prismix_CONFIG", "com.jackmeng.prismix.");

  public static final String PATH = new String(new byte[] { 0x65, 0x78, 0x6F, 0x61, 0x64 });

  public static void __init()
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

  /**
   * PAIR[0] = (java.awt.Color) Color payload
   * PAIR[1] = (java.lang.Boolean) Ignore Payload for storage
   */
  public static stl_ListenerPool.ListenerPool_Attached< stl_Struct.struct_Pair< Color, Boolean > > COLOR_ENQ = new stl_ListenerPool.ListenerPool_Attached<>(
      "current-processing-pool") {
    @Override public synchronized void dispatch(stl_Struct.struct_Pair< Color, Boolean > e)
    {
      // Here we want it so that the same color would not be reused and redispatched
      // everytime causing unnecessary GUI updates. Note: all GUI updates are heavily
      // tied to this queue system
      if (Boolean.TRUE.equals(!e.second) && !e.first.equals(last_color()))
        super.dispatch(e);
    }
  };

  public static void shutdown_hook(Runnable r)
  {
    Runtime.getRuntime().addShutdownHook(new Thread(r));
  }

  public static final stl_Struct.struct_Pair< Integer, Integer > MOUSE_LOCATION = new struct_Pair<>(0, 0);

  public static Color last_color()
  {
    return _1const.lastColor.get() == null ? Color.gray : _1const.lastColor.get();
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
