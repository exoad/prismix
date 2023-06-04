// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import javax.swing.*;

import com.jackmeng.prismix._1const;
import com.jackmeng.prismix.jm_Prismix;
import com.jackmeng.stl.stl_AnsiColors;
import com.jackmeng.stl.stl_AnsiMake;

import java.awt.KeyboardFocusManager;

import static com.jackmeng.prismix.use_Maker.*;

import java.awt.BorderLayout;

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

    setTitle("Prismix ~ exoad (build_" + jm_Prismix._VERSION_ + ")");
    setIconImage(_1const.fetcher.image("assets/_icon.png"));
    wrapper.setPreferredSize(getPreferredSize());
    wrapper.add(bar, BorderLayout.PAGE_START);
    getContentPane().add(wrapper);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setDefaultLookAndFeelDecorated(true);

    if (_1const.SOFT_DEBUG)
    {
      System.out.println(new stl_AnsiMake(stl_AnsiColors.BLUE_TXT, "[GUI_MAIN] Arming a global key listener"));
      KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(v -> {
        System.out.println("[UX] Received a keyboard press: " + v.getKeyCode() + " -> " + v.getKeyChar());
        return false;
      });
    }
  }

  public void registerToBar(String name, JMenuItem... item)
  {
    bar.add(makeJMenu(name, item));
    revalidate();
  }

  @Override public void run()
  {
    this.pack();
    this.setLocationRelativeTo(null);
    this.setVisible(true);
  }

}