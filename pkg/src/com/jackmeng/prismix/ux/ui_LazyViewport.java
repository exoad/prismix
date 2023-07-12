// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JViewport;

/**
 * Mostly for a JScrollPane where an update in the component
 * view will not trigger the JScrollPane to update itself.
 *
 * @author Jack Meng
 */
public final class ui_LazyViewport
    extends JViewport
{

  public static ui_LazyViewport make(JComponent r)
  {
    ui_LazyViewport e = new ui_LazyViewport();
    e.setView(r);
    return e;
  }

  private boolean locked = false;

  @Override public void setViewPosition(final Point p)
  {
    if (this.locked())
      return;
    super.setViewPosition(p);
  }

  public boolean locked()
  {
    return this.locked;
  }

  public void locked(final boolean locked)
  {
    this.locked = locked;
  }
}