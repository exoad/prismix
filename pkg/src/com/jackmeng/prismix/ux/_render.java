// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import javax.swing.JComponent;

import com.jackmeng.stl.stl_Callback;

public final class _render
{
  private _render()
  {
  }

  /**
   * This method help with components that need to do primitive operations like
   * setBackground on a multitude of components at once. This helps to ensure that
   * the colors don't appear one by one when repainting and instead it makes it
   * seem like all components are painted at once. This also helps to alleviate
   * repaint calls to only several instead of multiple
   *
   * @param master
   *          Reference component container to call
   * @param e
   *          Operations
   */
  public static void unify_(final JComponent master, final stl_Callback< Void, Void > e)
  {
    master.setVisible(false); // it is assumed that this component and the callback are related in terms of
                              // components updated
    e.call(null); // it is assumed that the painting automatically is done on the EDT
    master.setVisible(true);
  }
}