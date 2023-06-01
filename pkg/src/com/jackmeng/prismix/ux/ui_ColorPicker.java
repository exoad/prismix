// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import com.jackmeng.prismix._1const;
import com.jackmeng.prismix.use_Maker;
import com.jackmeng.prismix.stl.extend_stl_Colors;
import com.jackmeng.stl.stl_Listener;
import com.jackmeng.stl.stl_Struct;
import com.jackmeng.stl.stl_Struct.struct_Pair;

import java.awt.*;

import javax.swing.*;

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
      setFocusable(true);
      setBorder(new ui_RoundBorder(10));
    }

    @Override public void paintComponent(Graphics g)
    {
      super.paintComponent(g);
      use_Maker.db_timed(() -> {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        g2.drawImage(
            extend_stl_Colors.cpick_gradient2(getParent().getPreferredSize().height,
                _1const.last_color()),
            null, 0,
            0);
        g2.dispose();
      });
    }

    @Override public Void call(struct_Pair< Color, Boolean > arg0)
    {
      if (this.isVisible() || this.isFocusOwner())
      {
        System.out.println("[CPick_Generic#" + this.hashCode() + "] Picks up a valid color call from the Color queue!");
        repaint(75L);
      }
      return (Void) null;
    }

  }

  // Creates a Gradient of White-Black-Color rectangular with sliders to adjust
  // the colors only
  // This should be the default option for the color picker
  public static final class CPick_GradRect
      extends JPanel
      implements
      stl_Listener< stl_Struct.struct_Pair< Color, Boolean > > // Optional to be added but the color picker should
                                                               // be always attached to the currently color picker
  {

    @Override public Void call(struct_Pair< Color, Boolean > arg0)
    {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'call'");
    }

  }

  // For creating a list of potential "suggestions" for a color using a table.
  // Shades, complementary, etc..
  // This should be a secondary option
  public static final class CPick_SuggestionsList
      extends JPanel
      implements stl_Listener< stl_Struct.struct_Pair< Color, Boolean > >
  {

    private static JPanel acquire_defpane(int rows, int cols)
    {
      JPanel jp = new JPanel() {
        @Override public Component add(Component e)
        {
          if (e instanceof JComponent r)
            r.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
          return super.add(e);
        }
      };
      jp.setLayout(new GridLayout(rows, cols));
    }

    public CPick_SuggestionsList()
    {
      JScrollPane masterScroll = new JScrollPane();

      ui_LazyViewport mainViewport = new ui_LazyViewport();

      JPanel shades_Brightness = new JPanel();

      setLayout(new BorderLayout());
      add(masterScroll, BorderLayout.CENTER);
    }

    @Override public Void call(struct_Pair< Color, Boolean > arg0)
    {
      if (Boolean.FALSE.equals(arg0.second))
        SwingUtilities.invokeLater(() -> repaint(150L));
      return (Void) null;
    }

  }
}