// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.event.*;

import com.jackmeng.ansicolors.jm_Ansi;

import static com.jackmeng.prismix.jm_Prismix.*;

/**
 * A bunch of premade AWT and Swing Listeners for debugging/logging purposes
 *
 * @author Jack Meng
 */
public final class ux_Listen
{
  private ux_Listen()
  {
  }

  public static ComponentListener VISIBILITY()
  {
    return new ComponentAdapter() {
      @Override public void componentHidden(ComponentEvent e)
      {
        log("VIS_LOG", e.getComponent().getName() + " IS NOW " + jm_Ansi.make().red().toString("HIDDEN"));
      }

      @Override public void componentShown(ComponentEvent r)
      {
        log("VIS_LOG", r.getComponent().getName() + " IS NOW " + jm_Ansi.make().green().toString("VISIBLE"));
      }
    };
  }
}