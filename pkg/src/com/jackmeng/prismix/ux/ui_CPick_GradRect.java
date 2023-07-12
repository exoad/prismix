package com.jackmeng.prismix.ux;

import static com.jackmeng.prismix.jm_Prismix.log;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;

import com.jackmeng.prismix._1const;
import com.jackmeng.prismix.use_Maker;
import com.jackmeng.prismix.stl.extend_stl_Colors;
import com.jackmeng.prismix.stl.extend_stl_Wrap;
import com.jackmeng.stl.stl_Colors;
import com.jackmeng.stl.stl_Listener;
import com.jackmeng.stl.stl_Struct;
import com.jackmeng.stl.stl_Ware;
import com.jackmeng.stl.stl_Wrap;
import com.jackmeng.stl.stl_Struct.struct_Pair;

// Creates a Gradient of White-Black-Color rectangular with sliders to adjust
// the colors only
// This should be the default option for the color picker
public final class ui_CPick_GradRect // This for picking RGBA colors
    extends JSplitPane
    implements
    stl_Listener< stl_Struct.struct_Pair< Color, Boolean > > // Optional to be added but the color picker should
                                                             // be always attached to the currently color picker
    , MouseMotionListener,
    MouseListener
{
  private JPanel gradientView;
  private transient boolean toPaintCurr = true;
  private transient extend_stl_Wrap< Integer > size_SquareGrad;
  private transient int transformed_x = -1, transformed_y = -1; // transformed which represent the relative mouse
  // location relative to prismix
  private int selector_cursor_radius = 10, selector_cursor_stroke = 2;
  private transient stl_Wrap< Color > c = new stl_Wrap<>(Color.gray);
  private boolean first = true, rgb_Sliders_Listen = false;
  private JSlider controls_RED, controls_GREEN, controls_BLUE;

  private transient ChangeListener syncSliders = Boolean.TRUE
      .equals((Boolean) _1const.val.parse("stoopid_sliders").get()) ? ev -> {
        rgb_Sliders_Listen = false;
        _1const.COLOR_ENQ.dispatch(stl_Struct
            .make_pair(new Color(controls_RED.getValue(), controls_GREEN.getValue(), controls_BLUE.getValue()),
                false));
        rgb_Sliders_Listen = true;
      } : ev -> {
        if (!((JSlider) ev.getSource()).getValueIsAdjusting())
        {
          rgb_Sliders_Listen = false;
          _1const.COLOR_ENQ.dispatch(stl_Struct
              .make_pair(new Color(controls_RED.getValue(), controls_GREEN.getValue(), controls_BLUE.getValue()),
                  false));
          rgb_Sliders_Listen = true;
        }

      };

  public ui_CPick_GradRect()
  {
    final JScrollPane controls_ScrollView = new JScrollPane();
    controls_ScrollView.setBorder(BorderFactory.createEmptyBorder());

    JPanel controls = new JPanel();
    controls.setLayout(new BoxLayout(controls, BoxLayout.X_AXIS));

    boolean use_theme_based_tooling = (Boolean) _1const.val.parse("use_theme_based_tooling").get();

    controls_RED = new JSlider(SwingConstants.VERTICAL, 0, 255, 255 / 2);
    controls_RED.setPaintTrack(true);
    controls_RED.setPaintTicks(true);
    controls_RED.setMajorTickSpacing(20);
    controls_RED.setMinorTickSpacing(5);
    controls_RED.setPaintLabels(true);
    controls_RED.addChangeListener(syncSliders);
    controls_RED.setForeground(use_theme_based_tooling ? Color.RED : stl_Colors.hexToRGB(ux_Theme.get().get("rose")));

    controls_GREEN = new JSlider(SwingConstants.VERTICAL, 0, 255, 255 / 2);
    controls_GREEN.setPaintTrack(true);
    controls_GREEN.setPaintTicks(true);
    controls_GREEN.setMajorTickSpacing(20);
    controls_GREEN.setMinorTickSpacing(5);
    controls_GREEN.setPaintLabels(true);
    controls_GREEN.addChangeListener(syncSliders);
    controls_GREEN
        .setForeground(use_theme_based_tooling ? Color.GREEN : stl_Colors.hexToRGB(ux_Theme.get().get("green")));

    controls_BLUE = new JSlider(SwingConstants.VERTICAL, 0, 255, 255 / 2);
    controls_BLUE.setPaintTrack(true);
    controls_BLUE.setPaintTicks(true);
    controls_BLUE.setMajorTickSpacing(20);
    controls_BLUE.setMinorTickSpacing(5);
    controls_BLUE.setPaintLabels(true);
    controls_BLUE.addChangeListener(syncSliders);
    controls_BLUE
        .setForeground(use_theme_based_tooling ? Color.BLUE : stl_Colors.hexToRGB(ux_Theme.get().get("ocean")));

    controls.add(Box.createHorizontalStrut(30));
    controls.add(controls_RED);
    controls.add(Box.createHorizontalStrut(20));
    controls.add(controls_GREEN);
    controls.add(Box.createHorizontalStrut(20));
    controls.add(controls_BLUE);

    ui_LazyViewport lazyViewport_controls = new ui_LazyViewport();
    lazyViewport_controls.setView(controls);
    controls_ScrollView.setViewportView(lazyViewport_controls);
    _1const.COLOR_ENQ.add(x -> {
      if (first)
      {
        c.obj(x.first);
        first = false;
      }
      else
      {
        if (!toPaintCurr)
          if (Boolean.TRUE.equals(!x.second))
            c.obj(x.first);
        toPaintCurr = !toPaintCurr;
      }

      repaint(75L);

      return ((Void) null);
    });

    gradientView = new JPanel() {
      @Override public void paintComponent(final Graphics g)
      {
        super.paintComponent(g);
        use_Maker.db_timed(() -> {
          final Graphics2D g2 = (Graphics2D) g;
          g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
          // draw the gradient
          BufferedImage v = extend_stl_Colors.cpick_gradient2(this.getSize().width,
              c.obj());
          g2.drawImage(v,
              null, 0,
              (getHeight() - v.getHeight()) / 2);
          size_SquareGrad.obj(v.getWidth()); // width == height

          if (transformed_x != -1 && transformed_y != -1)
          {

            // draw the color cursor
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE); // prob does
                                                                                                      // something
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // we are drawing
                                                                                                     // a
                                                                                                     // circle here,
                                                                                                     // so
                                                                                                     // gotta make
                                                                                                     // sure
                                                                                                     // the strokes
                                                                                                     // are
                                                                                                     // smooth
            g2.setStroke(new java.awt.BasicStroke(selector_cursor_stroke));
            g2.setColor(Color.white);
            g2.drawOval(transformed_x - (selector_cursor_radius / 2), transformed_y - (selector_cursor_radius / 2),
                selector_cursor_radius, selector_cursor_radius); // i got so confused, i
            // thought it took
            // x,y,x2,y2 so cringe

          }

          g2.dispose();
        });
      }
    };

    size_SquareGrad = new extend_stl_Wrap<>(gradientView.getSize().width); // width == height

    setRightComponent(lazyViewport_controls);
    setLeftComponent(gradientView);
    setOrientation(JSplitPane.HORIZONTAL_SPLIT);
    setDividerLocation(200); // fuck proportions, we hardcode this shit
    setAutoscrolls(false);
    setName("ui_CPick_GradRect");
    addComponentListener(ux_Listen.VISIBILITY());
    addMouseMotionListener(this);
    addMouseListener(this);
  }

  @Override public Void call(final struct_Pair< Color, Boolean > arg0)
  {
    SwingUtilities.invokeLater(() -> this.repaint(75L));
    if (rgb_Sliders_Listen)
    {
      controls_RED.setValue(arg0.first.getRed());
      controls_GREEN.setValue(arg0.first.getGreen());
      controls_BLUE.setValue(arg0.first.getBlue());
    }
    return (Void) null;
  }

  @Override public void mouseDragged(MouseEvent e)
  {
    if (stx_Helper.within(e.getPoint(), new Point(0, (getHeight() - size_SquareGrad.get()) / 2),
        new Dimension(size_SquareGrad.get(), size_SquareGrad.get())))
    {
      log("CPick_GradRect",
          "Mouse [ DRAGGED ] at: " + e.getX() + ", " + (e.getY() - (getHeight() - size_SquareGrad.get()) / 2)); // we
                                                                                                                // have
      // to
      // normalize
      // Y
      transformed_x = e.getX(); // wtf
      transformed_y = e.getY(); // wtf here i got confused with relative coordinates and absolute coordintes in
                                // regards to the components (screen versus prismix)
      setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
      toPaintCurr = false;
      SwingUtilities.invokeLater(() -> repaint(70L));
    }
    else
      setCursor(Cursor.getDefaultCursor());

  }

  @Override public void mouseExited(MouseEvent e)
  {
  }

  @Override public void mouseReleased(MouseEvent e)
  {
    if (stx_Helper.within(e.getPoint(), new Point(0, (getHeight() - size_SquareGrad.get()) / 2),
        new Dimension(size_SquareGrad.get(), size_SquareGrad.get())))
    {
      toPaintCurr = true;
      _1const.COLOR_ENQ
          .dispatch(stl_Struct.make_pair(stl_Ware.screen_colorAt(e.getXOnScreen(), e.getYOnScreen()).get(), false));
    }
  }

  @Override public void mouseClicked(MouseEvent e)
  {
    // to
    // normalize
    // Y
    transformed_x = e.getX(); // wtf
    transformed_y = e.getY(); // wtf here i got confused with relative coordinates and absolute coordintes in
                              // regards to the components (screen versus prismix)
    setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    toPaintCurr = false;
    SwingUtilities.invokeLater(() -> repaint(70L));
  }

  @Override public void mouseMoved(MouseEvent e)
  {
    /*-------------------------------------------------------------------------------------------------------------- /
    / log("CPick_GradRect",                                                                                          /
    /     "Mouse within? " + ux_Helper.within(e.getPoint(), new Point(0, (getHeight() - size_SquareGrad.get()) / 2), /
    /         new Dimension(size_SquareGrad.get(), size_SquareGrad.get())));                                         /
    / if (!ux_Helper.within(e.getPoint(), new Point(0, (getHeight() - size_SquareGrad.get()) / 2),                   /
    /     new Dimension(size_SquareGrad.get(), size_SquareGrad.get())))                                              /
    /   setCursor(Cursor.getDefaultCursor());                                                                        /
    /---------------------------------------------------------------------------------------------------------------*/

    /*----------------------------------------------------------------------------------------------------------------- /
    / // we ignore this event                                                                                           /
    / if (ux_Helper.within(e.getPoint(), new Point(0, (getHeight() - size_SquareGrad.get()) / 2),                       /
    /     new Dimension(size_SquareGrad.get(), size_SquareGrad.get())))                                                 /
    /   log("CPick_GradRect",                                                                                           /
    /       "Mouse [ MOVED ] at: " + e.getX() + ", " + (e.getY() - (getHeight() - size_SquareGrad.get()) / 2)); // we   /
    /                                                                                                           // have /
    / // to                                                                                                             /
    / // normalize                                                                                                      /
    / // Y                                                                                                              /
    /------------------------------------------------------------------------------------------------------------------*/
  }

  @Override public void mousePressed(MouseEvent e)
  {
  }

  @Override public void mouseEntered(MouseEvent e)
  {
  }

}