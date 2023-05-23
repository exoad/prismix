// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.clrplte.ui;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;

import com.jackmeng.clrplte._1const;
import com.jackmeng.clrplte.stl.extend_stl_Wrap;
import com.jackmeng.clrplte.use.use_Maker;
import com.jackmeng.stl.stl_Callback;
import com.jackmeng.stl.stl_Struct;
import com.jackmeng.stl.stl_Wrap;
import com.jackmeng.stl.types.Null_t;

public class gui_Main
    extends JFrame
    implements
    Runnable
{

  private static JMenuItem[] make(stl_Struct.struct_Pair< String, stl_Callback< Boolean, Null_t > >[] items)
  {
    JMenuItem[] e = new JMenuItem[items.length];
    for (extend_stl_Wrap< Integer > i = new extend_stl_Wrap<>(0); i.get() < e.length; i.set(i.get() + 1))
    {
      stl_Callback< Boolean, Null_t > callback = items[i.get()].second;
      int first_attribute = Integer.parseInt(items[i.get()].first.substring(0, 1));
      String name = items[i.get()].first.substring(2);
      System.out.println("[MenuBar] Checks for: " + name + "\n[Menubar] Itr: " + i.get()
          + " \n[MenuBar] Config Bar Type: " + first_attribute);
      switch (first_attribute) {
        case 1:
          e[i.get()] = new JCheckBoxMenuItem(name);
          ((JCheckBoxMenuItem) e[i.get()]).setState(items[i.get()].second.call((Null_t) null));
          break;
        default:
          System.out.println("[GUI_MAIN] Unknown type: " + first_attribute);
          break;
      }
      e[i.get()].addActionListener(ev -> callback.call((Null_t) null));
    }
    return e;
  }

  static stl_Struct.struct_Pair< String, stl_Callback< Boolean, Null_t > > make(String name,
      stl_Callback< Boolean, Null_t > callback)
  {
    return new stl_Struct.struct_Pair<>(name, callback);
  }

  static stl_Callback< Boolean, Null_t > make(JComponent component)
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

  gui_Container masterView;

  @SuppressWarnings("unchecked") public gui_Main(gui_Container masterView)
  {
    this.masterView = masterView;
    JPanel wrapper = new JPanel();
    wrapper.setLayout(new BorderLayout());

    JMenuBar bar = new JMenuBar();
    bar.setPreferredSize(new Dimension(masterView.getPreferredSize().width, 25));
    bar.add(use_Maker.makeJMenu("Color Config.", make(new stl_Struct.struct_Pair[] {
        make(
            "1_Misc. Attributes",
            make(masterView.top.miscAttributes)),
        make(
            "1_RGBA Attributes",
            make(masterView.top.rgbData)),
        make("1_Color Space Attributes",
            make(masterView.top.colorSpace)),
    })));

    setTitle("Color Paletter - exoad");
    setIconImage(_1const.fetcher.image("assets/_icon.png"));
    setPreferredSize(new Dimension(masterView.getPreferredSize().width,
        masterView.getPreferredSize().height + bar.getPreferredSize().height + 30));
    wrapper.setPreferredSize(getPreferredSize());
    wrapper.add(bar, BorderLayout.NORTH);
    wrapper.add(masterView, BorderLayout.SOUTH);
    getContentPane().add(wrapper);
    setIgnoreRepaint(true);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

  }

  @Override public void run()
  {
    this.pack();
    this.setLocationRelativeTo(null);
    masterView.validate_size();
    this.setVisible(true);
  }

}