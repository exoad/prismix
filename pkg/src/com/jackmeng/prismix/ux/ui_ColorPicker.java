// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import com.jackmeng.prismix._1const;
import com.jackmeng.prismix.use_Maker;
import com.jackmeng.prismix.stl.extend_stl_Colors;
import com.jackmeng.stl.stl_AnsiColors;
import com.jackmeng.stl.stl_AnsiMake;
import com.jackmeng.stl.stl_Listener;
import com.jackmeng.stl.stl_Struct;
import com.jackmeng.stl.stl_Struct.struct_Pair;

import java.awt.*;
import java.util.ArrayList;

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
        System.out.println("[CPick_Debug" + this.hashCode() + "] Picks up a valid color call from the Color queue!");
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

    private static JPanel acquire_defpane(String name)
    {
      JPanel jp = new JPanel() {
        @Override public Component add(Component e)
        {
          if (e instanceof JComponent r)
            r.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
          return super.add(e);
        }
      };
      jp.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5), name));
      jp.setLayout(new GridBagLayout());

      return jp;
    }

    transient java.util.List< JButton > brightness_List;
    JPanel shades_Brightness;

    public CPick_SuggestionsList()
    {
      int shades_cols = 10, shades_rows = 10;


      if (shades_cols != shades_rows)
        System.out
            .println(new stl_AnsiMake(stl_AnsiColors.CYAN_BG,
                "[CPick_Suggestions] shades_cols != shades_rows - This may cause unwanted graphical layouts."));
      JScrollPane masterScroll = new JScrollPane();

      ui_LazyViewport mainViewport = new ui_LazyViewport();
      JPanel contentWrapper = new JPanel();
      contentWrapper.setLayout(new BoxLayout(contentWrapper, BoxLayout.X_AXIS));

      shades_Brightness = acquire_defpane("Shades");

      if (_1const.SOFT_DEBUG)
      {
        shades_Brightness.setOpaque(true);
        shades_Brightness.setBackground(Color.CYAN);
      }

      brightness_List = new ArrayList<>();
      for (int i = 0; i < shades_cols * shades_rows; i++)
      {
        System.out.println(new stl_AnsiMake(stl_AnsiColors.MAGENTA_TXT, "[CPick_Suggestions] Creating a new ShadeButton[" + i + "]"));
        brightness_List.set(i, new JButton("HHHHH"));
        /*------------------------------------------------------------------------------------- /
        / if (_1const.SOFT_DEBUG)                                                               /
        /   brightness_List.get(i).setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 2)); /
        /--------------------------------------------------------------------------------------*/
        /*-------------------------------------------------- /
        / brightness_List.get(i).setForeground(Color.WHITE); /
        / brightness_List.get(i).setBackground(Color.BLACK); /
        /---------------------------------------------------*/
        shades_Brightness.add(brightness_List.get(i));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = i % shades_cols;
        gbc.gridy = i / shades_rows;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        shades_Brightness.add(brightness_List.get(i), gbc);
      }

      contentWrapper.add(shades_Brightness);

      mainViewport.setView(contentWrapper);
      masterScroll.setViewport(mainViewport);

      setLayout(new BorderLayout());
      add(masterScroll, BorderLayout.CENTER);
    }

    @Override public Void call(struct_Pair< Color, Boolean > arg0)
    {
      if (Boolean.FALSE.equals(arg0.second))
        SwingUtilities.invokeLater(() -> {
          System.out.println("[CPick_Suggestions] Called to revalidate the current colors");
          repaint(50L);
        });
      return (Void) null;
    }

  }
}
