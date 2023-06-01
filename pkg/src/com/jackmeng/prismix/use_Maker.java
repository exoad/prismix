// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix;

import javax.swing.BorderFactory;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.jackmeng.prismix.stl.extend_stl_Wrap;
import com.jackmeng.stl.stl_Callback;
import com.jackmeng.stl.stl_Struct;
import com.jackmeng.stl.stl_Wrap;
import com.jackmeng.stl.types.Null_t;

import java.awt.Color;

/**
 * Utility methods container
 * Class contains a bunch of uncategorized helper methods
 *
 * @author Jack Meng
 */
public final class use_Maker
{
  private use_Maker()
  {
  }

  public static void db_timed(Runnable r)
  {
    if (_1const.SOFT_DEBUG)
    {
      long i = System.currentTimeMillis();
      r.run();
      System.out
          .println("[DEBUG] Timed a run for: " + r.toString() + " took " + (System.currentTimeMillis() - i) + "ms");
    } else r.run();
  }

  public static JMenu makeJMenu(String text, JMenuItem... items)
  {
    JMenu menu = new JMenu(text);
    for (int i = 0; i < items.length; i++)
    {
      menu.add(items[i]);
      if (i != items.length - 1)
        menu.addSeparator();
    }
    return menu;
  }

  public static void debug(JComponent e)
  {
    if (e != null)
      if (e.getBorder() != null)
        e.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.pink, 2), e.getBorder()));
  }

  public static JMenuItem[] make(stl_Struct.struct_Pair< String, stl_Callback< Boolean, Null_t > >[] items)
  {
    JMenuItem[] e = new JMenuItem[items.length];
    for (extend_stl_Wrap< Integer > i = new extend_stl_Wrap<>(0); i.get() < e.length; i.set(i.get() + 1))
    {
      stl_Callback< Boolean, Null_t > callback = items[i.get()].second;
      String name = items[i.get()].first;
      System.out.println("[MenuBar] Checks for: " + name + "\n[Menubar] Itr: " + i.get());
      e[i.get()] = new JCheckBoxMenuItem(name);
      ((JCheckBoxMenuItem) e[i.get()]).setState(items[i.get()].second.call((Null_t) null));
      e[i.get()].addActionListener(ev -> callback.call((Null_t) null));
    }
    return e;
  }

  public static JPanel wrap(JComponent e, JComponent parent)
  {
    JPanel r = new JPanel();
    r.setPreferredSize(parent.getPreferredSize());
    r.add(e);
    return r;
  }

  public static float[] interpolate_generate(float step, float start, float end, int sample)
  {
    float[] t = new float[sample];
    float i = start;
    for (int x = 1; x < sample && i <= end; i += step, x++)
      t[x] = t[x - 1] + start;
    return t;
  }

  public static stl_Struct.struct_Pair< String, stl_Callback< Boolean, Null_t > > make(String name,
      stl_Callback< Boolean, Null_t > callback)
  {
    return new stl_Struct.struct_Pair<>(name, callback);
  }

  public static stl_Callback< Boolean, Null_t > make(JComponent component)
  {
    stl_Wrap< Boolean > firstSet_Component = new stl_Wrap<>(true);
    return x -> {
      if (Boolean.FALSE.equals(firstSet_Component.obj))
      {
        component.setVisible(!component.isVisible());
        SwingUtilities.invokeLater(component::repaint);
      }
      else firstSet_Component.obj(false);
      return component.isVisible();
    };
  }

}