// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.prismix;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.jackmeng.stl.stl_AssetFetcher;
import com.jackmeng.stl.stl_ListenerPool;
import com.jackmeng.stl.stl_Struct;
import com.jackmeng.stl.stl_AssetFetcher.assetfetcher_FetcherStyle;
import com.jackmeng.stl.stl_ListenerPool.ListenerPool_Attached.Attached_States;

import java.awt.Color;

/**
 * Critical constants for the program itself as a whole
 *
 * @author Jack Meng
 */
public final class _1const
{
  public static final boolean DEBUG_GUI = false;
  public static final Random RNG = new Random();
  public static Timer worker = new Timer("com-jackmeng-clrplte-worker01");
  public static stl_AssetFetcher fetcher = new stl_AssetFetcher(assetfetcher_FetcherStyle.WEAK);
  private static Color lastColor = new Color(1F, 1F, 1F, 1F);
  public static final String working_dir = System.getProperty("user.dir");
  /**
   * PAIR[0] = (java.awt.Color) Color payload
   * PAIR[1] = (java.lang.Boolean) Ignore Payload for storage
   */
  public static stl_ListenerPool.ListenerPool_Attached< stl_Struct.struct_Pair< Color, Boolean > > COLOR_ENQ = new stl_ListenerPool.ListenerPool_Attached<>(
      "current-processing-pool");
  static
  {
    COLOR_ENQ.attach(payload -> {
      if (payload.first != null)
      {
        System.out.println("[COLOR_POOL] Pool Listener:  received: " + payload);
        if (payload.second == Attached_States.ADD_LISTENER)
          System.out.println("[COLOR_POOL] Enqueued a POOL Listener: " + payload.first.toString());
        else if (payload.second == Attached_States.ATTACHED)
          System.out.println("[COLOR_POOL] The current pool listener  has been attached");
        else if (payload.second == Attached_States.DETACHED)
          System.out.println("[COLOR_POOL] The current pool listener  has been detached");
        else if (payload.second == Attached_States.RMF_LISTENER)
          System.out.println("[COLOR_POOL] Dequeued a POOL_LISTENER: " + payload.first.toString());
        else
          System.out.println("[COLOR_POOL] The current pool listener  received an invalid signal: "
              + payload.toString());
      }
      return (Void) null;
    });
    COLOR_ENQ.add(x -> {
      System.out.println("[COLOR_POOL] Enqueued another color for GUI elements to process: rgba(" + x.first.getRed()
          + "," + x.first.getGreen() + "," + x.first.getBlue() + "," + x.first.getAlpha() + ")");
      lastColor = x.first;
      return (Void) null;
    });
  }

  public static Color last_color()
  {
    return lastColor;
  }

  public static synchronized void add(Runnable r, long delay)
  {
    worker.schedule(new TimerTask() {
      @Override public void run()
      {
        r.run();
      }
    }, delay);
  }

  public static synchronized void add(Runnable r, long delay, long rep_delay)
  {
    worker.scheduleAtFixedRate(new TimerTask() {
      @Override public void run()
      {
        r.run();
      }
    }, delay, rep_delay);
  }
}