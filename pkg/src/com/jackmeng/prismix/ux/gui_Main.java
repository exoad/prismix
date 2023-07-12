// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.jackmeng.prismix._1const;
import com.jackmeng.prismix.jm_Prismix;
import com.jackmeng.prismix.use_Maker;

/*------------------------------------------------ /
/ import static com.jackmeng.prismix.jm_Prismix.*; /
/-------------------------------------------------*/

public final class gui_Main
    extends
    gui
{
  public final JMenuBar bar;
  public final JPanel wrapper;

  public gui_Main()
  {
    super("Prismix ~ exoad (build_" + jm_Prismix._VERSION_ + ")");
    this.wrapper = new JPanel();
    this.wrapper.setLayout(new BorderLayout());

    this.bar = new JMenuBar();

    this.setIconImage(_1const.fetcher.image("assets/_icon.png"));
    this.wrapper.setPreferredSize(this.getPreferredSize());
    this.setJMenuBar(this.bar); // should not be added with a wrapper JPanel! (i did not know this was a method)
    this.getContentPane().add(this.wrapper);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    JFrame.setDefaultLookAndFeelDecorated(true);

    /*------------------------------------------------------------------------------------------------------ /
    / if ("true".equalsIgnoreCase(_1const.val.get_value("debug_gui")))                                       /
    / {                                                                                                      /
    /   log("GUIMAIN", jm_Ansi.make().yellow().toString("Arming a global key listener"));                    /
    /   KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(v -> {                   /
    /     System.out.println("[UX] Received a keyboard press: " + v.getKeyCode() + " -> " + v.getKeyChar()); /
    /     return false;                                                                                      /
    /   });                                                                                                  /
    / }                                                                                                      /
    /-------------------------------------------------------------------------------------------------------*/
  }

  public void registerToBar(final String name, final JMenuItem... item)
  {
    this.bar.add(use_Maker.makeJMenu(name, item));
    this.revalidate();
  }

}