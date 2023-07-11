// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.Point;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.util.ArrayList;
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

import com.jackmeng.ansicolors.jm_Ansi;
import com.jackmeng.prismix._1const;
import com.jackmeng.prismix.use_Maker;
import com.jackmeng.prismix.stl.extend_stl_Colors;
import com.jackmeng.prismix.stl.extend_stl_Wrap;
import com.jackmeng.stl.stl_Callback;
import com.jackmeng.stl.stl_Colors;
import com.jackmeng.stl.stl_Listener;
import com.jackmeng.stl.stl_Struct;

import com.jackmeng.stl.stl_Ware;
import com.jackmeng.stl.stl_Wrap;
import com.jackmeng.stl.stl_Struct.struct_Pair;

import static com.jackmeng.prismix.jm_Prismix.*;

// Implementations of color pickers visually
// All of these should be able to dispatch events to the color queue found in _1const
public final class ui_ColorPicker
{

  private ui_ColorPicker()
  {
  }
  /*----------------------------------------------------------------------------------------- /
  /                                                                                           /
  / @SuppressWarnings("unchecked") public static JComponent attach_asis(JComponent e)         /
  / {                                                                                         /
  /   if (e instanceof stl_Callback) // finalized types at compile time are so fucking stupid /
  /     _1const.COLOR_ENQ.add((stl_Listener< struct_Pair< Color, Boolean > >) e);             /
  /   return e;                                                                               /
  / }                                                                                         /
  /------------------------------------------------------------------------------------------*/

  public static final class CPick_GenericDisp
      extends JPanel
      implements
      stl_Listener< stl_Struct.struct_Pair< Color, Boolean > >
  {

    public CPick_GenericDisp()
    {
      this.setFocusable(true);
    }

    @Override public void paintComponent(final Graphics g)
    {
      super.paintComponent(g);
      use_Maker.db_timed(() -> {
        final Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        g2.drawImage(
            extend_stl_Colors.cpick_gradient2(this.getParent().getPreferredSize().height,
                _1const.last_color()),
            null, 0,
            0);
        g2.dispose();
      });
    }

    @Override public Void call(final struct_Pair< Color, Boolean > arg0)
    {
      if (this.isVisible() || this.isFocusOwner())
      {
        log("CPickDB", hashCode() + " Picks up a valid color call from the color queue!");
        this.repaint(75L);
      }
      return (Void) null;
    }

  }

  // Creates a Gradient of White-Black-Color rectangular with sliders to adjust
  // the colors only
  // This should be the default option for the color picker
  public static final class CPick_GradRect // This for picking RGBA colors
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

    public CPick_GradRect()
    {
      final JScrollPane controls_ScrollView = new JScrollPane();
      controls_ScrollView.setBorder(BorderFactory.createEmptyBorder());

      JPanel controls = new JPanel();
      controls.setLayout(new BoxLayout(controls, BoxLayout.X_AXIS));

      controls_RED = new JSlider(SwingConstants.VERTICAL, 0, 255, 255 / 2);
      controls_RED.setPaintTrack(true);
      controls_RED.setPaintTicks(true);
      controls_RED.setMajorTickSpacing(20);
      controls_RED.setMinorTickSpacing(5);
      controls_RED.setPaintLabels(true);
      controls_RED.addChangeListener(syncSliders);
      controls_RED.setForeground(stl_Colors.hexToRGB(ux_Theme._theme.get("rose")));

      controls_GREEN = new JSlider(SwingConstants.VERTICAL, 0, 255, 255 / 2);
      controls_GREEN.setPaintTrack(true);
      controls_GREEN.setPaintTicks(true);
      controls_GREEN.setMajorTickSpacing(20);
      controls_GREEN.setMinorTickSpacing(5);
      controls_GREEN.setPaintLabels(true);
      controls_GREEN.addChangeListener(syncSliders);
      controls_GREEN.setForeground(stl_Colors.hexToRGB(ux_Theme._theme.get("green")));

      controls_BLUE = new JSlider(SwingConstants.VERTICAL, 0, 255, 255 / 2);
      controls_BLUE.setPaintTrack(true);
      controls_BLUE.setPaintTicks(true);
      controls_BLUE.setMajorTickSpacing(20);
      controls_BLUE.setMinorTickSpacing(5);
      controls_BLUE.setPaintLabels(true);
      controls_BLUE.addChangeListener(syncSliders);
      controls_BLUE.setForeground(stl_Colors.hexToRGB(ux_Theme._theme.get("ocean")));

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
        debug("RECEIVED: " + toPaintCurr + " " + Boolean.TRUE.equals(!x.second));
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
      if (ux_Helper.within(e.getPoint(), new Point(0, (getHeight() - size_SquareGrad.get()) / 2),
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
      if (ux_Helper.within(e.getPoint(), new Point(0, (getHeight() - size_SquareGrad.get()) / 2),
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

  // For creating a list of potential "suggestions" for a color using a table.
  // Shades, complementary, etc..
  // This should be a secondary option
  public static final class CPick_SuggestionsList
      extends JPanel
      implements stl_Listener< stl_Struct.struct_Pair< Color, Boolean > >
  {

    private static JPanel acquire_defpane(final String name)
    {
      final JPanel jp = new JPanel();
      jp.setBorder(ux_Helper.cpick_suggestions_AttributesBorder(name));
      jp.setLayout(new GridBagLayout());

      return jp;
    }
    /*----------------------------------------------------------------------------------------------------------------- /
    / transient ActionListener shades_Listeners = ev -> {                                                               /
    /   final JButton e = ((JButton) ev.getSource()); // no additional checks needed, we shld be assured that this      /
    /                                                 // listener is                                                    /
    /   // only added to a JButton. additional checks can cause additional                                              /
    /   // optimization strains on the jvm                                                                              /
    /                                                                                                                   /
    /   if (e.getText().length() > 0)                                                                                   /
    /   {                                                                                                               /
    /     log("CPickSGTN", "Actions acquiring a proper menu handle");                                                   /
    /     SwingUtilities.invokeLater(() -> {                                                                            /
    /       final Optional< JPopupMenu > r = use_Maker.jpop(e.getText(), new stl_Struct.struct_Pair[] {                 /
    /           stl_Struct.make_pair("Copy to clipboard", (stl_Callback< Void, Void >) nil -> {                         /
    /             Toolkit.getDefaultToolkit().getSystemClipboard()                                                      /
    /                 .setContents(new java.awt.datatransfer.StringSelection(e.getText()), null);                       /
    /             log("CPickSGTN",                                                                                      /
    /                 jm_Ansi.make().green().toString("{1} Copied the target color to the clipboard: ") + e.getText()); /
    /             return (Void) null;                                                                                   /
    /           }),                                                                                                     /
    /           stl_Struct.make_pair("Inspect color", (stl_Callback< Void, Void >) nil -> {                             /
    /             _1const.COLOR_ENQ.dispatch(stl_Struct.make_pair(stl_Colors.hexToRGB(e.getText()), false));            /
    /             log("CPickSGTN",                                                                                      /
    /                 jm_Ansi.make().green().toString("{2} Dispatched the target color to the global inspect queue: ")  /
    /                     + e.getText());                                                                               /
    /             return (Void) null;                                                                                   /
    /           }),                                                                                                     /
    /           stl_Struct.make_pair("Inspect & Copy", (stl_Callback< Void, Void >) nil -> {                            /
    /             Toolkit.getDefaultToolkit().getSystemClipboard()                                                      /
    /                 .setContents(new java.awt.datatransfer.StringSelection(e.getText()), null);                       /
    /             _1const.COLOR_ENQ.dispatch(stl_Struct.make_pair(stl_Colors.hexToRGB(e.getText()), false));            /
    /                                                                                                                   /
    /             log("CPickSGTN", jm_Ansi.make().green().toString(                                                     /
    /                 "{3} Dispatched & Copied the target color: ")                                                     /
    /                 + e.getText());                                                                                   /
    /             return (Void) null;                                                                                   /
    /           })                                                                                                      /
    /       });                                                                                                         /
    /       log("CPickSGTN", "Action received the desired state!");                                                     /
    /       r.ifPresent(x -> {                                                                                          /
    /         java.awt.Point pt = new Point(_1const.MOUSE_LOCATION.first, _1const.MOUSE_LOCATION.second);               /
    /         log("CPickSGTN", "Rendering the final popup menu to the screen at: " + pt.x + ","                         /
    /             + pt.y);                                                                                              /
    /         ((javax.swing.JPopupMenu) x).show(this, pt.x, pt.y); // !needs fix                                        /
    /       });                                                                                                         /
    /     });                                                                                                           /
    /   }                                                                                                               /
    / };                                                                                                                /
    /------------------------------------------------------------------------------------------------------------------*/

    class Base_Palette
    {
      private java.util.List< ui_Tag > tags;
      public final JPanel component;
      public final int rows, cols;

      public Base_Palette(String name, int rows, int cols)
      {
        this.rows = rows;
        this.cols = cols;
        this.tags = new ArrayList<>(rows * cols);
        this.component = CPick_SuggestionsList.acquire_defpane(name);
        for (int i = 0; i < this.rows * this.cols; i++)
        {
          ui_Tag r = new ui_Tag();
          r.setFocusPainted(false);
          r.setBorderPainted(false);
          r.setFocusable(false);
          r.setForeground(Color.WHITE);
          r.setRolloverEnabled(false);
          this.tags.add(r);
          GridBagConstraints gbc = new GridBagConstraints();
          gbc.gridx = i % this.cols;
          gbc.gridy = i / this.rows;
          gbc.fill = GridBagConstraints.BOTH;
          gbc.weightx = 0.5;
          gbc.weighty = 1.0;
          gbc.ipadx = 30;
          gbc.insets = new Insets(3, 3, 3, 3);

          this.component.add(this.tags.get(i), gbc);
        }
      }

      public synchronized void _update(float[][] e)
      {
        for (int i = 0; i < this.cols * this.rows; i++)
          this.tags.get(i).sync(extend_stl_Colors.awt_remake(e[i]));
      }

    }

    final transient Base_Palette tones, tints, shades, complementary;

    public CPick_SuggestionsList()
    {
      final JScrollPane masterScroll = new JScrollPane();
      masterScroll.getHorizontalScrollBar().setUnitIncrement(5);
      masterScroll.setBorder(BorderFactory.createEmptyBorder());

      final ui_LazyViewport mainViewport = new ui_LazyViewport();
      final JPanel contentWrapper = new JPanel();
      contentWrapper.setBorder(BorderFactory.createEmptyBorder());
      contentWrapper.setLayout(new BoxLayout(contentWrapper, BoxLayout.X_AXIS));

      tones = new Base_Palette("Tones", 10, 15);
      tints = new Base_Palette("Tints", 10, 15);
      shades = new Base_Palette("Shades", 10, 15);
      complementary = new Base_Palette("Complementary", 8, 13);

      contentWrapper.add(tones.component);
      contentWrapper.add(tints.component);
      contentWrapper.add(shades.component);
      contentWrapper.add(complementary.component);

      mainViewport.setView(contentWrapper);
      masterScroll.setViewport(mainViewport);

      this.setLayout(new BorderLayout());
      this.add(masterScroll, BorderLayout.CENTER);

      setName("ui_CPick_SuggestionsList");
      addComponentListener(ux_Listen.VISIBILITY());
    }

    @Override public Void call(final struct_Pair< Color, Boolean > arg0)
    {
      if (Boolean.FALSE.equals(arg0.second)) // second arg to make sure that this does not paint
        // when the user is on a different tab
        /*------------------------------------------------------------------------------------------------------------------ /
        / {                                                                                                                  /
        /   log("CPickSGTN", jm_Ansi.make().blue().toString("Called to revalidate the current colors"));                     /
        /   boolean use_sorted = "true".equalsIgnoreCase(_1const.val.get_value("suggestions_sorted")),                       /
        /       use_l2d = "true".equalsIgnoreCase(_1const.val.get_value("suggestions_sort_light_to_dark"));                  /
        /   use_Maker.db_timed(() -> {                                                                                       /
        /     if (this.shades_Tones.isVisible())                                                                             /
        /     {                                                                                                              /
        /       final float[][] gen_tones = extend_stl_Colors.tones(extend_stl_Colors.awt_strip_rgba(arg0.first),            /
        /           this.tones_cols * this.tones_rows);                                                                      /
        /       if (use_sorted)                                                                                              /
        /       {                                                                                                            /
        /         if (use_l2d)                                                                                               /
        /           extend_stl_Colors.sort_l2d(gen_tones);                                                                   /
        /         else extend_stl_Colors.sort_d2l(gen_tones);                                                                /
        /       }                                                                                                            /
        /       final Color[] remadeTones = new Color[this.tones_cols * this.tones_rows];                                    /
        /       final String[] hexTones = new String[this.tones_cols * this.tones_rows];                                     /
        /       _render.unify_(this.shades_Shades, nil -> {                                                                  /
        /         for (int i = 0; i < this.tones_cols * this.tones_rows; i++)                                                /
        /         {                                                                                                          /
        /           final JButton button = this.tones_List.get(i);                                                           /
        /           remadeTones[i] = extend_stl_Colors.awt_remake(gen_tones[i]);                                             /
        /           button.setBackground(remadeTones[i]);                                                                    /
        /           button.setForeground(extend_stl_Colors.awt_remake(extend_stl_Colors.binary_fg_decider(gen_tones[i])));   /
        /           hexTones[i] = extend_stl_Colors.RGBToHex((int) gen_tones[i][0], (int) gen_tones[i][1],                   /
        /               (int) gen_tones[i][2]);                                                                              /
        /           button.setText(hexTones[i]);                                                                             /
        /           button.setBorderPainted(true);                                                                           /
        /           button.setToolTipText(hexTones[i]);                                                                      /
        /         }                                                                                                          /
        /         return (Void) null;                                                                                        /
        /       });                                                                                                          /
        /                                                                                                                    /
        /     }                                                                                                              /
        /                                                                                                                    /
        /     if (this.shades_Complements.isVisible())                                                                       /
        /     {                                                                                                              /
        /       float[][] gen_complements = extend_stl_Colors.complementaries(extend_stl_Colors.awt_strip_rgba(arg0.first),  /
        /           this.complement_cols * this.complement_rows);                                                            /
        /       if (use_sorted)                                                                                              /
        /       {                                                                                                            /
        /         if (use_l2d)                                                                                               /
        /           extend_stl_Colors.sort_l2d(gen_complements);                                                             /
        /         else extend_stl_Colors.sort_d2l(gen_complements);                                                          /
        /       }                                                                                                            /
        /       final Color[] remadeComplements = new Color[this.complement_cols * this.complement_rows];                    /
        /       final String[] hexComplements = new String[this.complement_cols * this.complement_rows];                     /
        /       _render.unify_(this.shades_Tint, nil -> {                                                                    /
        /         for (int i = 0; i < this.complement_cols * this.complement_rows; i++)                                      /
        /         {                                                                                                          /
        /           final JButton button = this.complements_List.get(i);                                                     /
        /           remadeComplements[i] = extend_stl_Colors.awt_remake(gen_complements[i]);                                 /
        /           button.setBackground(remadeComplements[i]);                                                              /
        /           button.setForeground(                                                                                    /
        /               extend_stl_Colors.awt_remake(extend_stl_Colors.binary_fg_decider(gen_complements[i])));              /
        /           hexComplements[i] = extend_stl_Colors.RGBToHex((int) gen_complements[i][0], (int) gen_complements[i][1], /
        /               (int) gen_complements[i][2]);                                                                        /
        /           button.setText(hexComplements[i]);                                                                       /
        /           button.setBorderPainted(true);                                                                           /                                    /
        /           button.setToolTipText(hexComplements[i]);                                                                /
        /         }                                                                                                          /
        /         return (Void) null;                                                                                        /
        /       });                                                                                                          /
        /                                                                                                                    /
        /     }                                                                                                              /
        /                                                                                                                    /
        /     if (this.shades_Tint.isVisible())                                                                              /
        /     {                                                                                                              /
        /       final float[][] gen_tints = extend_stl_Colors.tints(extend_stl_Colors.awt_strip_rgba(arg0.first),            /
        /           this.tint_cols * this.tint_rows);                                                                        /
        /       if (use_sorted)                                                                                              /
        /       {                                                                                                            /
        /         if (use_l2d)                                                                                               /
        /           extend_stl_Colors.sort_l2d(gen_tints);                                                                   /
        /         else extend_stl_Colors.sort_d2l(gen_tints);                                                                /
        /       }                                                                                                            /
        /       final Color[] remadeTints = new Color[this.tint_cols * this.tint_rows];                                      /
        /       final String[] hexTints = new String[this.tint_cols * this.tint_rows];                                       /
        /       _render.unify_(this.shades_Tint, nil -> {                                                                    /
        /         for (int i = 0; i < this.tint_cols * this.tint_rows; i++)                                                  /
        /         {                                                                                                          /
        /           final JButton button = this.tint_List.get(i);                                                            /
        /           remadeTints[i] = extend_stl_Colors.awt_remake(gen_tints[i]);                                             /
        /           button.setBackground(remadeTints[i]);                                                                    /
        /           button.setForeground(extend_stl_Colors.awt_remake(extend_stl_Colors.binary_fg_decider(gen_tints[i])));   /
        /           hexTints[i] = extend_stl_Colors.RGBToHex((int) gen_tints[i][0], (int) gen_tints[i][1],                   /
        /               (int) gen_tints[i][2]);                                                                              /
        /           button.setText(hexTints[i]);                                                                             /
        /           button.setBorderPainted(true);                                                                           //
        /           button.setToolTipText(hexTints[i]);                                                                      /
        /         }                                                                                                          /
        /         return (Void) null;                                                                                        /
        /       });                                                                                                          /
        /     }                                                                                                              /
        /                                                                                                                    /
        /     if (this.shades_Shades.isVisible())                                                                            /
        /     {                                                                                                              /
        /       final float[][] gen_shades = extend_stl_Colors.shades(extend_stl_Colors.awt_strip_rgba(arg0.first),          /
        /           this.shades_cols * this.shades_rows);                                                                    /
        /       if (use_sorted)                                                                                              /
        /       {                                                                                                            /
        /         if (use_l2d)                                                                                               /
        /           extend_stl_Colors.sort_l2d(gen_shades);                                                                  /
        /         else extend_stl_Colors.sort_d2l(gen_shades);                                                               /
        /       }                                                                                                            /
        /       final Color[] remadeShades = new Color[this.shades_cols * this.shades_rows];                                 /
        /       final String[] hexShades = new String[this.shades_cols * this.shades_rows];                                  /
        /       _render.unify_(this.shades_Shades, nil -> {                                                                  /
        /         for (int i = 0; i < this.shades_cols * this.shades_rows; i++)                                              /
        /         {                                                                                                          /
        /           final JButton button = this.shades_List.get(i);                                                          /
        /           remadeShades[i] = extend_stl_Colors.awt_remake(gen_shades[i]);                                           /
        /           button.setBackground(remadeShades[i]);                                                                   /
        /           button.setForeground(extend_stl_Colors.awt_remake(extend_stl_Colors.binary_fg_decider(gen_shades[i])));  /
        /           hexShades[i] = extend_stl_Colors.RGBToHex((int) gen_shades[i][0], (int) gen_shades[i][1],                /
        /               (int) gen_shades[i][2]);                                                                             /
        /           button.setText(hexShades[i]);                                                                            /
        /           button.setBorderPainted(true);                                                                           /                                   /
        /           button.setToolTipText(hexShades[i]);                                                                     /
        /         }                                                                                                          /
        /         return (Void) null;                                                                                        /
        /       });                                                                                                          /
        /                                                                                                                    /
        /     }                                                                                                              /
        /   });                                                                                                              /
        / }                                                                                                                  /
        /-------------------------------------------------------------------------------------------------------------------*/
        log("CPickSGTN", jm_Ansi.make().blue().toString("Called to revalidate the current colors"));
      boolean use_sorted = "true".equalsIgnoreCase(_1const.val.get_value("suggestions_sorted")),
          use_l2d = "true".equalsIgnoreCase(_1const.val.get_value("suggestions_sort_light_to_dark"));
      use_Maker.db_timed(() -> {
        float[][] gen_tones = extend_stl_Colors.tones(extend_stl_Colors.awt_strip_rgba(arg0.first),
            this.tones.cols * this.tones.rows);
        float[][] gen_complements = extend_stl_Colors.complementaries(extend_stl_Colors.awt_strip_rgba(arg0.first),
            this.complementary.cols * this.complementary.rows);
        float[][] gen_tints = extend_stl_Colors.tints(extend_stl_Colors.awt_strip_rgba(arg0.first),
            this.tints.rows * this.tints.cols);
        float[][] gen_shades = extend_stl_Colors.shades(extend_stl_Colors.awt_strip_rgba(arg0.first),
            this.shades.cols * this.shades.rows);

        if (use_sorted)
        {
          if (use_l2d)
          {
            extend_stl_Colors.sort_l2d(gen_tones);
            extend_stl_Colors.sort_l2d(gen_complements);
            extend_stl_Colors.sort_l2d(gen_tints);
            extend_stl_Colors.sort_l2d(gen_shades);
          }
          else
          {
            extend_stl_Colors.sort_d2l(gen_tones);
            extend_stl_Colors.sort_d2l(gen_complements);
            extend_stl_Colors.sort_d2l(gen_tints);
            extend_stl_Colors.sort_d2l(gen_shades);
          }
        }
        // unified rendering doesn't work here, causes tab compositing to fuck up.
        SwingUtilities.invokeLater(() -> {
          this.tints._update(gen_tints);
          this.shades._update(gen_shades);
          this.complementary._update(gen_complements);
          this.tones._update(gen_complements);
        });

      });
      return null;
    }
    // defunct version (works but not good enuf for performance)
    /*---------------------------------------------------------------------------------------------------------------- /
    / @Override public Void call(struct_Pair< Color, Boolean > arg0)                                                   /
    / {                                                                                                                /
    /   if (Boolean.FALSE.equals(arg0.second))                                                                         /
    /   {                                                                                                              /
    /     System.out.println("[CPick_Suggestions] Called to revalidate the current colors");                           /
    /                                                                                                                  /
    /     if (shades_Tones.isVisible())                                                                                /
    /     {                                                                                                            /
    /       float[][] gen_tones = extend_stl_Colors.tones(extend_stl_Colors.awt_strip_rgba(arg0.first),                /
    /           tones_cols * tones_rows);                                                                              /
    /       SwingUtilities.invokeLater(() -> {                                                                         /
    /         for (int i = 0; i < tones_cols * tones_rows; i++)                                                        /
    /         {                                                                                                        /
    /           JButton button = tones_List.get(i);                                                                    /
    /           Color c = extend_stl_Colors.awt_remake(gen_tones[i]);                                                  /
    /           button.setBackground(c);                                                                               /
    /           button.setForeground(extend_stl_Colors.awt_remake(extend_stl_Colors.binary_fg_decider(gen_tones[i]))); /
    /           button.setText(                                                                                        /
    /               extend_stl_Colors.RGBToHex((int) gen_tones[i][0], (int) gen_tones[i][1], (int) gen_tones[i][2]));  /
    /           button.setToolTipText(button.getText());                                                               /
    /         }                                                                                                        /
    /       });                                                                                                        /
    /     }                                                                                                            /
    /                                                                                                                  /
    /     if (shades_Tint.isVisible())                                                                                 /
    /     {                                                                                                            /
    /       float[][] gen_tints = extend_stl_Colors.tints(extend_stl_Colors.awt_strip_rgba(arg0.first),                /
    /           tint_cols * tint_rows);                                                                                /
    /       SwingUtilities.invokeLater(() -> {                                                                         /
    /         for (int i = 0; i < tint_cols * tint_rows; i++)                                                          /
    /         {                                                                                                        /
    /           JButton button = tint_List.get(i);                                                                     /
    /           Color c = extend_stl_Colors.awt_remake(gen_tints[i]);                                                  /
    /           button.setBackground(c);                                                                               /
    /           button.setForeground(extend_stl_Colors.awt_remake(extend_stl_Colors.binary_fg_decider(gen_tints[i]))); /
    /           button.setText(                                                                                        /
    /               extend_stl_Colors.RGBToHex((int) gen_tints[i][0], (int) gen_tints[i][1], (int) gen_tints[i][2]));  /
    /           button.setToolTipText(button.getText());                                                               /
    /         }                                                                                                        /
    /       });                                                                                                        /
    /     }                                                                                                            /
    /   }                                                                                                              /
    /   return null;                                                                                                   /
    / }                                                                                                                /
    /                                                                                                                  /
    /-----------------------------------------------------------------------------------------------------------------*/
  }
}
