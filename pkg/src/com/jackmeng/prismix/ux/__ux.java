// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.jackmeng.ansicolors.jm_Ansi;
import com.jackmeng.prismix._1const;
import com.jackmeng.prismix.use_Maker;
import com.jackmeng.prismix.stl.extend_stl_Colors;
import com.jackmeng.prismix.user.use_LSys;
import com.jackmeng.stl.stl_Callback;
import com.jackmeng.stl.stl_Struct;
import com.jackmeng.stl.stl_SwingHelper;
import com.jackmeng.stl.types.Null_t;

import static com.jackmeng.prismix.jm_Prismix.*;

/**
 * Main UI Handler class. This serves as a bridge between the
 * mainui and the childui facilitating regstering child elements to certain
 * elements in the mainui. On the other hand, it also facilitates as an
 * abstraction
 * layer for the main(String[]) function defined in jm_ColorPalette
 *
 * @author Jack Meng
 */
public final class __ux
    implements
    Runnable
{

  public static final __ux _ux = new __ux();

  private final gui_Main mainui;
  private final ui_Cntnr childui;
  private final gui_Config configui;

  public gui_Main getMainUI()
  {
    return this.mainui;
  }

  public synchronized void clear_history()
  {
    childui.bottom.clear_history();
  }

  @SuppressWarnings("unchecked") public __ux()
  {
    this.childui = new ui_Cntnr();
    this.mainui = new gui_Main();
    this.configui = new gui_Config();
    this.mainui
        .setPreferredSize(new Dimension(this.childui.getPreferredSize().width, this.childui.getPreferredSize().height));
    this.mainui.wrapper.add(this.childui, BorderLayout.CENTER);
    this.mainui.bar.setPreferredSize(new Dimension(this.childui.getPreferredSize().width, 25));
    final stl_Struct.struct_Pair< String, stl_Callback< Boolean, Null_t > >[] e = new stl_Struct.struct_Pair[this.childui.top
        .exports().length];
    final JPanel[] r = this.childui.top.exports();
    for (int i = 0; i < r.length; i++)
      e[i] = use_Maker.make(r[i].getName(), use_Maker.make(r[i]));
    this.mainui.registerToBar("Color Attributes", use_Maker.make(e));
    this.mainui.registerToBar("Prismix",
        new JMenuItem[] { stx_Helper.make_simple("Config", configui),
            stx_Helper.make_simple("Licenses",
                () -> gui_XInf.force_invoke(
                    use_LSys.read_all(_1const.fetcher.file("assets/text/TEXT_legals.html")).replace("\n", "<br>"),
                    "Open Source Licenses")),
            stx_Helper.make_simple("LuaOp", () -> gui_Lua.instance().run()) });
  }

  public synchronized void force_redo()
  {
    log("UX", jm_Ansi.make().yellow().toString("Forcing a GUI redo..."));
    SwingUtilities.updateComponentTreeUI(this.mainui);

    this.mainui.repaint();
    this.mainui.revalidate();
    log("UX", jm_Ansi.make().green().toString("Force GUI redo DONE"));
  }

  @Override public void run()
  {
    log("UX", "Dispatched a run event for the current UI creation! Hoping this goes well...");
    SwingUtilities.invokeLater(() -> {
      this.childui.validate_size();
      this.mainui.run();
      this.childui.top.redo();
      _1const.COLOR_ENQ.dispatch(stl_Struct.make_pair(extend_stl_Colors.awt_random_Color(), true)); // moved this line
                                                                                                     // out of
                                                                                                     // gui_Container to
                                                                                                     // avoid unequal
                                                                                                     // initialization
                                                                                                     // of certain
                                                                                                     // components for
                                                                                                     // listening
    });
    /*-------------------------------------------------------------------- /
    / ux._ux.getMainUI().addMouseMotionListener(new MouseMotionAdapter() { /
    /   @Override public void mouseMoved(MouseEvent r)                     /
    /   {                                                                  /
    /     MOUSE_LOCATION.first = r.getXOnScreen();                         /
    /     MOUSE_LOCATION.second = r.getYOnScreen();                        /
    /   }                                                                  /
    / });                                                                  /
    /---------------------------------------------------------------------*/

    if ("true".equalsIgnoreCase(_1const.val.get_value("debug_gui")))
    {
      new Thread(() -> {
        final AtomicBoolean started = new AtomicBoolean(true);
        while (true)
        {
          if (!started.get())
            started.set(true);
          stl_SwingHelper.listComponents_OfContainer(this.mainui).forEach(x -> {
            log("DEBUG", jm_Ansi.make().cyan_bg().toString("Setting DEBUG border for: " + x.hashCode()));
            try
            {
              if (x instanceof JComponent)
                ((JComponent) x).setBorder(!started.get() ? (((JComponent) x).getBorder() == null
                    ? BorderFactory
                        .createLineBorder(
                            new Color((float) Math.random(), (float) Math.random(), (float) Math.random()))
                    : BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                            new Color((float) Math.random(), (float) Math.random(), (float) Math.random())),
                        ((JComponent) x).getBorder()))
                    : BorderFactory
                        .createLineBorder(
                            new Color((float) Math.random(), (float) Math.random(), (float) Math.random())));
            } catch (final Exception t)
            {
              // IGNORE, probably some .setBorder() not supported bs
            }
          });
          try
          {
            Thread.sleep(1500L);
          } catch (final InterruptedException e)
          {
            // IGNORE var "e"
          }
        }
      }).start();

    }
  }
}