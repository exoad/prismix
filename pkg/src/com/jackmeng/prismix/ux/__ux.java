// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JLayeredPane;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

import com.jackmeng.ansicolors.jm_Ansi;
import com.jackmeng.prismix._1const;
import com.jackmeng.prismix.use_Maker;
import com.jackmeng.prismix.stl.extend_stl_Colors;
import com.jackmeng.prismix.user.use_LSys;
import com.jackmeng.stl.stl_Callback;
import com.jackmeng.stl.stl_Struct;
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
    gui_XConfig configui = new gui_XConfig();
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
            stx_Helper.make_simple("Config", configui),
            stx_Helper.make_simple("Licenses",
                () -> gui_XInf.force_invoke(
                    use_LSys.read_all(_1const.fetcher.file("assets/text/TEXT_legals.html")).replace("\n", "<br>"),
                    "Open Source Licenses")),
            stx_Helper.make_simple("LuaOp", () -> gui_XLua.instance().run()));
  }

  public synchronized void force_redo()
  {
    log("UX", jm_Ansi.make().yellow().toString("Forcing a GUI redo..."));
    SwingUtilities.updateComponentTreeUI(this.mainui);

    this.mainui.repaint();
    this.mainui.revalidate();
    log("UX", jm_Ansi.make().green().toString("Force GUI redo DONE"));
  }

  private void overlayComponents(Container container)
  {
    for (Component component : container.getComponents())
    {
      if (component != null)
      {
        if (!(component instanceof JPanel || component instanceof JLayeredPane || component instanceof JRootPane
            || component instanceof JMenuBar || component instanceof JSplitPane))
        {
          try
          {
            log("DEBUG_GUI", "Added: " + component.getClass().getCanonicalName());
            Container parent = component.getParent();

            int index = parent.getComponentZOrder(component);

            ui_Whoops er = new ui_Whoops(extend_stl_Colors.awt_random_Color(), Color.black, 20, 4, true)
                .with_size(component.getPreferredSize());

            parent.add(er);
            parent.setComponentZOrder(component, index + 1);
            parent.setComponentZOrder(er, 0);
            parent.revalidate();
            parent.repaint();
          } catch (Exception ignored)
          {
          }
        }
      }

      if (component instanceof JTabbedPane tabbedPane)
      {
        for (int i = 0; i < tabbedPane.getTabCount(); i++)
        {
          Component tabComponent = tabbedPane.getComponentAt(i);
          if (tabComponent instanceof Container)
          {
            overlayComponents((Container) tabComponent); // Recursive call to process tab content
          }
        }
      } else if (component instanceof JScrollPane scrollPane)
      {
        JViewport viewport = scrollPane.getViewport();
        if (viewport != null)
        {
          Component viewComponent = viewport.getView();
          if (viewComponent instanceof Container)
          {
            overlayComponents((Container) viewComponent); // Recursive call to process scroll pane view content
          }
        }
      }

      if (component instanceof Container)
      {
        overlayComponents((Container) component); // Recursive call to process child components
      }

    }
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
      overlayComponents(this.mainui);

    }
  }
}