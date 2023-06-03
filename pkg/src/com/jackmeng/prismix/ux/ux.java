// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import javax.swing.*;

import org.w3c.dom.events.MouseEvent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.AWTEventListener;
import java.util.Arrays;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import com.jackmeng.prismix._1const;
import com.jackmeng.prismix.use_Maker;
import com.jackmeng.prismix.stl.extend_stl_Colors;
import com.jackmeng.stl.stl_Callback;
import com.jackmeng.stl.stl_Struct;
import com.jackmeng.stl.stl_SwingHelper;
import com.jackmeng.stl.jlib.jlib_Point;
import com.jackmeng.stl.types.Null_t;

import java.awt.*;

/**
 * Main UI Handler class. This serves as a bridge between the
 * mainui and the childui facilitating regstering child elements to certain
 * elements in the mainui. On the other hand, it also facilitates as an
 * abstraction
 * layer for the main(String[]) function defined in jm_ColorPalette
 *
 * @author Jack Meng
 */
public final class ux
    implements
    Runnable
{

  static
  {
    _1const.worker.scheduleAtFixedRate(new TimerTask() {
      @Override public void run()
      {
        System.out.println(
            "[UX_SM_WATCHDOG] Cleanup watchdog checking if the AWT thread has a listener that is not needed...");
        if (!sampledMousePicking_Started.get())
        {
          try
          {
            if (Arrays.binarySearch(Toolkit.getDefaultToolkit().getAWTEventListeners(), awt_1) >= 0)
            {
              Toolkit.getDefaultToolkit().removeAWTEventListener(awt_1);
              System.out.println("[UX_SM_WATCHDOG] Cleanup watchdog removed the click sampler listener");
            }
            if (Arrays.binarySearch(Toolkit.getDefaultToolkit().getAWTEventListeners(), awt_2) >= 0)
            {
              Toolkit.getDefaultToolkit().removeAWTEventListener(awt_2);
              System.out.println("[UX_SM_WATCHDOG] Cleanup watchdog removed the motion sampler listener");
            }
            if (!ux.mainui.isEnabled())
              ux.mainui.setEnabled(true);
          } catch (ClassCastException e)
          {
            // this is an arbitrary error we can just ignore
          }
        }
        else System.out.println("[UX_SM_WATCHDOG] Cleanup watchdog not needed...");

      }
    }, 500L, 650L);
  }

  public static final ux ux = new ux();
  /* sampling version listener */ static final AWTEventListener awt_1 = event -> {
    if (event instanceof MouseEvent)
    {
      MouseEvent evt = (MouseEvent) event;
      System.out
          .println("[UX_SAMPLING_MOUSE] Mouse caught at: (" + evt.getScreenX() + ", " + evt.getScreenY() + ")");

      ux.mainui.setEnabled(true);
    }
  };

  /* motion sampler */ static final AWTEventListener awt_2 = event -> {
    if (event instanceof MouseEvent)
    {
      MouseEvent evt = (MouseEvent) event;
      System.out
          .println("[UX_SAMPLING_MOUSE] Mouse sampled at: (" + evt.getScreenX() + ", " + evt.getScreenY() + ")");
    }
  };

  static final AtomicBoolean sampledMousePicking_Started = new AtomicBoolean(false);
  public static final AtomicBoolean PROPERTY_USE_SORTED_COLOR_SUGGESTIONS = new AtomicBoolean(false);
  static final AtomicReference< stl_Struct.struct_Pair< jlib_Point, Color > > current = new AtomicReference<>(null);

  // THE FOLLOWING IS BROKEN!!! DO NOT CALL THE FOLLOWING CODE PLS
  // launchs a callback for a screen picking action
  public static synchronized void sampled_MousePicker(long sampleDelay,
      stl_Callback< Void, stl_Struct.struct_Pair< jlib_Point, Color > > e)
  {
    if (!sampledMousePicking_Started.get())
    {
      System.out.println("[UX_SAMPLING_MOUSE] Mouse sampler attached for COLOR_PICKING");
      ux.mainui.setEnabled(false);

      Toolkit.getDefaultToolkit().addAWTEventListener(awt_1::eventDispatched, AWTEvent.MOUSE_EVENT_MASK);
      Toolkit.getDefaultToolkit().addAWTEventListener(awt_2, AWTEvent.MOUSE_MOTION_EVENT_MASK);

      Thread t_worker = new Thread(() -> {
        while (sampledMousePicking_Started.get())
        {
          System.out.println("[UX_SAMPLING_MOUSE] Sampled at: ");
          try
          {
            Thread.sleep(sampleDelay);
          } catch (InterruptedException $e)

          {
            // ignore this bs exception
          }
        }
      });
      t_worker.start();

      sampledMousePicking_Started.set(true);
    }
  }

  private gui_Main mainui;
  private gui_Container childui;

  @SuppressWarnings("unchecked") public ux()
  {
    childui = new gui_Container();
    mainui = new gui_Main();
    mainui.setPreferredSize(new Dimension(childui.getPreferredSize().width, childui.getPreferredSize().height + 60));
    mainui.wrapper.add(childui, BorderLayout.SOUTH);
    mainui.bar.setPreferredSize(new Dimension(childui.getPreferredSize().width, 25));

    stl_Struct.struct_Pair< String, stl_Callback< Boolean, Null_t > >[] e = new stl_Struct.struct_Pair[childui.top
        .exports().length];
    JPanel[] r = childui.top.exports();
    for (int i = 0; i < r.length; i++)
      e[i] = use_Maker.make(r[i].getName(), use_Maker.make(r[i]));
    mainui.registerToBar("Color Attributes", use_Maker.make(e));
  }

  public synchronized void force_redo()
  {
    System.out.println("[UX] Attempting Force redo GUI...");
    SwingUtilities.updateComponentTreeUI(mainui);
    mainui.repaint();
    mainui.revalidate();
    System.out.println("[UX] Force redo GUI DONE");
  }

  @Override public void run()
  {
    System.out.println("[UX] Dispatched a run event for the current UI creation! Hoping this goes well...");
    SwingUtilities.invokeLater(() -> {
      childui.validate_size();
      mainui.run();
      childui.top.redo();
      _1const.COLOR_ENQ.dispatch(stl_Struct.make_pair(extend_stl_Colors.awt_random_Color(), false)); // moved this line
                                                                                                     // out of
                                                                                                     // gui_Container to
                                                                                                     // avoid unequal
                                                                                                     // initialization
                                                                                                     // of certain
                                                                                                     // components for
                                                                                                     // listening
    });
    if (_1const.DEBUG_GUI)
    {
      new Thread(() -> {
        AtomicBoolean started = new AtomicBoolean(true);
        while (true)
        {
          if (!started.get())
            started.set(true);
          stl_SwingHelper.listComponents_OfContainer(mainui).forEach(x -> {
            System.out.println("[DEBUG] Setting debug border for: " + x.hashCode());
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
            } catch (Exception t)
            {
              // IGNORE, probably some .setBorder() not supported bs
            }
          });
          try
          {
            Thread.sleep(1500L);
          } catch (InterruptedException e)
          {
            // IGNORE var "e"
          }
        }
      }).start();

    }
  }
}