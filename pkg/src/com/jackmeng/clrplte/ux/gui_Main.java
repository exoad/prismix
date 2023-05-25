// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.clrplte.ux;

import javax.swing.*;

import static com.jackmeng.clrplte.use_Maker.*;

import java.awt.BorderLayout;
import java.awt.Dimension;

import com.jackmeng.clrplte._1const;

public class gui_Main
    extends JFrame
    implements
    Runnable
{
  public final JMenuBar bar;
  public final JPanel wrapper;

  public gui_Main()
  {
    wrapper = new JPanel();
    wrapper.setLayout(new BorderLayout());

    bar = new JMenuBar();

    setTitle("Color Paletter - exoad");
    setIconImage(_1const.fetcher.image("assets/_icon.png"));
    wrapper.setPreferredSize(getPreferredSize());
    wrapper.add(bar, BorderLayout.NORTH);
    getContentPane().add(wrapper);
    setIgnoreRepaint(true);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

  }

  public void registerToBar(String name, JMenuItem... item)
  {
    bar.add(makeJMenu(name, item));
  }

  @Override public void run()
  {
    this.pack();
    this.setLocationRelativeTo(null);
    this.setVisible(true);
  }

}