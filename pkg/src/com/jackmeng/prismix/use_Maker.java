// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.BorderFactory;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import com.jackmeng.prismix.stl.extend_stl_Wrap;
import com.jackmeng.stl.stl_AnsiColors;
import com.jackmeng.stl.stl_AnsiMake;
import com.jackmeng.stl.stl_Callback;
import com.jackmeng.stl.stl_Struct;
import com.jackmeng.stl.stl_Wrap;
import com.jackmeng.stl.types.Null_t;

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

  public static void db_timed(final Runnable r)
  {
    if (_1const.SOFT_DEBUG)
    {
      final long i = System.currentTimeMillis();
      r.run();
      System.out
          .println(new stl_AnsiMake(stl_AnsiColors.RED_TXT,
              "[DEBUG] Timed a run for: " + r.toString() + " took " + (System.currentTimeMillis() - i) + "ms"));
    }
    else r.run();
  }



  public static JMenu makeJMenu(final String text, final JMenuItem... items)
  {
    final JMenu menu = new JMenu(text);
    for (int i = 0; i < items.length; i++)
    {
      menu.add(items[i]);
      if (i != items.length - 1)
        menu.addSeparator();
    }
    return menu;
  }

  public static void debug(final JComponent e)
  {
    if (e != null)
      if (e.getBorder() != null)
        e.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.pink, 2), e.getBorder()));
  }

  public static JMenuItem[] make(final stl_Struct.struct_Pair< String, stl_Callback< Boolean, Null_t > >[] items)
  {
    final JMenuItem[] e = new JMenuItem[items.length];
    for (final extend_stl_Wrap< Integer > i = new extend_stl_Wrap<>(0); i.get() < e.length; i.set(i.get() + 1))
    {
      final stl_Callback< Boolean, Null_t > callback = items[i.get()].second;
      final String name = items[i.get()].first;
      System.out.println("[MenuBar] Checks for: " + name + "\n[Menubar] Itr: " + i.get());
      e[i.get()] = new JCheckBoxMenuItem(name);
      ((JCheckBoxMenuItem) e[i.get()]).setState(items[i.get()].second.call((Null_t) null));
      e[i.get()].addActionListener(ev -> callback.call((Null_t) null));
    }
    return e;
  }

  public static JPanel wrap(final JComponent e, final JComponent parent)
  {
    final JPanel r = new JPanel();
    r.setPreferredSize(parent.getPreferredSize());
    r.add(e);
    return r;
  }

  public static JPanel lr_wrap(final JComponent left, final JComponent right)
  {
    final JPanel r = new JPanel();
    r.setLayout(new BorderLayout());
    r.add(left, BorderLayout.WEST);
    r.add(right, BorderLayout.EAST);
    return r;
  }

  public static Optional< JPopupMenu > jpop(final String name,
      final stl_Struct.struct_Pair< String, stl_Callback< Void, Void > >[] entities)
  {
    if (entities != null && entities.length > 0)
    {
      final JPopupMenu jp = new JPopupMenu(name);
      for (final AtomicInteger i = new AtomicInteger(0); i.get() < entities.length; i.set(i.get()))
      {
        final JMenuItem item = new JMenuItem(entities[i.get()].first);
        item.addActionListener(ev -> entities[i.get()].second.call((Void) null));
        jp.add(item);
      }
      return Optional.of(jp);
    }
    return Optional.empty();
  }

  public static float[] rev(final float[] arr)
  {
    for (int i = 0; i < arr.length / 2; i++)
    {
      final float temp = arr[i];
      arr[i] = arr[arr.length - 1 - i];
      arr[arr.length - 1 - i] = temp;
    }
    return arr;
  }

  public static float[] interpolate_generate(final float step, final float start, final float end, final int sample)
  {
    final float[] t = new float[sample];
    float i = start;
    for (int x = 1; x < sample && i <= end; i += step, x++)
      t[x] = t[x - 1] + start;
    return t;
  }

  public static stl_Struct.struct_Pair< String, stl_Callback< Boolean, Null_t > > make(final String name,
      final stl_Callback< Boolean, Null_t > callback)
  {
    return new stl_Struct.struct_Pair<>(name, callback);
  }

  public static stl_Callback< Boolean, Null_t > make(final JComponent component)
  {
    final stl_Wrap< Boolean > firstSet_Component = new stl_Wrap<>(true);
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